package com.example.bittrack.domain.repository

import com.example.bittrack.domain.models.BtcRate

interface RateRepository {
    suspend fun fetchRemoteBtcUsdRate(): Double?
    suspend fun getCachedBtcUsdRate(): BtcRate?
    suspend fun cacheBtcUsdRate(btcRate: BtcRate)
}
