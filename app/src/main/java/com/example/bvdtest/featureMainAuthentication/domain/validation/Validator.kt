package com.example.bvdtest.featureMainAuthentication.domain.validation

interface Validator<T> {
    fun validate(value : T) : ValidationResult
}