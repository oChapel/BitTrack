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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.ui.components.BaseButton
import com.example.bittrack.ui.components.BtcAmountInput
import com.example.bittrack.ui.theme.BitTrackTheme
import com.example.bittrack.ui.theme.LocalExtendedColors
import com.example.bittrack.ui.theme.LocalSpacing
import com.example.bittrack.ui.theme.NeutralBgDarker
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
    val dropdownIcon = if (expanded) {
        Icons.Rounded.KeyboardArrowUp
    } else {
        Icons.Rounded.KeyboardArrowDown
    }
    val amount = remember(input) {
        runCatching { input.toBigDecimal() }.getOrNull()
    }
    val errorText = when {
        amount != null && amount > addTransactionState.balance -> "Amount exceeds balance (${addTransactionState.balance.asBtc()})"
        else -> null
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "New Transaction", style = MaterialTheme.typography.titleMedium
            )
        },
            navigationIcon = {
                IconButton(
                    onClick = onBack, modifier = Modifier.padding(start = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Success
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = NeutralBgDarker)
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .background(LocalExtendedColors.current.appBackground)
                .padding(padding)
                .padding(horizontal = spacing.l.dp, vertical = spacing.l.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(spacing.l.dp)
        ) {
            BtcAmountInput(modifier = Modifier.fillMaxWidth(),
                value = input,
                onValueChange = { input = it.trim().replace(",", ".") })
            Column(verticalArrangement = Arrangement.spacedBy(spacing.s.dp)) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box {
                    OutlinedButton(
                        onClick = { expanded = !expanded },
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

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        TransactionCategory.entries.filter { it != TransactionCategory.NONE }
                            .forEach { categoryItem ->
                                DropdownMenuItem(text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        categoryItem.emoji?.let { Text(it) }
                                        Text(categoryItem.displayName)
                                    }
                                }, onClick = {
                                    category = categoryItem
                                    expanded = false
                                })
                            }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            BaseButton(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
                enabled = amount != null && errorText != null,
                text = "Add Transaction",
                onClick = {
                    amount?.let {
                        onEvent(AddTransactionEvent.AddTransaction(amount, category))
                        onBack()
                    }
                })
        }
    }
}

@Preview
@Composable
private fun AddTransactionScreenPreview() {
    BitTrackTheme {
        AddTransactionScreen(addTransactionState = AddTransactionState(), onBack = {}, onEvent = {})
    }
}
