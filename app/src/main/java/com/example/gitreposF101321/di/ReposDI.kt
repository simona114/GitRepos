package com.example.gitreposF101321.di

import com.example.gitreposF101321.networking.GitReposApi
import com.example.gitreposF101321.repos.data.ReposRepository
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
        ReposRepository(get())
    }
    viewModel {
        RepositoriesViewModel(get())
    }
}