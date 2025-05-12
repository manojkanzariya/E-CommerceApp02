package com.example.e_commerceapp.vendor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.databinding.ActivityPaymentMethodsBinding
import com.example.e_commerceapp.utils.PreferenceHelper

class PaymentMethodsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentMethodsBinding
    private lateinit var prefs: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferenceHelper(this)

        setupToolbar()
        loadPaymentMethods()

        binding.btnSave.setOnClickListener {
            savePaymentMethods()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Payment Methods"
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun loadPaymentMethods() {
        binding.switchUpi.isChecked = prefs.isPaymentMethodEnabled("upi")
        binding.switchGooglePay.isChecked = prefs.isPaymentMethodEnabled("gpay")
        binding.switchPhonePe.isChecked = prefs.isPaymentMethodEnabled("phonepe")
        binding.switchPaytm.isChecked = prefs.isPaymentMethodEnabled("paytm")
        binding.switchCOD.isChecked = prefs.isPaymentMethodEnabled("cod")
    }

    private fun savePaymentMethods() {
        prefs.setPaymentMethodEnabled("upi", binding.switchUpi.isChecked)
        prefs.setPaymentMethodEnabled("gpay", binding.switchGooglePay.isChecked)
        prefs.setPaymentMethodEnabled("phonepe", binding.switchPhonePe.isChecked)
        prefs.setPaymentMethodEnabled("paytm", binding.switchPaytm.isChecked)
        prefs.setPaymentMethodEnabled("cod", binding.switchCOD.isChecked)

        showToast("Payment methods updated")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
