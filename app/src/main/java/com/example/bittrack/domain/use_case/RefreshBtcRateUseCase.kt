package com.example.bittrack.domain.use_case

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
    suspend operator fun invoke(refreshInterval: Duration) = withContext(ioDispatcher) {
        val now = DateUtils.nowMillis()
        val localRate = rateRepository.getCachedBtcUsdRate().firstOrNull()
        if (localRate == null || now - localRate.timestamp >= refreshInterval.inWholeMilliseconds) {
            val remoteRate = rateRepository.fetchRemoteBtcUsdRate()
            remoteRate?.let { rateRepository.cacheBtcUsdRate(BtcRate(it, now)) }
        }
    }
}
