package com.example.bittrack.core.util

import java.time.LocalDateTime
import java.time.ZoneId

object DateUtils {

    val zoneId: ZoneId = ZoneId.systemDefault()

    fun nowMillis() = System.currentTimeMillis()

    fun nowLocalDateTime() = LocalDateTime.now(zoneId)

    fun localDateTimeToEpochMillis(timestamp: LocalDateTime): Long {
        return timestamp.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun epochMillisToLocalDateTime(timestampMillis: Long): LocalDateTime {
        return java.time.Instant.ofEpochMilli(timestampMillis)
            .atZone(zoneId)
            .toLocalDateTime()
    }
}
