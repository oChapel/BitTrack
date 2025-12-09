package com.example.bittrack.ui.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    collapsed: Boolean,
    onTopUpClick: () -> Unit
) {
    val spacing = LocalSpacing.current

    val transition = updateTransition(
        targetState = collapsed,
        label = "collapsedTransition"
    )

    val spacerHeight by transition.animateDp(
        transitionSpec = { tween(200) },
        label = "spacerHeight",
        targetValueByState = { collapsed ->
            if (!collapsed) spacing.xl.dp else spacing.s.dp
        }
    )

    val expandedStyle = MaterialTheme.typography.displaySmall
    val collapsedStyle = MaterialTheme.typography.headlineMedium

    val fontSize by transition.animateFloat(
        transitionSpec = { tween(200) },
        label = "fontSize",
        targetValueByState = { collapsed ->
            if (!collapsed) expandedStyle.fontSize.value else collapsedStyle.fontSize.value
        }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = LocalExtendedColors.current.cardGradient,
                shape = MaterialTheme.shapes.large
            )
            .border(
                width = 0.5.dp,
                color = LocalExtendedColors.current.outlineSoft,
                shape = MaterialTheme.shapes.large
            )
            .padding(spacing.xl.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = stringResource(R.string.total_balance),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AnimatedVisibility(
                visible = !collapsed,
                enter = fadeIn(tween(200)) + expandVertically(),
                exit = fadeOut(tween(200)) + shrinkVertically()
            ) {
                BaseButton(
                    text = stringResource(R.string.deposit),
                    iconRes = R.drawable.add_icon,
                    onClick = onTopUpClick
                )
            }
        }
        Spacer(modifier = Modifier.height(spacerHeight))
        Text(
            text = balanceText,
            style = expandedStyle.copy(fontSize = fontSize.sp),
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
            fiatApproxText = "≈ $112905.5498828",
            collapsed = false
        ) {}
    }
}