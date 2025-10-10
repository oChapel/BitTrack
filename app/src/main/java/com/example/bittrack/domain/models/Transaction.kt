package com.example.bittrack.domain.models

import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType
import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val category: TransactionCategory,
    val timestamp: LocalDateTime
)
