package com.example.bvdtest.utils.common

sealed class Resource<out T> {

    class Success<T>(val data:T) : Resource<T>()
    class Failure(val message: String) : Resource<Nothing>()
    class Loading() : Resource<Nothing>()

}