package com.example.e_commerceapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Category
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.CartManager
import com.example.e_commerceapp.data.repository.ProductRepository
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.ui.adapter.CategoryAdapter
import com.example.e_commerceapp.ui.adapter.ProductAdapter
import com.example.e_commerceapp.ui.cart.CartFragment
import com.example.e_commerceapp.ui.product.ProductDetailsFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var allProducts: List<Product> = listOf()
    private var filteredProducts: List<Product> = listOf()
    private val productRepository = ProductRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        loadSampleData()
       // fetchCategoriesFromFirestore()
        loadProductsFromFirestore()

        binding.fabCart.setOnClickListener {

            startActivity(Intent(requireContext(), CartFragment::class.java))
        }
    }

    private fun setupRecyclerViews() {
        // Setup Category Adapter
        categoryAdapter = CategoryAdapter { category ->
            filterByCategory(category.name)
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            setHasFixedSize(true)
        }

        // Setup Product Adapter
        productAdapter = ProductAdapter(
            onItemClick = { product ->
                val productDetailsFragment = ProductDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("product", product)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, productDetailsFragment)
                    .addToBackStack("product_details") // Optional: Add to back stack
                    .commit()
            },
            onAddToCartClick = { product ->
                lifecycleScope.launch {
                    CartManager.addToCart(product)
                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            }
        )
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadSampleData() {
        val categories = listOf(
            Category(1, "All", R.drawable.ic_all, 0),
            Category(2, "Electronics", R.drawable.ic_electronics, 42),
            Category(3, "Fashion", R.drawable.ic_fashion, 22),
            Category(4, "Home & Kitchen", R.drawable.ic_home_kitchen, 12),
            Category(5, "Books", R.drawable.ic_books, 33),
            Category(6, "Toys", R.drawable.ic_toys, 10)
        )
        categoryAdapter.submitList(categories)
    }
    /*private fun fetchCategoriesFromFirestore() {
        firestore.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                val categories = result.toObjects<Category>()
                categoryAdapter.submitList(categories)
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error fetching categories", exception)
            }
    }*/
    private fun loadProductsFromFirestore() {
        productRepository.getProducts { products ->
            allProducts = products
            filteredProducts = allProducts
            productAdapter.submitList(filteredProducts)
        }
    }

    private fun filterByCategory(category: String) {
        filteredProducts = if (category == "All") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
        productAdapter.submitList(filteredProducts)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search products..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterProducts(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterProducts(query: String) {
        filteredProducts = allProducts.filter { it.name.contains(query, ignoreCase = true) }
        productAdapter.submitList(filteredProducts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
