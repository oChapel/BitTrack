package com.example.bittrack.ui.home

sealed class HomeEvent {
    data object GetBtcRate : HomeEvent()
    data class AddIncomeTransaction(val amount: Double) : HomeEvent()
}