package com.example.bvdtest.featureMainAuthentication.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.bvdtest.R
import com.example.bvdtest.featureDashBoard.presentation.view.DashBoardActivity
import com.example.bvdtest.featureMainAuthentication.presentation.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        lifecycleScope.launch{
            delay(3000)

            if (splashViewModel.checkValidityToken()){
                Toast.makeText(applicationContext,"Valid "+splashViewModel.checkValidityToken(),Toast.LENGTH_SHORT).show()

                startActivity(Intent(applicationContext,DashBoardActivity::class.java))
            }
            else{
                Toast.makeText(applicationContext,"inValid "+splashViewModel.checkValidityToken(),Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,MainLoginActivity::class.java))
            }
            finish()
        }





    }
}