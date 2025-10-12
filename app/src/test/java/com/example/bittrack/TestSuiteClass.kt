package com.example.bittrack

import com.example.bittrack.ui.add_transaction.AddTransactionViewModelTest
import com.example.bittrack.ui.home.HomeViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeViewModelTest::class,
    AddTransactionViewModelTest::class
)
class TestSuiteClass
