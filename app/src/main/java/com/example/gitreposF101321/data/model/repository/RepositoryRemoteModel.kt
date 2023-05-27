package com.example.gitreposF101321.data.model.repository

import com.example.gitreposF101321.data.local.RepoEntity
import com.example.gitreposF101321.data.model.owner.RepositoryOwnerRemoteModel
import com.example.gitreposF101321.data.model.owner.toRepositoryOwnerModel
import com.google.gson.annotations.SerializedName

data class RepositoryRemoteModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("owner")
    val owner: RepositoryOwnerRemoteModel,
    @SerializedName("language")
    val language: String,
    @SerializedName("html_url")
    val link: String,
)

fun RepositoryRemoteModel.toRepositoryModel(): RepositoryModel =
    RepositoryModel(
        this.id,
        this.title,
        this.owner.toRepositoryOwnerModel(),
        this.language,
        this.link
    )

fun RepositoryRemoteModel.toRepoEntity(): RepoEntity = RepoEntity(id, title,owner.name, language, link)