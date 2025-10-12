package com.example.bittrack.ui.home

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.core.util.Formatting.asBtc
import com.example.bittrack.core.util.Formatting.asUsd
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.use_case.AddTransactionUseCase
import com.example.bittrack.domain.use_case.GetBalanceUseCase
import com.example.bittrack.domain.use_case.GetBtcRateUseCase
import com.example.bittrack.domain.use_case.GetPagedTransactionsUseCase
import com.example.bittrack.mock.RateRepositoryMock
import com.example.bittrack.mock.TransactionRepositoryMock
import com.example.bittrack.ui.model.TransactionRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    data class ViewModelDependencies(
        val transactionRepository: TransactionRepositoryMock,
        val rateRepository: RateRepositoryMock
    )

    @Before
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun tearDown() = Dispatchers.resetMain()

    private val noopListUpdate = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    private val rowDiff = object : DiffUtil.ItemCallback<TransactionRow>() {
        override fun areItemsTheSame(old: TransactionRow, new: TransactionRow): Boolean =
            when {
                old is TransactionRow.Transaction && new is TransactionRow.Transaction -> old.transaction.id == new.transaction.id
                old is TransactionRow.DayHeader && new is TransactionRow.DayHeader -> old.label == new.label
                else -> false
            }

        override fun areContentsTheSame(old: TransactionRow, new: TransactionRow): Boolean = old == new
    }

    @Test
    fun `initial state uses placeholder rate and zero balance`() = runTest {
        val vmDependencies = ViewModelDependencies(
            transactionRepository = TransactionRepositoryMock(),
            rateRepository = RateRepositoryMock(null)
        )
        val vm = getViewModel(vmDependencies)

        val state = vm.uiState.value
        assertEquals("...", state.btcRate)
        assertEquals(BigDecimal.ZERO.asBtc(), state.balance)
        assertEquals("", state.fiatBalance)
    }

    @Test
    fun `updates fiat when either balance or rate changes`() = runTest {
        val vmDependencies = ViewModelDependencies(
            transactionRepository = TransactionRepositoryMock(),
            rateRepository = RateRepositoryMock(null)
        )
        val vm = getViewModel(vmDependencies)

        vmDependencies.rateRepository.emitCached(BigDecimal("100000"))
        vm.onEvent(HomeEvent.AddIncomeTransaction(BigDecimal("1.50")))

        val s = vm.uiState.value
        assertEquals(BigDecimal("1.50").asBtc(), s.balance)
        assertEquals(BigDecimal("100000").asUsd(), s.btcRate)
        assertEquals("≈ ${BigDecimal("150000").asUsd()}", s.fiatBalance)

        vmDependencies.rateRepository.emitCached(BigDecimal("120000"))
        assertEquals("≈ ${BigDecimal("180000").asUsd()}", vm.uiState.value.fiatBalance)
    }

    @Test
    fun `add income saves transaction`() = runTest {
        val vmDependencies = ViewModelDependencies(
            transactionRepository = TransactionRepositoryMock(),
            rateRepository = RateRepositoryMock(null)
        )
        val vm = getViewModel(vmDependencies)

        vm.onEvent(HomeEvent.AddIncomeTransaction(BigDecimal("0.12345678")))

        assertEquals(BigDecimal("0.12345678").asBtc(), vm.uiState.value.balance)
    }

    @Test
    fun `paging inserts day headers`() = runTest {
        val now = DateUtils.nowLocalDateTime()
        val transactions = listOf(
            Transaction(1, BigDecimal("0.1"), TransactionType.EXPENSE, TransactionCategory.GROCERIES, now),
            Transaction(2, BigDecimal("0.2"), TransactionType.DEPOSIT, TransactionCategory.NONE, now.minusMinutes(1)),
            Transaction(3, BigDecimal("0.3"), TransactionType.EXPENSE, TransactionCategory.TAXI, now.minusDays(1))
        )

        val vmDependencies = ViewModelDependencies(
            transactionRepository = TransactionRepositoryMock(transactions),
            rateRepository = RateRepositoryMock(null)
        )
        val vm = getViewModel(vmDependencies)

        val differ = AsyncPagingDataDiffer(
            diffCallback = rowDiff,
            updateCallback = noopListUpdate,
            workerDispatcher = UnconfinedTestDispatcher()
        )

        val paging = vm.transactionFlow.first()
        differ.submitData(paging)

        advanceUntilIdle()

        val snapshot = differ.snapshot().items
        assertTrue(snapshot[0] is TransactionRow.DayHeader)
        assertTrue(snapshot[1] is TransactionRow.Transaction)
        assertTrue(snapshot[2] is TransactionRow.Transaction)
        assertTrue(snapshot[3] is TransactionRow.DayHeader)
        assertTrue(snapshot[4] is TransactionRow.Transaction)
    }

    private fun getViewModel(vmDependencies: ViewModelDependencies): HomeViewModel {
        val dispatcher = UnconfinedTestDispatcher()
        return HomeViewModel(
            getPagedTransactionsUseCase = GetPagedTransactionsUseCase(vmDependencies.transactionRepository),
            getBalanceUseCase = GetBalanceUseCase(vmDependencies.transactionRepository),
            getBtcRateUseCase = GetBtcRateUseCase(vmDependencies.rateRepository, dispatcher),
            addTransactionUseCase = AddTransactionUseCase(vmDependencies.transactionRepository, dispatcher)
        )
    }
}
