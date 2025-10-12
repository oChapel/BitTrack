package com.example.bittrack.ui.mapper

import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.core.util.Formatting
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.ui.model.TransactionUi

fun Transaction.toUi(): TransactionUi {
    val isDeposit = type == TransactionType.DEPOSIT
    val amountString = amount.asBtc()
    return TransactionUi(
        id = id,
        title = if (isDeposit) "Deposit" else category.displayName,
        emoji = if (isDeposit) "💰" else category.emoji.orEmpty(),
        timeLabel = Formatting.formatTime(timestamp),
        amountLabel = (if (isDeposit) "+ " else "− ") + amountString,
        isDeposit = isDeposit,
        timestamp = timestamp
    )
}
