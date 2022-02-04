package com.davidcastella.features.productlist.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.davidcastella.features.productlist.databinding.ActivityProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    private val binding: ActivityProductListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    private val navController: NavController? by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.productListFragmentContainer.id)
        navHostFragment?.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController?.let { setupActionBarWithNavController(it) }
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: false
}
