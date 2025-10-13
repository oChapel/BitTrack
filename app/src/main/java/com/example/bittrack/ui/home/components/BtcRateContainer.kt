package com.example.bittrack.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bittrack.R
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalExtendedColors
import com.example.bittrack.ui.theme.LocalSpacing

@Composable
fun BtcRateContainer(
    modifier: Modifier = Modifier,
    rateText: String
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
            .border(
                width = 0.5.dp,
                color = LocalExtendedColors.current.outlineSoft,
                shape = MaterialTheme.shapes.large
            )
            .padding(horizontal = spacing.m.dp, vertical = spacing.s.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.asset_rate_icon),
            contentDescription = null,
            contentScale = ContentScale.None
        )
        Text(
            text = stringResource(R.string.btc_rate_format, rateText),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun AssetRateContainerPreview(modifier: Modifier = Modifier) {
    BitTrackTheme {
        BtcRateContainer(rateText = "$95668.09")
    }
}
