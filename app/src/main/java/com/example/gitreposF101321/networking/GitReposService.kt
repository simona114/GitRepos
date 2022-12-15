package com.example.gitreposF101321.networking

import com.example.gitreposF101321.repos.data.remote.RepositoryRemoteModel
import retrofit2.http.GET

interface GitReposService {

    @GET("jokes/random")
    suspend fun getRepositories():List<RepositoryRemoteModel>
}