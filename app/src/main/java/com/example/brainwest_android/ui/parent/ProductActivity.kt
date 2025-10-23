package com.example.brainwest_android.ui.parent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.ActivityProductBinding
import com.example.brainwest_android.ui.community.chating.ChatingCommunityFragment
import com.example.brainwest_android.ui.community.history.HistoryCommunityFragment
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

        val isDetail = intent.getBooleanExtra("isDetail", false)

        val fragment = if (isDetail) {
            ListProductFragment()
        } else {
            ListProductFragment()
        }

        supportFragmentManager.commit {
            replace(R.id.navHostFragment, fragment)
        }
    }
}