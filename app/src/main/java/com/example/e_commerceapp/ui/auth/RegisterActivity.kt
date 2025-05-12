package com.example.e_commerceapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.User
import com.example.e_commerceapp.databinding.ActivityRegisterBinding
import com.example.e_commerceapp.data.repository.FirebaseAuthManager
import com.example.e_commerceapp.ui.home.MainActivity
import com.example.e_commerceapp.vendor.VendorDashboardActivity
import com.example.e_commerceapp.vendor.VendorRegisterActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authManager = FirebaseAuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            validateAndRegister()
        }

        binding.tvLogin.setOnClickListener {
            navigateToLogin()
        }

        binding.tvBecomeVendor.setOnClickListener {
            val intent = Intent(this, VendorRegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateAndRegister() {
        val name = binding.tilName.editText?.text.toString().trim()
        val email = binding.tilEmail.editText?.text.toString().trim()
        val password = binding.tilPassword.editText?.text.toString().trim()

        // Clear previous errors
        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null

        when {
            name.isEmpty() -> {
                binding.tilName.error = "Please enter your name"
                binding.tilName.requestFocus()
            }
            email.isEmpty() -> {
                binding.tilEmail.error = "Please enter your email"
                binding.tilEmail.requestFocus()
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Please enter a valid email"
                binding.tilEmail.requestFocus()
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Please enter a password"
                binding.tilPassword.requestFocus()
            }
            password.length < 6 -> {
                binding.tilPassword.error = "Password must be at least 6 characters"
                binding.tilPassword.requestFocus()
            }
            else -> {
                performRegistration(name, email, password)
            }
        }
    }

    private fun performRegistration(name: String, email: String, password: String) {
        showLoading(true)

        authManager.register(name, email, password,
            onSuccess = {
                showLoading(false)

                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    val db = FirebaseFirestore.getInstance()
                    val userMap = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "roles" to arrayListOf("customer"), // Store as array
                        "currentRole" to "customer" // Add currentRole field
                    )

                    db.collection("users").document(uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            // Save locally
                            val user = User(
                                name = name,
                                email = email,
                                profileImage = "",
                                address = null
                            )
                            com.example.e_commerceapp.data.repository.UserRepository.init(applicationContext)
                            com.example.e_commerceapp.data.repository.UserRepository.setUser(user)

                            showSuccess("Registration successful!")
                            navigateToMain()
                        }
                        .addOnFailureListener { e ->
                            showError("Failed to save user data: ${e.message}")
                        }
                } else {
                    showError("User ID is null")
                }
            },
            onFailure = { errorMessage ->
                showLoading(false)
                showError(errorMessage)
            }
        )
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !show
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                validateAndRegister()
            }
            .show()
    }

    private fun showSuccess(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(resources.getColor(R.color.green_success))
            .show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun navigateToMain() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener {
                Intent(this, MainActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .addOnFailureListener {
                showError("Failed to fetch user role: ${it.message}")
            }
    }
}