package com.example.gitreposF101321.data.repository

import com.example.gitreposF101321.data.db.RepoDao
import com.example.gitreposF101321.data.db.RepoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RepositoriesLocalDataSource @Inject constructor (private val dao: RepoDao) {
    suspend fun saveRepo(repo: RepoEntity) {
        dao.saveItem(repo)
    }

    fun getAllSavedRepos(): Flow<List<RepoEntity>> = dao.getSavedItems()
}