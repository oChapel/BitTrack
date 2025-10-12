package com.example.bittrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bittrack.R
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.core.util.Formatting.asUsd
import com.example.bittrack.ui.components.BaseButton
import com.example.bittrack.ui.home.components.BtcRateContainer
import com.example.bittrack.ui.home.components.DepositDialog
import com.example.bittrack.ui.home.components.TotalBalanceContainer
import com.example.bittrack.ui.home.components.TransactionsList
import com.example.bittrack.ui.model.TransactionRow
import com.example.bittrack.ui.preview.HomeScreenData
import com.example.bittrack.ui.preview.HomeScreenDataProvider
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalExtendedColors
import com.example.bittrack.ui.theme.LocalSpacing
import com.example.bittrack.ui.theme.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal

@Composable
fun HomeScreen(
    homeState: HomeState,
    transactionsFlow: Flow<PagingData<TransactionRow>>,
    onEvent: (HomeEvent) -> Unit,
    onAddTransactionClick: () -> Unit
) {
    var showDepositDialog by rememberSaveable { mutableStateOf(false) }
    val lazyPagingTransactions = transactionsFlow.collectAsLazyPagingItems()
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .background(LocalExtendedColors.current.appBackground)
            .padding(horizontal = spacing.l.dp, vertical = spacing.xxl.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.l.dp, Alignment.Top)
    ) {
        BtcRateContainer(
            modifier = Modifier.align(Alignment.End),
            rateText = homeState.btcRate
        )
        TotalBalanceContainer(
            balanceText = homeState.balance,
            fiatApproxText = homeState.fiatBalance,
            onTopUpClick = {} // TODO
        )
        BaseButton(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.secondary,
            iconRes = R.drawable.add_icon,
            iconSize = spacing.xxl.dp,
            iconTint = Success,
            text = "Add Transaction",
            textColor = MaterialTheme.colorScheme.onSurface,
            onClick = onAddTransactionClick
        )
        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        TransactionsList(
            modifier = Modifier.fillMaxWidth(),
            transactions = lazyPagingTransactions
        )
        if (showDepositDialog) {
            DepositDialog(
                onConfirm = {
                    showDepositDialog = false
                    onEvent(HomeEvent.AddIncomeTransaction(it))
                },
                onDismiss = { showDepositDialog = false }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeScreenDataProvider::class) data: HomeScreenData
) {
    BitTrackTheme {
        val btcRate = BigDecimal(108905.798)
        val balance = BigDecimal(1.278095)
        HomeScreen(
            homeState = HomeState(
                btcRate = btcRate.asUsd(),
                balance = balance.asBtc(),
                fiatBalance = balance.multiply(btcRate).asUsd(),
                isLoading = false
            ),
            transactionsFlow = flowOf(data.transactions),
            onEvent = {},
            onAddTransactionClick = {}
        )
    }
}