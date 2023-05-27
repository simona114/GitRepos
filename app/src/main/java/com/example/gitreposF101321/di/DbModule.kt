package com.example.gitreposF101321.di

import android.content.Context
import androidx.room.Room
import com.example.gitreposF101321.data.db.ReposDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ReposDatabase::class.java,
        "android_task_db"
    ).build()

    @Provides
    @Singleton
    fun providesProductDao(db: ReposDatabase) = db.getRepoDao()

}