package com.example.bittrack.ui.add_transaction

import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType
import com.example.bittrack.core.util.DateUtils
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.use_case.AddTransactionUseCase
import com.example.bittrack.domain.use_case.GetBalanceUseCase
import com.example.bittrack.mock.TransactionRepositoryMock
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionViewModelTest {

    @Before
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `balance updates when flow emits and transaction added`() = runTest {
        val transactionRepository = TransactionRepositoryMock()
        val vm = getViewModel(transactionRepository)

        val now = DateUtils.nowLocalDateTime()

        transactionRepository.saveTransaction(
            Transaction(0, BigDecimal("0.2"), TransactionType.DEPOSIT, TransactionCategory.NONE, now)
        )

        advanceUntilIdle()

        vm.uiState.first { it.balance == BigDecimal("0.2") }

        val amount = BigDecimal("0.1")
        val category = TransactionCategory.GROCERIES
        vm.onEvent(AddTransactionEvent.AddTransaction(amount, category))

        advanceUntilIdle()

        val state = vm.uiState.first { it.balance == BigDecimal("0.1") }
        assertEquals(BigDecimal("0.1"), state.balance)
    }

    private fun getViewModel(transactionRepository: TransactionRepositoryMock): AddTransactionViewModel {
        val dispatcher = UnconfinedTestDispatcher()
        val getBalanceUseCase = GetBalanceUseCase(transactionRepository)
        val addTransactionUseCase = AddTransactionUseCase(transactionRepository, dispatcher)

        return AddTransactionViewModel(
            getBalanceUseCase = getBalanceUseCase,
            addTransactionUseCase = addTransactionUseCase
        )
    }
}
