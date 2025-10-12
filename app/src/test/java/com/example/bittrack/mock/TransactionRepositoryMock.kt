package com.example.bittrack.mock

import androidx.paging.PagingData
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class TransactionRepositoryMock(
    initial: List<Transaction> = emptyList()
) : TransactionRepository {

    private val _transactions = mutableListOf<Transaction>().apply { addAll(initial) }
    private val _balance = MutableStateFlow(calcBalance())

    override fun getPagedTransactions(): Flow<PagingData<Transaction>> =
        flowOf(PagingData.from(_transactions.toList()))

    override suspend fun saveTransaction(transaction: Transaction) {
        _transactions.add(transaction)
        _balance.value = calcBalance()
    }

    override fun getBalance(): Flow<Double> = _balance.map { it.toDouble() }

    private fun calcBalance(): BigDecimal {
        return _transactions.fold(BigDecimal.ZERO) { acc, t ->
            when (t.type) {
                TransactionType.DEPOSIT -> acc + t.amount
                TransactionType.EXPENSE -> acc - t.amount
            }
        }
    }
}
