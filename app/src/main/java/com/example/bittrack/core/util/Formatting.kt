package com.example.bittrack.core.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Formatting {

    private val btcFormat = DecimalFormat("#,##0.########")
    private val usdFormat = DecimalFormat("#,##0.00")

    fun BigDecimal.asBtc() = "${btcFormat.format(this)} BTC"

    fun BigDecimal.asUsd() = "\$${usdFormat.format(this)}"

    fun formatDayLabel(date: LocalDateTime): String {
        val today = LocalDate.now(DateUtils.zoneId)
        return when (date.toLocalDate()) {
            today -> "Today"
            today.minusDays(1) -> "Yesterday"
            else -> date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }
    }

    fun formatTime(date: LocalDateTime): String =
        date.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
}
