package com.example.bittrack.data.repository

import com.example.bittrack.data.local.datastore.BtcRateStore
import com.example.bittrack.data.mappers.toDomain
import com.example.bittrack.data.mappers.toProto
import com.example.bittrack.data.network.CoinCapApi
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RateRepositoryImpl(
    private val coinCapApi: CoinCapApi,
    private val btcRateStore: BtcRateStore
) : RateRepository {

    override suspend fun fetchRemoteBtcUsdRate(): Double? {
        return coinCapApi.getBitcoinPrice().data.priceUsd.toDoubleOrNull()
    }

    override fun getCachedBtcUsdRate(): Flow<BtcRate> {
        return btcRateStore.btcRate
            .map { it.toDomain() }
    }

    override suspend fun cacheBtcUsdRate(btcRate: BtcRate) {
        btcRateStore.setBtcRate(btcRate.toProto())
    }
}
