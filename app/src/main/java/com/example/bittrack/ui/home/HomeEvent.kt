package com.example.bittrack.ui.home

import java.math.BigDecimal

sealed class HomeEvent {
    data class AddIncomeTransaction(val amount: BigDecimal) : HomeEvent()
}
