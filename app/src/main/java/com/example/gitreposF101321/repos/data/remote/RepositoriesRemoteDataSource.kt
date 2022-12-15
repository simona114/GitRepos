package com.example.gitreposF101321.repos.data.remote

import com.example.gitreposF101321.networking.GitReposService

class RepositoriesRemoteDataSource(private val apiClient:GitReposService) {

    suspend fun getAllRepositories() = apiClient.getRepositories()
}