package com.example.gitreposF101321.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi


@Database(entities = [RepoEntity::class],version = 1 , exportSchema = false)
abstract class ReposDatabase: RoomDatabase() {

    abstract fun getRepoDao(): RepoDao

    companion object {
        @Volatile
        private var INSTANCE: ReposDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): ReposDatabase {
            kotlinx.coroutines.internal.synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ReposDatabase::class.java,
                    "repos_db"
                ).fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}