package com.example.e_commerceapp.vendor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.databinding.ActivityStoreSettingsBinding
import com.example.e_commerceapp.utils.PreferenceHelper

class StoreSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreSettingsBinding
    private lateinit var prefs: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferenceHelper(this)

        setupToolbar()
        loadStoreData()

        binding.btnSave.setOnClickListener {
            saveStoreData()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.title = "Store Information"
    }

    private fun loadStoreData() {
        binding.etStoreName.setText(prefs.getStoreName() ?: "")
        binding.etStoreDescription.setText(prefs.getStoreDescription() ?: "")
        binding.etStoreContact.setText(prefs.getStoreContact() ?: "")
    }

    private fun saveStoreData() {
        val name = binding.etStoreName.text.toString().trim()
        val description = binding.etStoreDescription.text.toString().trim()
        val contact = binding.etStoreContact.text.toString().trim()

        if (name.isEmpty()) {
            showToast("Store name is required")
            return
        }

        prefs.setStoreName(name)
        prefs.setStoreDescription(description)
        prefs.setStoreContact(contact)

        showToast("Store information saved")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
