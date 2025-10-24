package com.example.brainwest_android.ui.parent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.commit
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.ActivityProductBinding
import com.example.brainwest_android.ui.community.chating.ChatingCommunityFragment
import com.example.brainwest_android.ui.community.history.HistoryCommunityFragment
import com.example.brainwest_android.ui.product.cart.CartProductFragment
import com.example.brainwest_android.ui.product.detail.DetailProductFragment
import com.example.brainwest_android.ui.product.list.ListProductFragment

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = getColor(R.color.bg)
        window.navigationBarColor = getColor(R.color.bg)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false

        val from = intent.getStringExtra("from")

        val fragment = if (from == "detail") {
            DetailProductFragment()
        } else if (from == "cart") {
            CartProductFragment()
        } else {
            ListProductFragment()
        }

        supportFragmentManager.commit {
            replace(R.id.navHostFragment, fragment)
        }
    }
}