package com.example.bittrack.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.bittrack.ui.model.TransactionRow
import com.example.bittrack.ui.theme.LocalSpacing

@Composable
fun TransactionsList(
    modifier: Modifier = Modifier,
    transactions: LazyPagingItems<TransactionRow>
) {
    val spacing = 12.dp

    if (transactions.loadState.refresh is LoadState.NotLoading && transactions.itemCount == 0) {
        EmptyPlaceholder(modifier.padding(24.dp))
        return
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(
            count = transactions.itemCount,
            key = { index ->
                when (val it = transactions.peek(index)) {
                    is TransactionRow.Transaction -> "txn_${it.transaction.id}"
                    is TransactionRow.DayHeader -> "hdr_${it.label}"
                    null -> "placeholder_$index"
                }
            },
            contentType = { index ->
                when (transactions.peek(index)) {
                    is TransactionRow.Transaction -> "item"
                    is TransactionRow.DayHeader -> "header"
                    else -> "placeholder"
                }
            }
        ) { index ->
            when (val row = transactions[index]) {
                is TransactionRow.DayHeader -> Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = row.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                is TransactionRow.Transaction -> TransactionListItem(transaction = row.transaction)
                null -> Spacer(Modifier.height(64.dp))
            }
        }
    }
}

@Composable
private fun EmptyPlaceholder(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .border(0.5.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No transactions yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(spacing.m.dp))
        Text(
            "Deposit by clicking the button on top",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
