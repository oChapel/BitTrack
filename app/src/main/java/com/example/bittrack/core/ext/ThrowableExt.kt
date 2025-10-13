package com.example.bittrack.core.ext

import androidx.sqlite.SQLiteException
import com.example.bittrack.core.handler.DomainError

fun Throwable.toDomainError(): DomainError = when (this) {
    is retrofit2.HttpException -> DomainError.Server(code(), response()?.errorBody()?.string())
    is java.io.IOException -> DomainError.Network(this)
    is kotlinx.serialization.SerializationException -> DomainError.Serialization(this)
    is NumberFormatException -> DomainError.Serialization(this)
    is SQLiteException -> DomainError.Database(this)
    else -> DomainError.Unknown(this)
}
