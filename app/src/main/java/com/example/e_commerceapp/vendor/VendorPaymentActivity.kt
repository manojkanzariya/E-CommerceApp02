package com.example.e_commerceapp.vendor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.databinding.ActivityVendorPaymentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VendorPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorPaymentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupToolbar()
        loadPaymentDetails()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Payment Setup"
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun loadPaymentDetails() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("vendor_payments").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    binding.etUpiId.setText(document.getString("upiId") ?: "")
                    binding.etBankAccountNumber.setText(document.getString("accountNumber") ?: "")
                    binding.etBankName.setText(document.getString("bankName") ?: "")
                    binding.etAccountHolderName.setText(document.getString("accountHolderName") ?: "")
                    binding.etIfscCode.setText(document.getString("ifscCode") ?: "")

                    // Set checkbox states
                    binding.cbUpi.isChecked = document.getBoolean("upiEnabled") ?: false
                    binding.cbBankTransfer.isChecked = document.getBoolean("bankTransferEnabled") ?: false
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load payment details", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            savePaymentDetails()
        }

        binding.cbUpi.setOnCheckedChangeListener { _, isChecked ->
            binding.tilUpiId.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.cbBankTransfer.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if (isChecked) View.VISIBLE else View.GONE
            binding.tilBankAccountNumber.visibility = visibility
            binding.tilBankName.visibility = visibility
            binding.tilAccountHolderName.visibility = visibility
            binding.tilIfscCode.visibility = visibility
        }
    }

    private fun savePaymentDetails() {
        val upiId = binding.etUpiId.text.toString().trim()
        val accountNumber = binding.etBankAccountNumber.text.toString().trim()
        val bankName = binding.etBankName.text.toString().trim()
        val accountHolderName = binding.etAccountHolderName.text.toString().trim()
        val ifscCode = binding.etIfscCode.text.toString().trim()
        val upiEnabled = binding.cbUpi.isChecked
        val bankTransferEnabled = binding.cbBankTransfer.isChecked

        // Validate UPI ID if enabled
        if (upiEnabled && !isValidUpiId(upiId)) {
            binding.tilUpiId.error = "Enter a valid UPI ID"
            return
        } else {
            binding.tilUpiId.error = null
        }

        // Validate bank details if enabled
        if (bankTransferEnabled) {
            if (accountNumber.isEmpty()) {
                binding.tilBankAccountNumber.error = "Account number required"
                return
            } else {
                binding.tilBankAccountNumber.error = null
            }

            if (bankName.isEmpty()) {
                binding.tilBankName.error = "Bank name required"
                return
            } else {
                binding.tilBankName.error = null
            }

            if (accountHolderName.isEmpty()) {
                binding.tilAccountHolderName.error = "Account holder name required"
                return
            } else {
                binding.tilAccountHolderName.error = null
            }

            if (ifscCode.isEmpty() || !isValidIfsc(ifscCode)) {
                binding.tilIfscCode.error = "Enter valid IFSC code"
                return
            } else {
                binding.tilIfscCode.error = null
            }
        }

        val userId = auth.currentUser?.uid ?: return
        val paymentData = hashMapOf(
            "upiId" to upiId,
            "upiEnabled" to upiEnabled,
            "accountNumber" to accountNumber,
            "bankName" to bankName,
            "accountHolderName" to accountHolderName,
            "ifscCode" to ifscCode,
            "bankTransferEnabled" to bankTransferEnabled,
            "lastUpdated" to System.currentTimeMillis()
        )

        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.isEnabled = false

        db.collection("vendor_payments").document(userId)
            .set(paymentData)
            .addOnSuccessListener {
                Toast.makeText(this, "Payment details saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save payment details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                binding.progressBar.visibility = View.GONE
                binding.btnSave.isEnabled = true
            }
    }

    private fun isValidUpiId(upiId: String): Boolean {
        // Basic UPI ID validation (username@upi)
        return upiId.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+$"))
    }

    private fun isValidIfsc(ifscCode: String): Boolean {
        // Basic IFSC validation (4 letters + 7 digits)
        return ifscCode.matches(Regex("^[A-Z]{4}0[A-Z0-9]{6}$"))
    }
}