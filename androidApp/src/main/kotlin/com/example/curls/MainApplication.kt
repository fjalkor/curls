package com.example.curls

import android.app.Application
import com.example.curls.di.appModule
import com.example.curls.di.initKoin
import com.example.curls.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin { androidContext(this@MainApplication) }
    }
}