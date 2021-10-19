package com.davidcastella.gnbtransactions

import android.app.Application
import com.davidcastella.gnbtransactions.di.mainModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class GNBApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule)
        }
    }
}