package com.example.bittrack.data.mappers

import com.example.bittrack.data.local.model.TransactionEntity
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.core.util.DateUtils

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        category = category,
        timestamp = DateUtils.localDateTimeToEpochMillis(timestamp)
    )
}

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        type = type,
        category = category,
        timestamp = DateUtils.epochMillisToLocalDateTime(timestamp)
    )
}