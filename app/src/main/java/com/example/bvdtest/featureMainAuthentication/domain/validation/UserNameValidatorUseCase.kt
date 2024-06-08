package com.example.bvdtest.featureMainAuthentication.domain.validation

import javax.inject.Inject


class UserNameValidatorUseCase @Inject constructor() : Validator<String> {
    override fun validate(value: String): ValidationResult {
        return if (value.isNotEmpty() && value.length > 3){
            ValidationResult(successful = true)
        }
        else{
            ValidationResult(successful = false, "Username Should not be Empty or less than 3 characters")
        }
    }
}