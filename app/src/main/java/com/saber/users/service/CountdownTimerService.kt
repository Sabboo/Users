package com.saber.users.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CountdownTimerService : Service() {

    private val binder = LocalBinder()

    suspend fun startTimer(): Flow<Int> = withContext(Dispatchers.Default) {
        flow {
            for (i in 5 downTo 0) {
                emit(i)
                delay(1_000)
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): CountdownTimerService = this@CountdownTimerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}