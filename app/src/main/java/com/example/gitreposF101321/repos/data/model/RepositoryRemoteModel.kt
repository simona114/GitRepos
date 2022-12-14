package com.example.gitreposF101321.repos.data.model

import com.example.gitreposF101321.owner.RepositoryOwnerRemoteModel
import com.example.gitreposF101321.owner.toRepositoryOwnerModel
import com.example.gitreposF101321.repos.data.model.RepositoryModel
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