package com.example.bittrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bittrack.ui.add_transaction.AddTransactionScreen
import com.example.bittrack.ui.add_transaction.AddTransactionViewModel
import com.example.bittrack.ui.home.HomeScreen
import com.example.bittrack.ui.home.HomeViewModel

sealed class Route(val value: String) {
    data object Home : Route("home")
    data object AddTransaction : Route("add_transaction")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.value
    ) {
        composable(Route.Home.value) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.uiState.collectAsState().value

            HomeScreen(
                homeState = state,
                transactionsFlow = viewModel.transactionFlow,
                onAddTransactionClick = { navController.toAddTransaction() },
                onEvent = viewModel::onEvent,
            )
        }

        composable(Route.AddTransaction.value) {
            val viewModel: AddTransactionViewModel = hiltViewModel()
            val state = viewModel.uiState.collectAsState().value

            AddTransactionScreen(
                addTransactionState = state,
                onBack = { navController.navigateUp() },
                onEvent = viewModel::onEvent
            )
        }
    }
}

private fun NavController.toAddTransaction(singleTop: Boolean = true) =
    navigate(Route.AddTransaction.value) {
        if (singleTop) launchSingleTop = true
    }
