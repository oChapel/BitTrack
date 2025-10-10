package com.example.bittrack.core.model

enum class TransactionType {
    INCOME,
    EXPENSE;

    companion object {
        fun fromName(name: String): TransactionType =
            TransactionType.entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: EXPENSE
    }
}
