package com.example.bittrack.data.mappers

import com.example.bittrack.data.local.model.TransactionEntity
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.core.util.DateUtils
import java.math.BigDecimal

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount.toDouble(),
        type = type,
        category = category,
        timestamp = DateUtils.localDateTimeToEpochMillis(timestamp)
    )
}

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = BigDecimal.valueOf(amount),
        type = type,
        category = category,
        timestamp = DateUtils.epochMillisToLocalDateTime(timestamp)
    )
}