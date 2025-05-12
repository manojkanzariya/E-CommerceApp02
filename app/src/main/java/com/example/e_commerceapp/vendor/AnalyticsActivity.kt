package com.example.e_commerceapp.vendor

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityAnalyticsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyticsBinding
    private lateinit var salesChart: BarChart
    private lateinit var ratingChart: BarChart
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        salesChart = findViewById(R.id.chartSalesRevenue)
        ratingChart = findViewById(R.id.chartProductRatings)

        setupToolbar()
        loadSalesAndRevenueData()
        loadProductRatings()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Sales Analytics"
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun loadSalesAndRevenueData() {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val productCountMap = mutableMapOf<String, Int>()

                for (document in result) {
                    val items = document.get("items") as? List<Map<String, Any>> ?: continue
                    for (item in items) {
                        val productName = item["productName"] as? String ?: continue
                        productCountMap[productName] = productCountMap.getOrDefault(productName, 0) + 1
                    }
                }

                val entries = productCountMap.entries.sortedByDescending { it.value }
                val barEntries = entries.mapIndexed { index, entry -> BarEntry(index.toFloat(), entry.value.toFloat()) }
                val labels = entries.map { it.key }

                val dataSet = BarDataSet(barEntries, "Top Ordered Products")
                dataSet.color = Color.BLUE
                salesChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                salesChart.data = BarData(dataSet)
                salesChart.invalidate()
            }
    }

    private fun loadProductRatings() {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val ratingMap = mutableMapOf<String, Pair<Float, Int>>() // product -> (totalRating, count)

                for (document in result) {
                    val name = document.getString("name") ?: continue
                    val rating = document.getDouble("rating")?.toFloat() ?: continue
                    val count = document.getLong("ratingCount")?.toInt() ?: 1

                    ratingMap[name] = Pair(rating, count)
                }

                val avgRatings = ratingMap.mapValues { it.value.first / it.value.second }
                val sortedRatings = avgRatings.entries.sortedByDescending { it.value }

                val barEntries = sortedRatings.mapIndexed { index, entry -> BarEntry(index.toFloat(), entry.value) }
                val labels = sortedRatings.map { it.key }

                val dataSet = BarDataSet(barEntries, "Top Rated Products")
                dataSet.color = Color.MAGENTA
                ratingChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                ratingChart.data = BarData(dataSet)
                ratingChart.invalidate()
            }
    }
}