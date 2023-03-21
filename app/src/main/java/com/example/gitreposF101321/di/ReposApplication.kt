package com.example.gitreposF101321.di

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.gitreposF101321.worker.RefreshRepositoriesDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class ReposApplication : Application(), KoinComponent, Configuration.Provider {
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ReposApplication)
            workManagerFactory()
            modules(di, workerManagerModule)
        }
        delayedInit()
    }


    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(get<KoinWorkerFactory>())
            .build()
    }


    /**
     * Sets up background tasks, expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    private fun delayedInit() {
        applicationScope.launch {
            setUpSynchronizingReposWork()
        }
    }

    /**
     * Sets up WorkManager to fetch data from the server and save it to the local database hourly
     */
    private fun setUpSynchronizingReposWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshRepositoriesDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RefreshRepositoriesDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
        Log.i(
            "synchronize_repos_work",
            "WorkManager: Periodic Work request for synchronization of repositories is scheduled"
        )

    }

}