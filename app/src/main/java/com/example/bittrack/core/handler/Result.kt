package com.example.bittrack.core.handler

import com.example.bittrack.core.ext.toDomainError

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error<out D>(val error: DomainError) : Result<D>
}

inline fun <R> runCatchingForResult(block: () -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (ex: Throwable) {
        Result.Error(ex.toDomainError())
    }
}
