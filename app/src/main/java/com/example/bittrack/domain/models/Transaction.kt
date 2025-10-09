package com.example.bittrack.domain.models

import com.example.bittrack.meta.model.TransactionCategory
import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: LocalDateTime
)
