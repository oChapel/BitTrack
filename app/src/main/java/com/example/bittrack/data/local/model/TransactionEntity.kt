package com.example.bittrack.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType

@Entity(
    tableName = "transactions",
    indices = [Index("timestamp")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val type: TransactionType,
    val category: TransactionCategory,
    val timestamp: Long
)
