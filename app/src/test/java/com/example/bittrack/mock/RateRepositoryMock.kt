package com.example.bittrack.mock

import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import java.math.BigDecimal

class RateRepositoryMock(
    initial: BtcRate? = null
) : RateRepository {

    private val _cached = MutableStateFlow(initial)

    override suspend fun fetchRemoteBtcUsdRate(): Double? = _cached.value?.rate

    override fun getCachedBtcUsdRate(): Flow<BtcRate> = _cached.filterNotNull()

    override suspend fun cacheBtcUsdRate(btcRate: BtcRate) {
        _cached.value = btcRate
    }

    fun emitCached(rate: BigDecimal) {
        _cached.value = BtcRate(rate = rate.toDouble(), timestamp = DateUtils.nowMillis())
    }
}
