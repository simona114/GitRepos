package com.example.gitreposF101321.repos.data

import com.example.gitreposF101321.repos.data.local.RepositoriesLocalDataSource
import com.example.gitreposF101321.repos.data.model.RepositoryModel
import com.example.gitreposF101321.repos.data.remote.RepositoriesRemoteDataSource

class ReposRepository(
    private val remoteDataSource: RepositoriesRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource
) {

    suspend fun getNewRepos() = remoteDataSource.getAllRepositories()

    suspend fun getNewCommitsForRepo(repoName: String) =
        remoteDataSource.getAllCommitsForRepo(repoName)

    fun saveRepo(repo: RepositoryModel) {
        localDataSource.saveRepository(repo)
    }
}