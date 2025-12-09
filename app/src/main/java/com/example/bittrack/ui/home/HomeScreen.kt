package com.example.bittrack.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val spacing = LocalSpacing.current

    var showDepositDialog by rememberSaveable { mutableStateOf(false) }
    val onDepositClick = remember {
        { showDepositDialog = true }
    }

    val lazyPagingTransactions = transactionsFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    val collapsed by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalExtendedColors.current.appBackground)
            .padding(horizontal = spacing.l.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier.animateContentSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.End),
                visible = !collapsed,
                enter = fadeIn(tween(200)) + expandVertically(),
                exit = fadeOut(tween(200)) + shrinkVertically()
            ) {
                BtcRateContainer(
                    rateText = homeState.btcRate
                )
            }

            TotalBalanceContainer(
                modifier = Modifier.padding(vertical = spacing.l.dp),
                balanceText = homeState.balance,
                fiatApproxText = homeState.fiatBalance,
                onTopUpClick = onDepositClick,
                collapsed = collapsed
            )

            AnimatedVisibility(
                visible = !collapsed,
                enter = fadeIn(tween(200)) + expandVertically(),
                exit = fadeOut(tween(150)) + shrinkVertically()
            ) {
                BaseButton(
                    modifier = Modifier.fillMaxWidth().padding(bottom = spacing.l.dp),
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    iconRes = R.drawable.add_icon,
                    iconSize = spacing.xxl.dp,
                    iconTint = Success,
                    text = stringResource(R.string.add_transaction),
                    textColor = MaterialTheme.colorScheme.onSurface,
                    onClick = onAddTransactionClick
                )
            }

            Text(
                modifier = Modifier.padding(bottom = spacing.s.dp),
                text = stringResource(R.string.recent_transactions),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            TransactionsList(
                modifier = Modifier.fillMaxWidth(),
                transactions = lazyPagingTransactions,
                listState = listState
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

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = collapsed,
            enter = fadeIn(tween(200)) + scaleIn(),
            exit = fadeOut(tween(200)) + scaleOut()
        ) {
            FloatingActionButton(
                onClick = onAddTransactionClick,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = stringResource(R.string.add_transaction)
                )
            }
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