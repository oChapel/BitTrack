package com.example.bittrack.ui.model

import java.time.LocalDateTime

data class TransactionUi(
    val id: Long,
    val title: String,
    val emoji: String,
    val timeLabel: String,
    val amountLabel: String,
    val isDeposit: Boolean,
    val timestamp: LocalDateTime
)
