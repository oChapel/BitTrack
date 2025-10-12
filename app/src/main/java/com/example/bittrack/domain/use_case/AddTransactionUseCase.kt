package com.example.bittrack.domain.use_case

import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.di.IODispatcher
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    sealed interface AddTransactionRequest {
        val amount: Double
        data class Income(override val amount: Double) : AddTransactionRequest
        data class Expense(
            override val amount: Double,
            val category: TransactionCategory
        ) : AddTransactionRequest
    }

    suspend operator fun invoke(request: AddTransactionRequest) = withContext(dispatcher) {
        val (type, category) = when (request) {
            is AddTransactionRequest.Income -> TransactionType.DEPOSIT to TransactionCategory.NONE
            is AddTransactionRequest.Expense -> TransactionType.EXPENSE to request.category
        }
        val transaction = Transaction(
            amount = BigDecimal.valueOf(request.amount),
            type = type,
            category = category,
            timestamp = DateUtils.nowLocalDateTime()
        )
        transactionRepository.saveTransaction(transaction)
    }
}
