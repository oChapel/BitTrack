package com.example.bittrack.ui.home

data class HomeState(
    val btcRate: String = "",
    val balance: String = "",
    val fiatBalance: String = "",
    val isLoading: Boolean = false
)
