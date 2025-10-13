package com.example.bittrack.data.repository

import com.example.bittrack.core.ext.mapToResult
import com.example.bittrack.core.handler.Result
import com.example.bittrack.core.handler.runCatchingForResult
import com.example.bittrack.data.local.datastore.BtcRateStore
import com.example.bittrack.data.mappers.toDomain
import com.example.bittrack.data.mappers.toProto
import com.example.bittrack.data.network.CoinCapApi
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class RateRepositoryImpl(
    private val coinCapApi: CoinCapApi,
    private val btcRateStore: BtcRateStore
) : RateRepository {

    override suspend fun fetchRemoteBtcUsdRate(): Result<BigDecimal> = runCatchingForResult {
        BigDecimal(coinCapApi.getBitcoinPrice().data.priceUsd)
    }

    override fun getCachedBtcUsdRate(): Flow<Result<BtcRate>> {
        return btcRateStore.btcRate
            .map { it.toDomain() }
            .mapToResult()
    }

    override suspend fun cacheBtcUsdRate(btcRate: BtcRate): Result<Unit> = runCatchingForResult {
        btcRateStore.setBtcRate(btcRate.toProto())
    }
}
