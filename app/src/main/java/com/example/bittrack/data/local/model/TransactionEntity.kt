package com.example.bittrack.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bittrack.core.model.TransactionCategory

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: Long = 0
)
