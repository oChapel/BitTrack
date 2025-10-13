package com.example.bittrack.domain.use_case

import com.example.bittrack.core.ext.mapResult
import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.repository.RateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import com.example.bittrack.core.handler.Result
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import javax.inject.Inject

class GetBtcRateUseCase @Inject constructor(
    private val rateRepository: RateRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Result<BigDecimal>> {
        return rateRepository.getCachedBtcUsdRate()
            .mapResult { rate -> BigDecimal(rate.rate) }
            .flowOn(ioDispatcher)
    }
}
