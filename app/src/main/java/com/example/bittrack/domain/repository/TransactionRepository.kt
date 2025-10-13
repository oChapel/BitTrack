package com.example.bittrack.domain.repository

import androidx.paging.PagingData
import com.example.bittrack.core.handler.Result
import com.example.bittrack.domain.models.Transaction
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface TransactionRepository {
    fun getPagedTransactions(): Flow<PagingData<Transaction>>
    suspend fun saveTransaction(transaction: Transaction): Result<Unit>
    fun getBalance(): Flow<Result<BigDecimal>>
}
