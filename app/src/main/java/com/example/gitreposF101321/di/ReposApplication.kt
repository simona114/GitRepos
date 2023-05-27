package com.example.gitreposF101321.di

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.gitreposF101321.worker.RefreshRepositoriesDataWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ReposApplication : Application(), Configuration.Provider {
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    /**
     * Sets up background tasks, expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    private fun delayedInit() {
        applicationScope.launch {
//            setUpSynchronizingReposWork()
        }
    }

    /**
     * Sets up WorkManager to fetch data from the server and save it to the local database hourly
     */
    private fun setUpSynchronizingReposWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshRepositoriesDataWorker>(1, TimeUnit.DAYS)
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