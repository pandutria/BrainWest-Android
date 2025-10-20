package com.example.brainwest_android.ui.parent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.commit
import com.example.brainwest_android.R
import com.example.brainwest_android.ui.community.chating.ChatingCommunityFragment
import com.example.brainwest_android.ui.community.history.HistoryCommunityFragment

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_community)
        //    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val isDetail = intent.getBooleanExtra("isDetail", false)

        val fragment = if (isDetail) {
            ChatingCommunityFragment()
        } else {
            HistoryCommunityFragment()
        }

        supportFragmentManager.commit {
            replace(R.id.navHostFragment, fragment)
        }

        window.navigationBarColor = getColor(R.color.bg)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val navInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(0, systemBars.top, 0, maxOf(imeInsets.bottom, navInsets.bottom))

            insets
        }
    }
}