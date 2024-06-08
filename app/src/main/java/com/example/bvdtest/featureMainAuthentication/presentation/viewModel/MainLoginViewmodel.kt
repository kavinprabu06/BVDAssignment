package com.example.bvdtest.featureMainAuthentication.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bvdtest.featureMainAuthentication.domain.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.featureMainAuthentication.domain.validation.PassWordValidatorUseCase
import com.example.bvdtest.featureMainAuthentication.domain.validation.UserNameValidatorUseCase
import com.example.bvdtest.featureMainAuthentication.domain.validation.ValidationResult
import com.example.bvdtest.utils.common.Resource
import kotlinx.coroutines.launch


@HiltViewModel
class MainLoginViewmodel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val userNameValidatorUseCase: UserNameValidatorUseCase,
    private val passwordValidatorUseCase: PassWordValidatorUseCase,
) : ViewModel() {


    private val _loginResult = MutableLiveData<Resource<LoginUserResponse>>()
    val loginResult get() = _loginResult

    private val _validationUsernameResult = MutableLiveData<ValidationResult>()
    val validtaionUsernameResult = _validationUsernameResult

    private val _validationPasswordResult = MutableLiveData<ValidationResult>()
    val validtaionPasswordResult = _validationPasswordResult

    fun verifyUser(username: String, passWord: String) {
        viewModelScope.launch {

            val userNameValidationResult = userNameValidatorUseCase.validate(username)
            val passWordValidationResult = passwordValidatorUseCase.validate(passWord)

//            if (!userNameValidationResult.successful && !passWordValidationResult.successful){
//                _validationResult.value = ValidationResult(
//                    successful = false,
//                    usernameError = true,
//                    passwordError = true,
//                    errorMessage = "Both username and password have errors"
//                )
//                return@launch
//            }

            if (!userNameValidationResult.successful) {
                validtaionUsernameResult.value = userNameValidationResult
                return@launch
            }

            if (!passWordValidationResult.successful) {
                validtaionPasswordResult.value = passWordValidationResult
                return@launch
            }

            //Indicates the Livedata in UI that Validtaion is Successfull
            validtaionUsernameResult.value = ValidationResult(true)
            validtaionPasswordResult.value = ValidationResult(true)

            // Set the loading state
            Resource.Loading()

            val response = loginUserUseCase.verifyUser(username, passWord)
            _loginResult.value = response

            Log.d("MainLoginViewModel", _loginResult.value.toString())
        }
    }


}