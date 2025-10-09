package com.example.bittrack.domain.repository

import androidx.paging.PagingData
import com.example.bittrack.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getPagedTransactions(pageSize: Int): Flow<PagingData<Transaction>>
    suspend fun saveTransaction(transaction: Transaction)
}
