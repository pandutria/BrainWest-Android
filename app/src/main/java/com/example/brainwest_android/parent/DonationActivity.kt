package com.example.brainwest_android.parent

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.ActivityDonationBinding

class DonationActivity : AppCompatActivity() {
    lateinit var binding: ActivityDonationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#0059D0")
        window.navigationBarColor = getColor(R.color.bg)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false

        val bottomNav = binding.bottomNavbar
        bottomNav.selectedItemId = R.id.donationMenu

        bottomNav.doOnLayout {
            moveIndicatorTo(bottomNav.selectedItemId)
        }

        bottomNav.setOnItemSelectedListener { item ->
            moveIndicatorTo(item.itemId)
            when (item.itemId) {
                R.id.donationMenu -> {
                    // TODO: navigate to donate fragment/activity
                    true
                }
                R.id.transactionMenu -> {
                    // TODO: navigate to history fragment/activity
                    true
                }
                else -> false
            }
        }

    }

    private fun moveIndicatorTo(itemId: Int) {
        val index = when (itemId) {
            R.id.donationMenu -> 0
            R.id.transactionMenu -> 1
            else -> 0
        }

        val bottomNav = binding.bottomNavbar
        val menuView = bottomNav.getChildAt(0) as? ViewGroup ?: return
        val itemView = menuView.getChildAt(index) ?: return

        binding.frameNavbar.post {
            val itemLoc = IntArray(2)
            val frameLoc = IntArray(2)
            itemView.getLocationOnScreen(itemLoc)
            binding.frameNavbar.getLocationOnScreen(frameLoc)

            val centerXInFrame = (itemLoc[0] - frameLoc[0]).toFloat() +
                    (itemView.width / 2f) - (binding.indicator.width / 2f)

            binding.indicator.animate()
                .x(centerXInFrame)
                .setDuration(200)
                .start()
        }
    }

//    fun showFragment(fragment1: Fragment, fragment2: Fragment, fragment3: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .add(R.id.container, fragment1, "F1")
//            .add(R.id.container, fragment2, "F2")
//            .add(R.id.container, fragment3, "F3")
//            .show(fragment1)
//            .hide(fragment2)
//            .hide(fragment3)
//            .commit()
//    }
}
