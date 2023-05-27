package com.example.gitreposF101321.di

import com.example.gitreposF101321.data.repository.ReposRepository
import com.example.gitreposF101321.data.repository.RepositoriesLocalDataSource
import com.example.gitreposF101321.data.repository.RepositoriesRemoteDataSource
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