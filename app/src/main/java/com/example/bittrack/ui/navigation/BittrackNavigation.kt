package com.example.bittrack.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
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
        slidingComposable(Route.Home.value) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.uiState.collectAsState().value

            HomeScreen(
                homeState = state,
                transactionsFlow = viewModel.transactionFlow,
                onAddTransactionClick = { navController.toAddTransaction() },
                onEvent = viewModel::onEvent,
            )
        }

        slidingComposable(Route.AddTransaction.value) {
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

private fun NavGraphBuilder.slidingComposable(
    route: String,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val duration = 300

    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(duration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(duration)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(duration)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(duration)
            )
        }
    ) {
        content(it)
    }
}

private fun NavController.toAddTransaction(singleTop: Boolean = true) =
    navigate(Route.AddTransaction.value) {
        if (singleTop) launchSingleTop = true
    }
