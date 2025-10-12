package com.example.bittrack.domain.use_case

import androidx.paging.PagingData
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(): Flow<PagingData<Transaction>> {
        return transactionRepository.getPagedTransactions()
    }
}
