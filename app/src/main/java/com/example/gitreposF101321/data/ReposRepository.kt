package com.example.gitreposF101321.data

import com.example.gitreposF101321.data.local.RepoEntity
import com.example.gitreposF101321.data.local.RepositoriesLocalDataSource
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.data.model.repository.RepositoryRemoteModel
import com.example.gitreposF101321.data.model.repository.toRepoEntity
import com.example.gitreposF101321.data.remote.RepositoriesRemoteDataSource
import kotlinx.coroutines.flow.Flow

class ReposRepository(
    private val remoteDataSource: RepositoriesRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource
) {

    suspend fun getNewRepos() = remoteDataSource.getAllRepositories()

    suspend fun getNewCommitsForRepo(repoName: String) =
        remoteDataSource.getAllCommitsForRepo(repoName)

    fun getSavedRepos(): Flow<List<RepoEntity>> = localDataSource.getAllSavedRepos()

    suspend fun saveRepo(repo: RepositoryModel) {
        localDataSource.saveRepo(repo.toRepoEntity())
    }
    suspend fun saveRepo(repo:RepositoryRemoteModel){
        localDataSource.saveRepo(repo.toRepoEntity())
    }
}