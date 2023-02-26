package com.example.gitreposF101321.data

import com.example.gitreposF101321.data.local.RepositoriesLocalDataSource
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.data.remote.RepositoriesRemoteDataSource

class ReposRepository(
    private val remoteDataSource: RepositoriesRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource
) {

    suspend fun getNewRepos() = remoteDataSource.getAllRepositories()

    suspend fun getNewCommitsForRepo(repoName: String) =
        remoteDataSource.getAllCommitsForRepo(repoName)

    fun getSavedRepos() =
        localDataSource.getAllSavedRepos()

    fun saveRepo(repo: RepositoryModel) {
        localDataSource.saveRepository(repo)
    }
}