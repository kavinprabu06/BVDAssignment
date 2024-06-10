package com.example.bvdtest.featureMainAuthentication.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.bvdtest.R
import com.example.bvdtest.databinding.ActivityMainBinding
import com.example.bvdtest.featureDashBoard.presentation.view.activities.DashBoardActivity
import com.example.bvdtest.featureMainAuthentication.presentation.viewModel.MainLoginViewmodel
import com.example.bvdtest.utils.common.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainLoginViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.etUserName.addTextChangedListener(textWatcher)
        binding.etPassWord.addTextChangedListener(textWatcher)

        binding.btLogin.setOnClickListener {
            viewModel.verifyUser(
                binding.etUserName.text.toString().trim(),
                binding.etPassWord.text.toString().trim()
            )
        }

        viewModel.validtaionUsernameResult.observe(this, Observer { result ->
            if (!result.successful) {
                binding.etUserName.error = result.errorMessage
            }
        })

        viewModel.validtaionPasswordResult.observe(this, Observer { result ->
            if (!result.successful) {
                binding.etPassWord.error = result.errorMessage
            }
        })

        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is Resource.Failure -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(applicationContext, "API Failure : " + result.message, Toast.LENGTH_LONG).show()

                }

                is Resource.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                    finish()
                }

                is Resource.Loading -> {
                        binding.progressBar.visibility = ProgressBar.VISIBLE
                }


            }

        })
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Enable or disable Login button based on EditText fields
            val userName = binding.etUserName.text.toString().trim()
            val password = binding.etPassWord.text.toString().trim()
            updateButtonState(userName, password)
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState(userName: String, password: String) {
        if (userName.isNotEmpty() && password.isNotEmpty()) {
            binding.btLogin.apply {
                setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.buttonEnabledColor
                    )
                )
                isEnabled = true

            }
        } else {
            binding.btLogin.apply {
                setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.buttonDisabledColor
                    )
                )
                isEnabled = false

            }
        }
    }
}