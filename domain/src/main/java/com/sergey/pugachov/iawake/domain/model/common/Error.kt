package com.sergey.pugachov.iawake.domain.model.common

sealed class Error {
    object Http : Error()
    data class Unknown(val throwable: Throwable) : Error()
}