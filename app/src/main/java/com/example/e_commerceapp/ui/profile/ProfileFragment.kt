package com.example.e_commerceapp.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.model.User
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.databinding.FragmentProfileBinding
import com.example.e_commerceapp.ui.adapter.OrderHistoryAdapter
import com.example.e_commerceapp.ui.auth.LoginActivity
import com.example.e_commerceapp.ui.order.OrderDetailsFragment
import com.example.e_commerceapp.vendor.VendorDashboardActivity
import com.example.e_commerceapp.vendor.VendorDetailsActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var firestore: FirebaseFirestore
    private var hasVendorRole = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserRepository.init(requireContext())

        firestore = FirebaseFirestore.getInstance()
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        setupRecyclerView()
        setupClickListeners()
        checkUserRoles()
        observeViewModel()
    }

    private fun checkUserRoles() {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        firestore.collection("users").document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val roles = document.get("roles") as? List<*> ?: listOf("customer")
                    hasVendorRole = roles.contains("vendor")
                    setupDrawer(hasVendorRole)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to check user roles", Toast.LENGTH_SHORT).show()
                setupDrawer(false)
            }
    }

    private fun setupDrawer(hasVendorRole: Boolean) {
        binding.topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val headerView = navigationView.getHeaderView(0)
        val navUserName = headerView.findViewById<TextView>(R.id.tvNavUserName)
        val navUserEmail = headerView.findViewById<TextView>(R.id.tvNavUserEmail)
        val navProfileImage = headerView.findViewById<ShapeableImageView>(R.id.imageView)

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                navUserName.text = it.name
                navUserEmail.text = it.email

                Glide.with(this)
                    .load(it.profileImage)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(navProfileImage)
            }
        }

        // Update the vendor menu item based on role
        navigationView.menu.findItem(R.id.nav_become_vendor)?.let { menuItem ->
            menuItem.title = if (hasVendorRole) {
                getString(R.string.navigate_to_vendor_dashboard)
            } else {
                getString(R.string.become_a_vendor)
            }
            menuItem.isVisible = true
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_wishlist -> navigateToWishlist()
                R.id.nav_change_password -> navigateToChangePassword()
                R.id.nav_forgot_password -> navigateToForgotPassword()
                R.id.nav_address -> navigateToAddress()
                R.id.nav_theme_light -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.nav_theme_dark -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.nav_theme_system -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.nav_become_vendor -> handleVendorNavigation(hasVendorRole)
                R.id.nav_logout -> confirmLogout()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun handleVendorNavigation(hasVendorRole: Boolean) {
        if (hasVendorRole) {
            // Navigate to Vendor Dashboard
            startActivity(Intent(requireActivity(), VendorDashboardActivity::class.java))
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        } else {
            // Start vendor registration process
            navigateToVendorRegistration()
        }
    }

    private fun navigateToVendorRegistration() {
        val user = FirebaseAuth.getInstance().currentUser ?: run {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(requireActivity(), VendorDetailsActivity::class.java).apply {
            putExtra("uid", user.uid)
            putExtra("email", user.email)
            putExtra("CURRENT_ROLE", "customer")
            putExtra("REGISTER_AS_VENDOR", true)
        }

        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        requireActivity().recreate()
    }

    private fun navigateToWishlist() {
        findNavController().navigate(
            R.id.action_navigation_profile_to_wishlistFragment,
            null,
            navOptionsWithBackStack()
        )
    }

    private fun navigateToChangePassword() {
        findNavController().navigate(
            R.id.action_navigation_profile_to_changePasswordFragment,
            null,
            navOptionsWithBackStack()
        )
    }

    private fun navigateToForgotPassword() {
        findNavController().navigate(
            R.id.action_navigation_profile_to_forgotPasswordFragment,
            null,
            navOptionsWithBackStack()
        )
    }

    private fun navigateToAddress() {
        findNavController().navigate(
            R.id.action_navigation_profile_to_addAddressFragment,
            null,
            navOptionsWithBackStack()
        )
    }

    private fun navOptionsWithBackStack(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }

    private fun observeViewModel() {
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let { updateUserUI(it) }
        }

        viewModel.orderHistoryLiveData.observe(viewLifecycleOwner) { orders ->
            updateOrderHistoryUI(orders)
        }
    }

    private fun updateUserUI(user: User) {
        binding.tvUserName.text = user.name
        binding.tvUserEmail.text = user.email

        Glide.with(requireContext())
            .load(user.profileImage)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(binding.ivProfileImage)
    }

    private fun updateOrderHistoryUI(orders: List<Order>) {
        if (orders.isEmpty()) {
            binding.rvOrderHistory.visibility = View.GONE
            binding.tvNoOrders.visibility = View.VISIBLE
        } else {
            binding.rvOrderHistory.visibility = View.VISIBLE
            binding.tvNoOrders.visibility = View.GONE
            orderHistoryAdapter.submitList(orders)
        }
    }

    private fun setupRecyclerView() {
        orderHistoryAdapter = OrderHistoryAdapter(emptyList()) { order ->
            navigateToOrderDetails(order)
        }
        binding.rvOrderHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderHistoryAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateToOrderDetails(order: Order) {
        Log.d("ProfileFragment", "Navigating to OrderDetailsFragment with Order ID: ${order.userId}")
        val orderDetailsFragment = OrderDetailsFragment.newInstance(order.id)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, orderDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
    }

    private fun navigateToEditProfile() {
        findNavController().navigate(R.id.action_navigation_profile_to_EditProfileFragment)
    }

    private fun confirmLogout() {
        Snackbar.make(binding.root, "Are you sure you want to logout?", Snackbar.LENGTH_LONG)
            .setAction("Logout") {
                performLogout()
            }
            .show()
    }

    private fun performLogout() {
        val sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.clear()
        editor.apply()

        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserData()
        viewModel.loadOrderHistory()
        checkUserRoles() // Refresh role status when returning to fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}