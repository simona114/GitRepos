package com.example.gitreposF101321

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ReposApplication)
            modules(listOf(di))
        }
    }

}