package com.example.bittrack.domain.repository

import com.example.bittrack.domain.models.BtcRate
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    suspend fun fetchRemoteBtcUsdRate(): Double?
    fun getCachedBtcUsdRate(): Flow<BtcRate>
    suspend fun cacheBtcUsdRate(btcRate: BtcRate)
}
