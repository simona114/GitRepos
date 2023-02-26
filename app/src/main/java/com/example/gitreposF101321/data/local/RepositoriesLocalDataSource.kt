package com.example.gitreposF101321.data.local

import com.example.gitreposF101321.data.model.repository.RepositoryModel

class RepositoriesLocalDataSource(private val dbHelper: MyDatabaseHelper) {

    fun saveRepository(repository: RepositoryModel) {
        dbHelper.saveRepository(repository)
    }

    fun getAllSavedRepos() =
        dbHelper.getAllSavedRepositories()
}