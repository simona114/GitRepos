package com.example.gitreposF101321.networking

import com.example.gitreposF101321.repos.data.remote.CommitResponseRemoteModel
import com.example.gitreposF101321.repos.data.remote.RepositoryRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GitReposService {
    //"https://api.github.com/users/simona114/repos"
    @GET("users/{user}/repos")
    suspend fun getRepositories(@Path("user") user: String): List<RepositoryRemoteModel>

    //https://api.github.com/repos/simona114/GitRepos/commits
    @GET("repos/{user}/{repo}/commits")
    suspend fun getCommits(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): List<CommitResponseRemoteModel>
}