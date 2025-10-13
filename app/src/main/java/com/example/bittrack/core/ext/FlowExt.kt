package com.example.bittrack.core.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import com.example.bittrack.core.handler.Result
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.mapToResult(): Flow<Result<T>> {
    return this.map<T, Result<T>> { data -> Result.Success(data) }
        .catch { error -> emit(Result.Error(error.toDomainError())) }
}

fun <T1, T2> Flow<Result<T1>>.mapResult(transform: (T1) -> T2): Flow<Result<T2>> {
    return map { result ->
        when (result) {
            is Result.Success -> Result.Success(transform(result.data))
            is Result.Error -> Result.Error(result.error)
        }
    }
}

fun <T> Flow<Result<T?>>.filterResultNotNull(): Flow<Result<T>> = mapNotNull { result ->
    when (result) {
        is Result.Success -> result.data?.let { Result.Success(it) }
        is Result.Error -> Result.Error(result.error)
    }
}

fun <T> Flow<Result<T>>.onSuccess(
    action: suspend (T) -> Unit
): Flow<Result<T>> = onEach { result ->
    if (result is Result.Success) action.invoke(result.data)
}

fun <T1, T2, R> combineResults(
    flow: Flow<Result<T1>>,
    flow2: Flow<Result<T2>>,
    transform: suspend (a: T1, b: T2) -> R
): Flow<Result<R>> = combine(flow, flow2) { result, result2 ->
    when {
        result is Result.Error -> Result.Error(result.error)
        result2 is Result.Error -> Result.Error(result2.error)
        else -> Result.Success(
            transform(
                (result as Result.Success).data,
                (result2 as Result.Success).data
            )
        )
    }
}
