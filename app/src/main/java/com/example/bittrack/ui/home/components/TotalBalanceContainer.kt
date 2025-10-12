package com.example.bittrack.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bittrack.R
import com.example.bittrack.ui.components.BaseButton
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalExtendedColors
import com.example.bittrack.ui.theme.LocalSpacing

@Composable
fun TotalBalanceContainer(
    modifier: Modifier = Modifier,
    balanceText: String,
    fiatApproxText: String,
    onTopUpClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .background(
                brush = LocalExtendedColors.current.cardGradient,
                shape = MaterialTheme.shapes.large
            )
            .border(
                width = 0.5.dp,
                color = LocalExtendedColors.current.outlineSoft,
                shape = MaterialTheme.shapes.large
            )
            .padding(spacing.xl.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            BaseButton(
                text = "Deposit",
                iconRes = R.drawable.add_icon,
                onClick = onTopUpClick
            )
        }
        Spacer(modifier = Modifier.height(spacing.xl.dp))
        Text(
            text = balanceText,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = fiatApproxText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun TotalBalanceContainerPreview() {
    BitTrackTheme {
        TotalBalanceContainer(
            balanceText = "1.2456 BTC",
            fiatApproxText = "≈ $112905.5498828"
        ) {}
    }
}