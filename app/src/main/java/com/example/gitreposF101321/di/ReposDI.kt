package com.example.gitreposF101321.di

import com.example.gitreposF101321.networking.GitReposApi
import com.example.gitreposF101321.repos.data.ReposRepository
import com.example.gitreposF101321.repos.data.local.MyDatabaseHelper
import com.example.gitreposF101321.repos.data.local.RepositoriesLocalDataSource
import com.example.gitreposF101321.repos.data.remote.RepositoriesRemoteDataSource
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val di = module {
    single {
        GitReposApi.getClient()
    }
    single {
        RepositoriesRemoteDataSource(get())
    }
    single {
        MyDatabaseHelper(get())
    }
    single {
        RepositoriesLocalDataSource(get())
    }
    single {
        ReposRepository(get(), get())
    }
    viewModel {
        RepositoriesViewModel(get())
    }
}