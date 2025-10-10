package com.example.bittrack.domain.use_case

import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<Double> = transactionRepository.getBalance()
}
