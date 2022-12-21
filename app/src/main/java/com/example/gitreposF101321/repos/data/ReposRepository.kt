package com.example.gitreposF101321.repos.data

import com.example.gitreposF101321.repos.data.remote.RepositoriesRemoteDataSource

class ReposRepository(private val remoteDataSource: RepositoriesRemoteDataSource) {

    suspend fun getNewRepos() = remoteDataSource.getAllRepositories()

    suspend fun getNewCommitsForRepo(repo:String) = remoteDataSource.getAllCommitsForRepo(repo)

}