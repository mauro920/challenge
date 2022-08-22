package com.example.talana.utils

//Sealed class who Return a Result, or a failure, and stay in a Loading, while search for the data required.
sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val exception: Exception): Resource<Nothing>()
}
