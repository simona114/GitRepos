package com.example.gitreposF101321.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: RepoEntity)

    @Query("SELECT * FROM repos")
    fun getSavedItems(): Flow<List<RepoEntity>>
}