package com.example.bittrack.ui.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.bittrack.R
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.ui.components.BaseButton
import com.example.bittrack.ui.components.BtcAmountInput
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalExtendedColors
import com.example.bittrack.ui.theme.LocalSpacing
import com.example.bittrack.ui.theme.NeutralBgDarker
import com.example.bittrack.ui.theme.Spacing
import com.example.bittrack.ui.theme.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    addTransactionState: AddTransactionState,
    onBack: () -> Unit,
    onEvent: (AddTransactionEvent) -> Unit
) {
    val spacing = LocalSpacing.current

    var input by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(TransactionCategory.GROCERIES) }
    var expanded by remember { mutableStateOf(false) }

    val amount = runCatching { input.toBigDecimal() }.getOrNull()

    val amountExceedsBalance = stringResource(
        R.string.amount_exceeds_balance,
        addTransactionState.balance.asBtc()
    )

    val errorText = when {
        amount != null && amount > addTransactionState.balance -> amountExceedsBalance
        else -> null
    }

    val enabled = amount != null && errorText == null

    val onConfirmClick = remember(onEvent, onBack, addTransactionState.balance, category) {
        {
            val currentAmount = input.toBigDecimalOrNull()
            if (currentAmount != null && currentAmount <= addTransactionState.balance) {
                onEvent(AddTransactionEvent.AddTransaction(currentAmount, category))
                onBack()
            }
        }
    }

    Scaffold(
        topBar = { AddTransactionTopBar(onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(LocalExtendedColors.current.appBackground)
                .padding(padding)
                .padding(horizontal = spacing.l.dp, vertical = spacing.l.dp)
                .fillMaxSize()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(spacing.l.dp)
        ) {
            AmountSection(
                input = input,
                errorText = errorText,
                onInputChange = { input = it.trim().replace(",", ".") }
            )

            CategorySection(
                spacing = spacing,
                category = category,
                expanded = expanded,
                onMenuClick = { expanded = !expanded },
                onDismiss = { expanded = false },
                onCategorySelected = {
                    category = it
                    expanded = false
                }
            )

            Spacer(Modifier.weight(1f))

            ConfirmButton(
                enabled = enabled,
                onClick = onConfirmClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTransactionTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.new_transaction_title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_back),
                    tint = Success
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(containerColor = NeutralBgDarker)
    )
}

@Composable
private fun AmountSection(
    input: String,
    errorText: String?,
    onInputChange: (String) -> Unit
) {
    BtcAmountInput(
        modifier = Modifier.fillMaxWidth(),
        value = input,
        onValueChange = { onInputChange(it.trim().replace(",", ".")) },
        errorText = errorText
    )
}

@Composable
private fun CategorySection(
    spacing: Spacing,
    category: TransactionCategory,
    expanded: Boolean,
    onMenuClick: () -> Unit,
    onDismiss: () -> Unit,
    onCategorySelected: (TransactionCategory) -> Unit
) {
    val dropdownIcon = if (expanded) {
        Icons.Rounded.KeyboardArrowUp
    } else {
        Icons.Rounded.KeyboardArrowDown
    }

    Column(verticalArrangement = Arrangement.spacedBy(spacing.s.dp)) {
        Text(
            text = stringResource(R.string.category_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box {
            OutlinedButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.s.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = category.emoji.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = dropdownIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
                TransactionCategory.entries
                    .filter { it != TransactionCategory.NONE }
                    .forEach { categoryItem ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    categoryItem.emoji?.let { Text(it) }
                                    Text(categoryItem.displayName)
                                }
                            },
                            onClick = { onCategorySelected(categoryItem) }
                        )
                    }
            }
        }
    }
}

@Composable
private fun ConfirmButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    BaseButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        text = stringResource(R.string.add_transaction),
        onClick = onClick
    )
}

@Preview
@Composable
private fun AddTransactionScreenPreview() {
    BitTrackTheme {
        AddTransactionScreen(addTransactionState = AddTransactionState(), onBack = {}, onEvent = {})
    }
}
