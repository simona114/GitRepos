package com.example.gitreposF101321.data.local

import kotlinx.coroutines.flow.Flow


class RepositoriesLocalDataSource(private val dao: RepoDao) {
    suspend fun saveRepo(repo: RepoEntity) {
        dao.saveItem(repo)
    }

    fun getAllSavedRepos(): Flow<List<RepoEntity>> = dao.getSavedItems()
}