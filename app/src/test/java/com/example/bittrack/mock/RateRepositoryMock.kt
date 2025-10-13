package com.example.bittrack.mock

import com.example.bittrack.core.ext.filterResultNotNull
import com.example.bittrack.core.handler.Result
import com.example.bittrack.core.handler.runCatchingForResult
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import kotlin.random.Random

class RateRepositoryMock(
    initial: BtcRate? = null
) : RateRepository {

    private val _cached: MutableStateFlow<Result<BtcRate?>> = MutableStateFlow(Result.Success(initial))

    override suspend fun fetchRemoteBtcUsdRate(): Result<BigDecimal> {
        return Result.Success(
            BigDecimal(Random.nextDouble(100000.0, 120000.0))
        )
    }

    override fun getCachedBtcUsdRate(): Flow<Result<BtcRate>> = _cached.filterResultNotNull()

    override suspend fun cacheBtcUsdRate(btcRate: BtcRate): Result<Unit> {
        _cached.value = Result.Success(btcRate)
        return Result.Success(Unit)
    }

    fun emitCached(rate: BigDecimal) {
        _cached.value = runCatchingForResult {
            BtcRate(
                rate = rate.toDouble(),
                timestamp = DateUtils.nowMillis()
            )
        }
    }
}
