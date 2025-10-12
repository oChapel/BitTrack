package com.example.bittrack

import android.app.Application
import com.example.bittrack.core.RateSessionRefresher
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BittrackApp : Application() {

    @Inject lateinit var rateRefresher: RateSessionRefresher

    override fun onCreate() {
        super.onCreate()
        rateRefresher.attach()
    }
}
