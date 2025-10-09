package com.example.bittrack.domain.repository

interface RateRepository {
    suspend fun fetchRemoteBtcUsdRate(): Double?
    suspend fun getCachedBtcUsdRate(): Double
    suspend fun cacheBtcUsdRate(rate: Double, timestamp: Long)
}
