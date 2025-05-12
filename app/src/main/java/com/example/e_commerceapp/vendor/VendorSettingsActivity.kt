package com.example.e_commerceapp.vendor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityVendorSettingsBinding
import com.example.e_commerceapp.ui.auth.LoginActivity
import com.example.e_commerceapp.ui.home.MainActivity
import com.example.e_commerceapp.utils.PreferenceHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VendorSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorSettingsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var prefs: PreferenceHelper
    private lateinit var db: FirebaseFirestore
    private var hasCustomerRole = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        prefs = PreferenceHelper(this)
        db = FirebaseFirestore.getInstance()

        setupToolbar()
        checkUserRoles()
        setupClickListeners()
        loadUserData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Settings"
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun checkUserRoles() {
        val currentUser = auth.currentUser ?: return
        db.collection("users").document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val roles = document.get("roles") as? List<*> ?: listOf("vendor")
                    hasCustomerRole = roles.contains("customer")

                    // Show/hide the switch to customer option
                    binding.switchToCustomer.visibility = if (hasCustomerRole) View.VISIBLE else View.GONE
                }
            }
    }

    private fun setupClickListeners() {
        // Profile Section
        binding.profileInfo.setOnClickListener { openProfileEditor() }
        binding.changePassword.setOnClickListener { openChangePassword() }
        binding.emailPreferences.setOnClickListener { openEmailPreferences() }

        // Store Section
        binding.storeInfo.setOnClickListener { openStoreEditor() }
        binding.shippingSettings.setOnClickListener { openShippingSettings() }
        binding.vendorPayment.setOnClickListener { openPaymentMethods1() }
        binding.paymentMethods.setOnClickListener { openPaymentMethods() }

        // Account Section
        binding.switchToCustomer.setOnClickListener { switchToCustomerMode() }
        binding.logout.setOnClickListener { confirmLogout() }
        binding.deleteAccount.setOnClickListener { confirmAccountDeletion() }
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser ?: return

        // Load basic user info
        binding.tvUserName.text = currentUser.displayName ?: "Vendor"
        binding.tvUserEmail.text = currentUser.email ?: "No email set"

        // Load store info from Firestore
        db.collection("vendors").document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val storeName = document.getString("storeName") ?: "My Store"
                    binding.tvStoreName.text = storeName

                    // Load vendor image
                    val imageUrl = document.getString("imageUrl")
                    if (!imageUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_profile) // Optional placeholder image
                            .error(R.drawable.ic_error) // Optional error image
                            .into(binding.ivVendorImage)
                    } else {
                        binding.ivVendorImage.setImageResource(R.drawable.ic_profile)
                    }

                    // Save to shared preferences if needed
                    prefs.setStoreName(storeName)
                } else {
                    binding.tvStoreName.text = "My Store"
                    binding.ivVendorImage.setImageResource(R.drawable.ic_profile)
                }
            }
            .addOnFailureListener { e ->
                binding.tvStoreName.text = "My Store"
                binding.ivVendorImage.setImageResource(R.drawable.ic_profile)
                Log.e("VendorSettings", "Error loading store info", e)
            }
    }


    private fun switchToCustomerMode() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Switch to Customer Mode")
            .setMessage("Do you want to switch to customer mode?")
            .setPositiveButton("Switch") { _, _ ->
                updateCurrentRoleAndNavigate()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateCurrentRoleAndNavigate() {
        val currentUser = auth.currentUser ?: return
        db.collection("users").document(currentUser.uid)
            .update("currentRole", "customer")
            .addOnSuccessListener {
                // Update shared preferences
                val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                sharedPreferences.edit().putString("userRole", "customer").apply()

                // Navigate to main activity
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
            .addOnFailureListener { e ->
                showToast("Failed to switch mode: ${e.message}")
            }
    }

    private fun openProfileEditor() {
        val intent = Intent(this, VendorProfileActivity::class.java)
        startActivity(intent)
    }

    private fun openChangePassword() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    private fun openEmailPreferences() {
        showEmailPreferenceDialog()
    }

    private fun openStoreEditor() {
        val intent = Intent(this, StoreSettingsActivity::class.java)
        startActivity(intent)
    }

    private fun openShippingSettings() {
        val intent = Intent(this, ShippingSettingsActivity::class.java)
        startActivity(intent)
    }
    private fun openPaymentMethods1() {
        startActivity(Intent(this, VendorPaymentActivity::class.java))
    }

    private fun openPaymentMethods() {
        val intent = Intent(this, PaymentMethodsActivity::class.java)
        startActivity(intent)
    }

    private fun showEmailPreferenceDialog() {
        val options = arrayOf(
            "Order notifications",
            "Promotional emails",
            "Inventory alerts",
            "All emails",
            "No emails"
        )

        val checkedItems = booleanArrayOf(
            prefs.getEmailPreference("orders"),
            prefs.getEmailPreference("promotions"),
            prefs.getEmailPreference("inventory"),
            false, // "All emails"
            false  // "No emails"
        )

        MaterialAlertDialogBuilder(this)
            .setTitle("Email Preferences")
            .setMultiChoiceItems(options, checkedItems) { _, which, isChecked ->
                when (which) {
                    0 -> prefs.setEmailPreference("orders", isChecked)
                    1 -> prefs.setEmailPreference("promotions", isChecked)
                    2 -> prefs.setEmailPreference("inventory", isChecked)
                    3 -> if (isChecked) {
                        // Select all
                        prefs.setEmailPreference("orders", true)
                        prefs.setEmailPreference("promotions", true)
                        prefs.setEmailPreference("inventory", true)
                    }
                    4 -> if (isChecked) {
                        // Deselect all
                        prefs.setEmailPreference("orders", false)
                        prefs.setEmailPreference("promotions", false)
                        prefs.setEmailPreference("inventory", false)
                    }
                }
            }
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmLogout() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        auth.signOut()
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finishAffinity()
    }

    private fun confirmAccountDeletion() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Account")
            .setMessage("This will permanently delete your account and all store data. Continue?")
            .setPositiveButton("Delete") { _, _ ->
                showFinalConfirmation()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showFinalConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Enter your password to confirm account deletion")
            .setView(R.layout.dialog_confirm_password)
            .setPositiveButton("Delete") { dialog, _ ->
                val password = (dialog as AlertDialog).findViewById<android.widget.EditText>(R.id.etPassword)?.text.toString()
                if (password.isNotEmpty()) {
                    deleteAccount(password)
                } else {
                    showToast("Password cannot be empty")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteAccount(password: String) {
        val user = auth.currentUser
        val credential = com.google.firebase.auth.EmailAuthProvider
            .getCredential(user?.email ?: "", password)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.delete().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        prefs.clearPreferences()
                        showToast("Account deleted successfully")
                        startActivity(Intent(this, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                                finish()
                    } else {
                        showToast("Failed to delete account: ${deleteTask.exception?.message}")
                    }
                }
            } else {
                showToast("Authentication failed: ${task.exception?.message}")
            }
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadUserData() // Refresh data when returning from other activities
    }
}