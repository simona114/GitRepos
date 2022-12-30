package com.example.gitreposF101321.repos.data.local

import com.example.gitreposF101321.repos.data.model.RepositoryModel

class RepositoriesLocalDataSource(private val dbHelper: MyDatabaseHelper) {

    fun saveRepository(repository: RepositoryModel) {
        dbHelper.saveRepository(repository)
    }

    fun getAllSavedRepos() =
        dbHelper.getAllSavedRepositories()
}