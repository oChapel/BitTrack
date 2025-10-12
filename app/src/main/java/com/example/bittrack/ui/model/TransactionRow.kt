package com.example.bittrack.ui.model

sealed interface TransactionRow {
    data class DayHeader(val label: String) : TransactionRow
    data class Transaction(val transaction: TransactionUi) : TransactionRow
}
