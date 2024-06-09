package com.example.bvdtest.featureDashBoard.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.bvdtest.R
import com.example.bvdtest.featureDashBoard.presentation.viewModel.FuelSiteViewModel
import com.example.bvdtest.featureMainAuthentication.presentation.view.MainLoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private val fuelSiteViewModel:FuelSiteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.bvdtest.R.layout.activity_dash_board)

        // Set up the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(com.example.bvdtest.R.id.dash_nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(com.example.bvdtest.R.id.bottomNavigationView)

        setupWithNavController(bottomNavigationView, navController)

//        bottomNavigationView.setOnItemSelectedListener { item ->
//
//            when (item.itemId) {
//                R.id.logoutFragment -> {
//                    showDialogBox()
//                    true
//
//                }
//
//                else -> {
//                    onNavDestinationSelected(item, navController)
//                    true
//                }
//
//
//            }
//
//        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showDialogBox()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showDialogBox() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure to Logout?")
            .setCancelable(false)
            .setPositiveButton("Logout") { dialog, which ->
                fuelSiteViewModel.logoutUser()
                startActivity(Intent(applicationContext,MainLoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
