package com.example.e_commerceapp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityForgotPasswordBinding
import com.example.e_commerceapp.data.repository.FirebaseAuthManager
import com.google.android.material.snackbar.Snackbar

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val authManager = FirebaseAuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            validateAndSubmit()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateAndSubmit() {
        val email = binding.etEmail.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.tilEmail.error = getString(R.string.error_email_empty)
            }
            !isValidEmail(email) -> {
                binding.tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilEmail.error = null
                sendResetEmail(email)
            }
        }
    }

    private fun sendResetEmail(email: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmit.isEnabled = false

        authManager.sendPasswordResetEmail(email,
            onSuccess = {
                binding.progressBar.visibility = View.GONE
                binding.btnSubmit.isEnabled = true
                showSuccessMessage(getString(R.string.reset_email_sent))
                binding.etEmail.text?.clear()
            },
            onFailure = { errorMessage ->
                binding.progressBar.visibility = View.GONE
                binding.btnSubmit.isEnabled = true
                showErrorMessage(errorMessage)
            }
        )
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showSuccessMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(R.color.green_success))
            .show()
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                validateAndSubmit()
            }
            .show()
    }
}