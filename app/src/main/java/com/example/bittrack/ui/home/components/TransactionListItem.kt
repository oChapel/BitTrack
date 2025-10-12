package com.example.bittrack.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.ui.model.TransactionUi
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalSpacing
import com.example.bittrack.ui.theme.Success

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    transaction: TransactionUi
) {
    val spacing = LocalSpacing.current
    val amountColor = if (transaction.isDeposit) Success else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier
            .shadow(spacing.xs.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = spacing.m.dp, spacing.s.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.s.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                .padding(10.dp)
        ) {
            Text(text = transaction.emoji)
        }
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = transaction.timeLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = transaction.amountLabel,
            style = MaterialTheme.typography.bodyLarge,
            color = amountColor
        )
    }
}

@Preview
@Composable
fun TransactionRowPreview() {
    BitTrackTheme {
        TransactionListItem(
            transaction = TransactionUi(
                id = 0,
                title = TransactionCategory.GROCERIES.displayName,
                emoji = "🍏",
                timeLabel = "12:56",
                amountLabel = "- 0.0056 BTC",
                isDeposit = false,
                timestamp = DateUtils.nowLocalDateTime()
            )
        )
    }
}