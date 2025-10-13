package com.example.bittrack.domain.repository

import com.example.bittrack.domain.models.BtcRate
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import com.example.bittrack.core.handler.Result

interface RateRepository {
    suspend fun fetchRemoteBtcUsdRate(): Result<BigDecimal>
    fun getCachedBtcUsdRate(): Flow<Result<BtcRate>>
    suspend fun cacheBtcUsdRate(btcRate: BtcRate): Result<Unit>
}
