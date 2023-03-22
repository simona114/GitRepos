package com.example.gitreposF101321.di

import com.example.gitreposF101321.ui.RepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        RepositoriesViewModel(get())
    }
}