package com.example.gitreposF101321.di

import com.example.gitreposF101321.worker.RefreshRepositoriesDataWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module



val workerManagerModule = module {
//    single { WorkManager.getInstance(get()) }
    worker { RefreshRepositoriesDataWorker(androidContext(), get()) }
}