package com.example.bittrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bittrack.R
import com.example.bittrack.ui.theme.LocalSpacing

@Composable
fun BtcAmountInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.s.dp)
    ) {
        Text(
            text = stringResource(R.string.amount_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            placeholder = { Text(stringResource(R.string.amount_placeholder)) },
            supportingText = { if (errorText != null) Text(errorText) },
            isError = errorText != null,
            shape = MaterialTheme.shapes.medium
        )
    }
}
