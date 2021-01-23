package com.sergey.pugachov.iawake.domain.model.common

sealed class Result<out T : Any>{
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure<out T : Any>(val error: Error, val data: T? = null) : Result<T>()
}