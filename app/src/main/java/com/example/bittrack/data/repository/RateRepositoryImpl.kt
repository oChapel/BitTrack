package com.example.bittrack.data.repository

import com.example.bittrack.data.local.datastore.BtcRateStore
import com.example.bittrack.data.network.CoinCapApi
import com.example.bittrack.domain.repository.RateRepository
import com.example.bittrack.proto.BtcRate
import kotlinx.coroutines.flow.first

class RateRepositoryImpl(
    private val coinCapApi: CoinCapApi,
    private val btcRateStore: BtcRateStore
) : RateRepository {

    override suspend fun fetchRemoteBtcUsdRate(): Double? {
        return coinCapApi.getBitcoinPrice().data.priceUsd.toDoubleOrNull()
    }

    override suspend fun getCachedBtcUsdRate(): Double {
        return btcRateStore.btcRate.first().rate
    }

    override suspend fun cacheBtcUsdRate(rate: Double, timestamp: Long) {
        btcRateStore.setBtcRate(
            BtcRate.newBuilder()
                .setRate(rate)
                .setTimestamp(timestamp)
                .build()
        )
    }
}
