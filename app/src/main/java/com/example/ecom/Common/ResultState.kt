package com.example.ecom.Common

sealed class ResultState<out T>{
    data class Success<out T>(val data: T) : ResultState<T>()
    data class ERROR<out T>(val message : String) : ResultState<T>()
    data object LOADING : ResultState<Nothing>()
}