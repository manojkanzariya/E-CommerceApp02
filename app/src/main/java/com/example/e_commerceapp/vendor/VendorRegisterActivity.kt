package com.example.e_commerceapp.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.databinding.ActivityVendorRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class VendorRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        setupListeners()
    }

    private fun setupListeners() {
        binding.registerBtn.setOnClickListener {
            registerVendor()
        }
    }

    private fun registerVendor() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (!validateInputs(email, password, confirmPassword)) return

        binding.progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Create user document with roles array
                        createUserDocument(user.uid, email) {
                            val intent = Intent(this, VendorDetailsActivity::class.java).apply {
                                putExtra("uid", user.uid)
                                putExtra("email", email)
                                putExtra("isNewRegistration", true)
                            }
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun createUserDocument(uid: String, email: String, onComplete: () -> Unit) {
        val userData = hashMapOf(
            "email" to email,
            "roles" to arrayListOf("vendor"), // Initialize with vendor role
            "currentRole" to "vendor",
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(uid)
            .set(userData)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to create user profile: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun validateInputs(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (email.isEmpty()) {
            binding.email.error = "Email is required"
            return false
        }

        if (password.isEmpty()) {
            binding.password.error = "Password is required"
            return false
        }

        if (password.length < 6) {
            binding.password.error = "Password must be at least 6 characters"
            return false
        }

        if (password != confirmPassword) {
            binding.confirmPassword.error = "Passwords do not match"
            return false
        }

        return true
    }
}