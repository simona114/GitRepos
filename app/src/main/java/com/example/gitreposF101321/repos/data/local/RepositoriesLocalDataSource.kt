package com.example.gitreposF101321.repos.data.local

import com.example.gitreposF101321.repos.data.model.RepositoryModel

class RepositoriesLocalDataSource(private val reposDao: MyDatabaseHelper) {

    fun saveRepository(repository: RepositoryModel) {
        reposDao.saveRepository(repository)
    }
}