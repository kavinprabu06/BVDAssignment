package com.example.bvdtest.featureDashBoard.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.bvdtest.R
import com.example.bvdtest.databinding.ActivityDashBoardBinding
import com.example.bvdtest.databinding.ActivityMainBinding
import com.example.bvdtest.featureDashBoard.presentation.viewModel.FuelSiteViewModel
import com.example.bvdtest.featureMainAuthentication.presentation.view.MainLoginActivity
import com.example.bvdtest.utils.common.Resource
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private val fuelSiteViewModel:FuelSiteViewModel by viewModels()
    private lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityDashBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set up the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(com.example.bvdtest.R.id.dash_nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(com.example.bvdtest.R.id.bottomNavigationView)

        setupWithNavController(bottomNavigationView, navController)

        observeLogoutLivedata()
    }

    private fun observeLogoutLivedata(){
        fuelSiteViewModel.userLogoutResponse.observe(this, Observer {response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE

                    navigateToLoginScreen()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(applicationContext, "API Failure : " + response.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun navigateToLoginScreen(){
        startActivity(Intent(applicationContext,MainLoginActivity::class.java))
        finish()
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
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
