package com.example.gitreposF101321.di

import com.example.gitreposF101321.data.ReposRepository
import com.example.gitreposF101321.data.local.RepositoriesLocalDataSource
import com.example.gitreposF101321.data.remote.RepositoriesRemoteDataSource
import org.koin.dsl.module

val repositoryModule = module{
    single {
        RepositoriesRemoteDataSource(get())
    }
    single {
        RepositoriesLocalDataSource(get())
    }
    single {
        ReposRepository(get(), get())
    }
}