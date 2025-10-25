package com.example.brainwest_android.ui.parent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val navInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // padding bawah = max antara nav bar & keyboard
            v.setPadding(0, systemBars.top, 0, maxOf(imeInsets.bottom, navInsets.bottom))

            insets
        }

        val openDetailDirect = intent.getBooleanExtra("open_detail_direct", false)
        val productId = intent.getIntExtra("product_id", -1)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (openDetailDirect && productId != -1) {
            Handler(Looper.getMainLooper()).post {
                navController.navigate(
                    R.id.detailFragment,
                    Bundle().apply { putInt("id", productId) }
                )
            }
        }

        window.statusBarColor = getColor(R.color.bg)
        window.navigationBarColor = getColor(R.color.bg)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false
    }
}