package com.maciejpaja.salesmanapp

import android.app.Application
import com.maciejpaja.salesmanapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class SalesmanApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@SalesmanApp)
            modules(appModule)
        }
    }
}