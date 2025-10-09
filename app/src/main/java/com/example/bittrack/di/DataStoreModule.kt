package com.example.bittrack.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.bittrack.data.local.datastore.BtcRateSerializer
import com.example.bittrack.proto.BtcRate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val BTC_RATE_FILE = "btc_rate.pb"

    @Singleton
    @Provides
    fun provideBtcRateDataStore(
        @ApplicationContext appContext: Context,
        @AppCoroutineScope applicationScope: CoroutineScope,
    ): DataStore<BtcRate> = DataStoreFactory.create(
        serializer = BtcRateSerializer,
        scope = applicationScope
    ) {
        appContext.dataStoreFile(BTC_RATE_FILE)
    }
}
