package com.example.gitreposF101321.di

import com.example.gitreposF101321.data.local.MyDatabaseHelper
import org.koin.dsl.module

val dbModule = module {
    single {
        MyDatabaseHelper(get())
    }
}