package com.example.bvdtest.featureMainAuthentication.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bvdtest.databinding.ActivityMainBinding
import com.example.bvdtest.featureDashBoard.presentation.view.DashBoardActivity
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
            ActivityMainBinding.inflate(layoutInflater) // Inflate the layout using view binding
        val view = binding.root
        setContentView(view)


        binding.etUserName.addTextChangedListener(textWatcher)
        binding.etPassWord.addTextChangedListener(textWatcher)

        binding.btLogin.setOnClickListener {

            viewModel.verifyUser("testapp1", "Testapp1@123")
//            viewModel.verifyUser("testapp", "")
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

                is Resource.Success -> {
                    Toast.makeText(this, "" + result.data.email, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                    finish()
                }

                is Resource.Failure -> {
                    Toast.makeText(this, "" + result.message, Toast.LENGTH_SHORT).show()

                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

            }

        })
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Enable or disable Login button based on EditText fields
            val userName = binding.etUserName.text.toString().trim()
            val password = binding.etPassWord.text.toString().trim()
            binding.btLogin.isEnabled = userName.isNotEmpty() && password.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}