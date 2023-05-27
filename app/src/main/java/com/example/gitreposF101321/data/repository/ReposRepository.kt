package com.example.gitreposF101321.data.repository

import com.example.gitreposF101321.data.db.RepoEntity
import com.example.gitreposF101321.data.model.commit.CommitModel
import com.example.gitreposF101321.data.model.owner.RepositoryOwnerRemoteModel
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.data.model.repository.RepositoryRemoteModel
import com.example.gitreposF101321.data.model.repository.toRepoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReposRepository  @Inject constructor(
//    private val remoteDataSource: RepositoriesRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource
) {

    suspend fun getNewRepos() = listOf(RepositoryRemoteModel("00000", "Test title", RepositoryOwnerRemoteModel("Simona"), "kotlin", "link"))
//    suspend fun getNewRepos() = remoteDataSource.getAllRepositories()

    suspend fun getNewCommitsForRepo(repoName: String) = emptyList<CommitModel>()
//    suspend fun getNewCommitsForRepo(repoName: String) =
//        remoteDataSource.getAllCommitsForRepo(repoName)

    fun getSavedRepos(): Flow<List<RepoEntity>> = localDataSource.getAllSavedRepos()

    suspend fun saveRepo(repo: RepositoryModel) {
        localDataSource.saveRepo(repo.toRepoEntity())
    }
    suspend fun saveRepo(repo:RepositoryRemoteModel){
        localDataSource.saveRepo(repo.toRepoEntity())
    }
}