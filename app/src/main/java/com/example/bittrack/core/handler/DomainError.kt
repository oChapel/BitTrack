package com.example.bittrack.core.handler

sealed class DomainError {
    data class Network(val cause: Throwable? = null) : DomainError()
    data class Server(val code: Int, val body: String? = null) : DomainError()
    data class Database(val cause: Throwable? = null) : DomainError()
    data class Serialization(val cause: Throwable? = null) : DomainError()
    data class Unknown(val cause: Throwable? = null) : DomainError()
}
