package com.example.gitreposF101321.di

import androidx.room.Room
import com.example.gitreposF101321.data.local.ReposDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ReposDatabase::class.java,
            "repos_db"
        ).build()
    }
    single {
        val database = get<ReposDatabase>()
        database.getRepoDao()
    }
}