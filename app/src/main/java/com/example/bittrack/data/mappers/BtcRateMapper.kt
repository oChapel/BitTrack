package com.example.bittrack.data.mappers

import com.example.bittrack.domain.models.BtcRate

typealias BtcRateProto = com.example.bittrack.proto.BtcRate

fun BtcRateProto.toDomain(): BtcRate {
    return BtcRate(
        rate = rate,
        timestamp = timestamp
    )
}

fun BtcRate.toProto(): BtcRateProto {
    return BtcRateProto.newBuilder()
        .setRate(rate)
        .setTimestamp(timestamp)
        .build()
}
