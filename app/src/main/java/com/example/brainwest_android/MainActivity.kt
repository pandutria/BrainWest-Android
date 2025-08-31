package com.example.brainwest_android

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.brainwest_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavbar) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, 0)

            (v.layoutParams as? ViewGroup.MarginLayoutParams)?.let { lp ->
                lp.bottomMargin = 0
                v.layoutParams = lp
            }
            insets
        }

        window.statusBarColor = getColor(R.color.bg)
        window.navigationBarColor = getColor(R.color.bg)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false

        val bottomNav = binding.bottomNavbar

        bottomNav.selectedItemId = R.id.homeMenu

        bottomNav.setOnItemSelectedListener { item ->
            moveIndicatorTo(item.itemId)
            true
        }

        bottomNav.doOnLayout {
            moveIndicatorTo(bottomNav.selectedItemId)


            val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
            navController = navHost.navController

            bottomNav.setOnItemSelectedListener { item ->
                moveIndicatorTo(item.itemId)
                when (item.itemId) {
                    R.id.homeMenu -> {
                        navController.navigate(R.id.homeFragment)
                        true
                    }
                    R.id.eduMenu -> {
                        navController.navigate(R.id.educationFragment)
                        true
                    }
//                    R.id.communityMenu -> {
//                        navController.navigate(R.id.communityFragment)
//                        true
//                    }
//                    R.id.profileMenu -> {
//                        navController.navigate(R.id.profileFragment)
//                        true
//                    }
                    else -> false
                }
            }


            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> {
                        binding.frameNavbar.visibility = View.VISIBLE
//                        binding.frameNavbar.doOnLayout {
//                            moveIndicatorTo(R.id.homeMenu)
//                        }
                    }
                    R.id.educationFragment -> {
                        binding.frameNavbar.visibility = View.VISIBLE
//                        binding.frameNavbar.doOnLayout {
//                            moveIndicatorTo(R.id.eduMenu)
//                        }
                    }

                    else -> {
                        binding.frameNavbar.visibility = View.GONE
                    }

                }
            }
        }
    }
    private fun moveIndicatorTo(itemId: Int) {
        val index = when (itemId) {
            R.id.homeMenu -> 0
            R.id.eduMenu -> 1
            R.id.communityMenu -> 2
            R.id.profileMenu -> 3
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
}