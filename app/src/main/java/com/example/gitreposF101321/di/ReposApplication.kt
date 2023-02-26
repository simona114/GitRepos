package com.example.gitreposF101321.di

import android.app.Application
import com.example.gitreposF101321.di.di
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