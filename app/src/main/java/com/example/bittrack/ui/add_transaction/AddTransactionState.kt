package com.example.bittrack.ui.add_transaction

import androidx.compose.runtime.Immutable
import java.math.BigDecimal

@Immutable
data class AddTransactionState(
    val balance: BigDecimal = BigDecimal.ZERO
)
