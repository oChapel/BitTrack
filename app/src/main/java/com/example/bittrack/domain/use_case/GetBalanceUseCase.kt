package com.example.bittrack.domain.use_case

import com.example.bittrack.core.handler.Result
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<Result<BigDecimal>> = transactionRepository.getBalance()
}
