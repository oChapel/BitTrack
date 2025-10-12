package com.example.bittrack.ui.add_transaction

import com.example.bittrack.core.model.TransactionCategory
import java.math.BigDecimal

sealed class AddTransactionEvent {
    data class AddTransaction(val amount: BigDecimal, val category: TransactionCategory) : AddTransactionEvent()
}
