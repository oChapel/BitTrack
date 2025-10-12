package com.example.bittrack.domain.use_case

import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<BigDecimal> {
        return transactionRepository.getBalance()
            .map { BigDecimal.valueOf(it) }
    }
}
