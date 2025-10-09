package com.example.bittrack.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bittrack.proto.BtcRate
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class BtcRateStore @Inject constructor(
    private val dataStore: DataStore<BtcRate>
) {

    val btcRate: Flow<BtcRate> = dataStore.data

    suspend fun setBtcRate(btcRate: BtcRate) {
        try {
            dataStore.updateData { btcRate }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update BTC rate", ioException)
        }
    }
}
