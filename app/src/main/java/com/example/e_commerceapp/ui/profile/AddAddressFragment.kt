package com.example.e_commerceapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.databinding.FragmentAddAddressBinding

class AddAddressFragment : Fragment() {
    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveAddress.setOnClickListener {
            saveAddress()
        }
    }

    private fun saveAddress() {
        val name = binding.etFullName.text.toString().trim()
        val phone = binding.etPhone.text.trim()
        val address = binding.etAddress.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val pincode = binding.etPincode.text.trim()

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Save address (you can store this in SharedPreferences, Firebase, or local database)
        UserRepository.saveAddress(name, phone.toString(), address, city, pincode.toString())

        // Navigate back to payment
        findNavController().navigate(R.id.action_addAddressFragment_to_paymentFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
