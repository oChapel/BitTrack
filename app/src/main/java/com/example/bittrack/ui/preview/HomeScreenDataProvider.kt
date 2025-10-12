package com.example.bittrack.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.core.util.Formatting
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.ui.mapper.toUi
import com.example.bittrack.ui.model.TransactionRow
import java.math.BigDecimal

class HomeScreenDataProvider : PreviewParameterProvider<HomeScreenData> {
    private val transactions = listOf(
        Transaction(
            id = 5,
            amount = BigDecimal.valueOf(0.00056),
            type = TransactionType.EXPENSE,
            category = TransactionCategory.GROCERIES,
            timestamp = DateUtils.nowLocalDateTime()
        ),
        Transaction(
            id = 4,
            amount = BigDecimal.valueOf(0.00024),
            type = TransactionType.EXPENSE,
            category = TransactionCategory.TAXI,
            timestamp = DateUtils.nowLocalDateTime().minusHours(2)
        ),
        Transaction(
            id = 3,
            amount = BigDecimal.valueOf(0.001),
            type = TransactionType.DEPOSIT,
            category = TransactionCategory.NONE,
            timestamp = DateUtils.nowLocalDateTime().minusHours(3)
        ),
        Transaction(
            id = 2,
            amount = BigDecimal.valueOf(0.0002),
            type = TransactionType.EXPENSE,
            category = TransactionCategory.RESTAURANT,
            timestamp = DateUtils.nowLocalDateTime().minusDays(1)
        ),
        Transaction(
            id = 1,
            amount = BigDecimal.valueOf(0.00007),
            type = TransactionType.EXPENSE,
            category = TransactionCategory.GROCERIES,
            timestamp = DateUtils.nowLocalDateTime().minusDays(1).minusHours(1)
        ),
        Transaction(
            id = 0,
            amount = BigDecimal.valueOf(0.0001),
            type = TransactionType.DEPOSIT,
            category = TransactionCategory.NONE,
            timestamp = DateUtils.nowLocalDateTime().minusDays(3).minusHours(4)
        )
    )
        .map { TransactionRow.Transaction(it.toUi()) }


    override val values = sequenceOf(
        HomeScreenData(transactions = PagingData.from(
            data = transactions,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false)
            )
        )
            .insertSeparators { before: TransactionRow?, after: TransactionRow? ->
                val afterTransaction = (after as? TransactionRow.Transaction)?.transaction
                    ?: return@insertSeparators null
                val beforeTransaction = (before as? TransactionRow.Transaction)?.transaction
                val afterDateTime = afterTransaction.timestamp
                val beforeDateTime = beforeTransaction?.timestamp
                if (before == null || beforeDateTime?.toLocalDate() != afterDateTime.toLocalDate()) {
                    TransactionRow.DayHeader(Formatting.formatDayLabel(afterDateTime))
                } else null
            }
        )
    )
}

data class HomeScreenData(
    val transactions: PagingData<TransactionRow>
)