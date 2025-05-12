package com.example.e_commerceapp.vendor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.databinding.ActivityShippingSettingsBinding
import com.example.e_commerceapp.utils.PreferenceHelper

class ShippingSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingSettingsBinding
    private lateinit var prefs: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferenceHelper(this)

        setupToolbar()
        loadShippingSettings()

        binding.btnSave.setOnClickListener {
            saveShippingSettings()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.title = "Shipping Settings"
    }

    private fun loadShippingSettings() {
        binding.switchDelivery.isChecked = prefs.getShippingDeliveryOption()
        binding.switchPickup.isChecked = prefs.getShippingPickupOption()
        binding.etFlatRate.setText(prefs.getShippingFlatRate()?.toString() ?: "")
        binding.etFreeShippingThreshold.setText(prefs.getFreeShippingThreshold()?.toString() ?: "")
    }

    private fun saveShippingSettings() {
        val delivery = binding.switchDelivery.isChecked
        val pickup = binding.switchPickup.isChecked
        val flatRate = binding.etFlatRate.text.toString().toDoubleOrNull() ?: 0.0
        val freeThreshold = binding.etFreeShippingThreshold.text.toString().toDoubleOrNull() ?: 0.0

        prefs.setShippingDeliveryOption(delivery)
        prefs.setShippingPickupOption(pickup)
        prefs.setShippingFlatRate(flatRate)
        prefs.setFreeShippingThreshold(freeThreshold)

        showToast("Shipping settings saved")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
