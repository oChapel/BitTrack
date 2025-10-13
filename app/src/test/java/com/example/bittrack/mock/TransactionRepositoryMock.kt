package com.example.bittrack.mock

import androidx.paging.PagingData
import com.example.bittrack.core.ext.mapToResult
import com.example.bittrack.core.handler.Result
import com.example.bittrack.core.handler.runCatchingForResult
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal

class TransactionRepositoryMock(
    initial: List<Transaction> = emptyList()
) : TransactionRepository {

    private val _transactions = mutableListOf<Transaction>().apply { addAll(initial) }
    private val _balance = MutableStateFlow(calcBalance())

    override fun getPagedTransactions(): Flow<PagingData<Transaction>> =
        flowOf(PagingData.from(_transactions.toList()))

    override suspend fun saveTransaction(transaction: Transaction): Result<Unit> =
        runCatchingForResult {
            _transactions.add(transaction)
            _balance.value = calcBalance()
        }

    override fun getBalance(): Flow<Result<BigDecimal>> = _balance.mapToResult()

    private fun calcBalance(): BigDecimal {
        return _transactions.fold(BigDecimal.ZERO) { acc, t ->
            when (t.type) {
                TransactionType.DEPOSIT -> acc + t.amount
                TransactionType.EXPENSE -> acc - t.amount
            }
        }
    }
}
