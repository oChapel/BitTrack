package com.example.bittrack.domain.use_case

import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.models.BtcRate
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

class GetBtcRateUseCase @Inject constructor(
    private val rateRepository: RateRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val refreshInterval = 1.hours

    suspend operator fun invoke(): BigDecimal = withContext(ioDispatcher) {
        val now = DateUtils.nowMillis()
        val localRate = rateRepository.getCachedBtcUsdRate()
        if (localRate == null || now - localRate.timestamp >= refreshInterval.inWholeMilliseconds) {
            val remoteRate = rateRepository.fetchRemoteBtcUsdRate()
            remoteRate?.let { rateRepository.cacheBtcUsdRate(BtcRate(it, now)) }
            return@withContext BigDecimal(remoteRate ?: 0.0)
        } else {
            return@withContext BigDecimal(localRate.rate)
        }
    }
}
