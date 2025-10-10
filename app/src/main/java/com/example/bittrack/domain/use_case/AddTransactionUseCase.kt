package com.example.bittrack.domain.use_case

import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(transaction: Transaction) = withContext(dispatcher) {
        transactionRepository.saveTransaction(transaction)
    }
}
