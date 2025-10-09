package com.example.bittrack.di

import com.example.bittrack.data.local.dao.TransactionDao
import com.example.bittrack.data.local.datastore.BtcRateStore
import com.example.bittrack.data.network.CoinCapApi
import com.example.bittrack.data.repository.RateRepositoryImpl
import com.example.bittrack.data.repository.TransactionRepositoryImpl
import com.example.bittrack.domain.repository.RateRepository
import com.example.bittrack.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(transactionDao)

    @Provides
    @Singleton
    fun provideRateRepository(coinCapApi: CoinCapApi, btcRateStore: BtcRateStore): RateRepository =
        RateRepositoryImpl(coinCapApi, btcRateStore)
}
