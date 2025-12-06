package com.example.bittrack.ui.model

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class TransactionUi(
    val id: Long,
    val title: String,
    val emoji: String,
    val timeLabel: String,
    val amountLabel: String,
    val isDeposit: Boolean,
    val timestamp: LocalDateTime
)
