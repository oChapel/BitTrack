package com.example.bittrack.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.bittrack.core.util.Formatting
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.core.util.Formatting.asUsd
import com.example.bittrack.domain.use_case.AddTransactionUseCase
import com.example.bittrack.domain.use_case.GetBalanceUseCase
import com.example.bittrack.domain.use_case.GetBtcRateUseCase
import com.example.bittrack.domain.use_case.GetPagedTransactionsUseCase
import com.example.bittrack.ui.base.BaseViewModel
import com.example.bittrack.ui.mapper.toUi
import com.example.bittrack.ui.model.TransactionRow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPagedTransactionsUseCase: GetPagedTransactionsUseCase,
    getBalanceUseCase: GetBalanceUseCase,
    private val getBtcRateUseCase: GetBtcRateUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : BaseViewModel<HomeState, HomeEvent>(HomeState()) {

    val transactionFlow: Flow<PagingData<TransactionRow>> =
        getPagedTransactionsUseCase()
            .map { pagingData ->
                pagingData
                    .map { TransactionRow.Transaction(it.toUi()) }
                    .insertSeparators { before: TransactionRow?, after: TransactionRow? ->
                        val afterTransaction = (after as? TransactionRow.Transaction)?.transaction
                            ?: return@insertSeparators null
                        val beforeTransaction = (before as? TransactionRow.Transaction)?.transaction
                        val afterDateTime = afterTransaction.timestamp
                        val beforeDateTime = beforeTransaction?.timestamp
                        if (before == null || beforeDateTime?.toLocalDate() != afterDateTime.toLocalDate()) {
                            TransactionRow.DayHeader(Formatting.formatDayLabel(afterDateTime))
                        } else null
                    }
            }
            .cachedIn(viewModelScope)

    private val btcRateFlow = MutableStateFlow<BigDecimal?>(null)

    init {
        combine(getBalanceUseCase(), btcRateFlow) { balance, rate -> balance to rate }
            .onEach { (balance, rate) ->
                val fiatBalance = rate?.let { "≈ ${it.multiply(balance).asUsd()}" }.orEmpty()
                updateState {
                    it.copy(
                        btcRate = rate?.asUsd() ?: "...",
                        balance = balance.asBtc(),
                        fiatBalance = fiatBalance
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetBtcRate -> getBtcRate()
            is HomeEvent.AddIncomeTransaction -> addIncomeTransaction(event.amount)
        }
    }

    private fun getBtcRate() {
        viewModelScope.launch {
            val btcRate = getBtcRateUseCase()
            btcRateFlow.value = btcRate
        }
    }

    private fun addIncomeTransaction(amount: Double) {
        viewModelScope.launch {
            addTransactionUseCase(AddTransactionUseCase.AddTransactionRequest.Income(amount))
        }
    }
}
