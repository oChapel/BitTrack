package com.example.bittrack.domain.use_case

import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class GetBtcRateUseCase @Inject constructor(
    private val rateRepository: RateRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<BigDecimal> {
        return rateRepository.getCachedBtcUsdRate()
            .map { BigDecimal(it.rate) }
            .flowOn(ioDispatcher)
    }
}
