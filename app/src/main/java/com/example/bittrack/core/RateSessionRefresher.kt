package com.example.bittrack.core

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.bittrack.di.AppCoroutineScope
import com.example.bittrack.domain.use_case.RefreshBtcRateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

class RateSessionRefresher @Inject constructor(
    private val refreshBtcRateUseCase: RefreshBtcRateUseCase,
    @AppCoroutineScope private val applicationScope: CoroutineScope
) : DefaultLifecycleObserver {

    private val refreshInterval = 1.hours

    override fun onStart(owner: LifecycleOwner) {
        applicationScope.launch {
            refreshBtcRateUseCase(refreshInterval)
        }
    }

    fun attach() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}
