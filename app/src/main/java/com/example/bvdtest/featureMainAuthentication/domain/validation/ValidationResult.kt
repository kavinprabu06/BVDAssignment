package com.example.bvdtest.featureMainAuthentication.domain.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
//                            val usernameError: Boolean = false,
//                            val passwordError: Boolean = false
)

