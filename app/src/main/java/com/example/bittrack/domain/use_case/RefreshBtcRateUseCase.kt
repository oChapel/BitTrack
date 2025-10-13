package com.example.bittrack.domain.use_case

import com.example.bittrack.core.handler.Result
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration

class RefreshBtcRateUseCase @Inject constructor(
    private val rateRepository: RateRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(refreshInterval: Duration): Result<Unit> = withContext(ioDispatcher) {
        val now = DateUtils.nowMillis()
        val localRateResult = rateRepository.getCachedBtcUsdRate().firstOrNull()
        val localRate = (localRateResult as? Result.Success)?.data
        val needRefresh = localRate == null || now - localRate.timestamp >= refreshInterval.inWholeMilliseconds
        if (!needRefresh) return@withContext Result.Success(Unit)

        return@withContext when(val remoteRateResult = rateRepository.fetchRemoteBtcUsdRate()) {
            is Result.Success -> {
                val btcRate = BtcRate(remoteRateResult.data.toDouble(), now)
                rateRepository.cacheBtcUsdRate(btcRate)
            }
            is Result.Error -> Result.Error(remoteRateResult.error)
        }
    }
}
