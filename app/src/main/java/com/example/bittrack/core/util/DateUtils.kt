package com.example.bittrack.core.util

import java.time.LocalDateTime
import java.time.ZoneId

object DateUtils {

    fun nowMillis() = System.currentTimeMillis()

    fun localDateTimeToEpochMillis(timestamp: LocalDateTime): Long {
        return timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun epochMillisToLocalDateTime(timestampMillis: Long): LocalDateTime {
        return java.time.Instant.ofEpochMilli(timestampMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}
