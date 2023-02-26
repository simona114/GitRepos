package com.example.gitreposF101321.repos.data.remote

import com.example.gitreposF101321.networking.GitReposService
import com.example.gitreposF101321.utils.Constants.Companion.USER_NAME

class RepositoriesRemoteDataSource(private val apiClient:GitReposService) {

    suspend fun getAllRepositories() = apiClient.getRepositories(USER_NAME)

    suspend fun getAllCommitsForRepo(repo:String) = apiClient.getCommits(USER_NAME, repo)
}