package com.example.bittrack.ui.add_transaction

import androidx.lifecycle.viewModelScope
import com.example.bittrack.core.ext.onSuccess
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.domain.use_case.AddTransactionUseCase
import com.example.bittrack.domain.use_case.GetBalanceUseCase
import com.example.bittrack.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    getBalanceUseCase: GetBalanceUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : BaseViewModel<AddTransactionState, AddTransactionEvent>(AddTransactionState()) {

    init {
        getBalanceUseCase()
            .onSuccess { balance -> updateState { it.copy(balance = balance) } }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.AddTransaction -> addTransaction(event.amount, event.category)
        }
    }

    private fun addTransaction(amount: BigDecimal, category: TransactionCategory) {
        viewModelScope.launch {
            addTransactionUseCase(
                AddTransactionUseCase.AddTransactionRequest.Expense(amount, category)
            )
        }
    }
}
