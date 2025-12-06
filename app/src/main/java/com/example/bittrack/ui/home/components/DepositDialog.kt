package com.example.bittrack.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.bittrack.R
import com.example.bittrack.ui.components.BaseButton
import com.example.bittrack.ui.components.BtcAmountInput
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalSpacing
import java.math.BigDecimal

@Composable
fun DepositDialog(
    modifier: Modifier = Modifier,
    onConfirm: (BigDecimal) -> Unit,
    onDismiss: () -> Unit
) {
    val spacing = LocalSpacing.current

    var input by rememberSaveable { mutableStateOf("") }
    val amount = runCatching { input.toBigDecimal() }.getOrNull()
    val buttonEnabled = input.isNotEmpty() && amount != null
    val onDepositClick = remember(onConfirm, onDismiss) {
        {
            val currentAmount = input.toBigDecimalOrNull()
            if (currentAmount != null) {
                onConfirm(currentAmount)
                onDismiss()
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.xl.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(spacing.xl.dp),
                verticalArrangement = Arrangement.spacedBy(spacing.l.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.add_bitcoin),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                BtcAmountInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = input,
                    onValueChange = { input = it.trim().replace(",", ".") }
                )
                ConfirmButton(
                    enabled = buttonEnabled,
                    onClick = onDepositClick
                )
            }
        }
    }
}

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    BaseButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        text = stringResource(R.string.deposit),
        onClick = onClick
    )
}

@Preview
@Composable
fun DepositDialogPreview() {
    BitTrackTheme {
        DepositDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}
