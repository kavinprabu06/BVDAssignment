package com.example.bvdtest.featureMainAuthentication.domain.validation

import javax.inject.Inject

class PassWordValidatorUseCase @Inject constructor() : Validator<String> {
    override fun validate(value: String): ValidationResult {
        return if (value.isNotEmpty() && value.length > 3){
            ValidationResult(successful = true)
        }
        else{
            ValidationResult(successful = false, "Password should not be empty or less than 3 characters")
        }
    }
}