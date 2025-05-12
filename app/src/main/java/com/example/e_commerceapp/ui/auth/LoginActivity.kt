package com.example.e_commerceapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.User
import com.example.e_commerceapp.databinding.ActivityLoginBinding
import com.example.e_commerceapp.data.repository.FirebaseAuthManager
import com.example.e_commerceapp.ui.home.MainActivity
import com.example.e_commerceapp.vendor.VendorDashboardActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authManager = FirebaseAuthManager()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener { validateAndLogin() }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        binding.btnGoogleSignIn.setOnClickListener { signInWithGoogle() }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it.idToken!!) }
            } catch (e: ApiException) {
                showError("Google sign in failed: ${e.statusCode}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        showLoading(true)

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        val appUser = User(
                            name = it.displayName ?: "",
                            email = it.email ?: "",
                            profileImage = it.photoUrl?.toString(),
                            address = null
                        )
                        // Save user info to repository
                        com.example.e_commerceapp.data.repository.UserRepository.init(applicationContext)
                        com.example.e_commerceapp.data.repository.UserRepository.setUser(appUser)

                        // Check user roles after Google sign in
                        checkUserRolesAndNavigate(user.uid)
                    }
                } else {
                    showError(task.exception?.message ?: "Google authentication failed")
                }
            }
    }

    private fun validateAndLogin() {
        val email = binding.tilEmail.editText?.text.toString().trim()
        val password = binding.tilPassword.editText?.text.toString().trim()

        binding.tilEmail.error = null
        binding.tilPassword.error = null

        when {
            email.isEmpty() -> {
                binding.tilEmail.error = getString(R.string.error_email_empty)
                binding.tilEmail.requestFocus()
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = getString(R.string.error_email_invalid)
                binding.tilEmail.requestFocus()
            }
            password.isEmpty() -> {
                binding.tilPassword.error = getString(R.string.error_password_empty)
                binding.tilPassword.requestFocus()
            }
            password.length < 6 -> {
                binding.tilPassword.error = getString(R.string.error_password_length)
                binding.tilPassword.requestFocus()
            }
            else -> {
                performLogin(email, password)
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        showLoading(true)

        authManager.login(email, password,
            onSuccess = {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.uid ?: run {
                    showLoading(false)
                    showError("User ID not found")
                    return@login
                }

                checkUserRolesAndNavigate(userId)
            },
            onFailure = { errorMessage ->
                showLoading(false)
                showError(errorMessage)
            }
        )
    }

    private fun checkUserRolesAndNavigate(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val roles = document.get("roles") as? List<*> ?: listOf("customer")
                    val currentRole = document.getString("currentRole") ?: "customer"

                    when {
                        roles.contains("vendor") && roles.contains("customer") -> {
                            // User has both roles - show role chooser
                            showRoleChooser(currentRole)
                        }
                        roles.contains("vendor") -> {
                            // Only vendor role - go to vendor dashboard
                            setSessionAndNavigate("vendor")
                        }
                        else -> {
                            // Default to customer role
                            setSessionAndNavigate("customer")
                        }
                    }
                } else {
                    // No document found - default to customer
                    setSessionAndNavigate("customer")
                }
            }
            .addOnFailureListener {
                showLoading(false)
                showError("Failed to fetch user role: ${it.message}")
            }
    }

    private fun showRoleChooser(currentRole: String) {
        val roles = arrayOf("Vendor", "Customer")
        val checkedItem = if (currentRole == "vendor") 0 else 1

        AlertDialog.Builder(this)
            .setTitle("Choose Your Role")
            .setSingleChoiceItems(roles, checkedItem) { dialog, which ->
                val selectedRole = if (which == 0) "vendor" else "customer"
                updateCurrentRole(selectedRole) {
                    setSessionAndNavigate(selectedRole)
                    dialog.dismiss()
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun updateCurrentRole(role: String, onComplete: () -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        db.collection("users")
            .document(uid)
            .update("currentRole", role)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener {
                // Even if update fails, proceed with navigation
                onComplete()
            }
    }

    private fun setSessionAndNavigate(role: String) {
        showLoading(false)

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putBoolean("isLoggedIn", true)
        editor.putString("userEmail", FirebaseAuth.getInstance().currentUser?.email)
        editor.putString("userRole", role)
        editor.apply()

        val intent = if (role == "vendor") {
            Intent(this, VendorDashboardActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !show
        binding.btnGoogleSignIn.isEnabled = !show
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                validateAndLogin()
            }
            .show()
    }
}