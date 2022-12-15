package com.example.gitreposF101321.repos.data.remote

import com.example.gitreposF101321.owner.RepositoryOwnerRemoteModel
import com.example.gitreposF101321.owner.toRepositoryOwnerModel
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel
import com.google.gson.annotations.SerializedName

data class RepositoryRemoteModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: RepositoryOwnerRemoteModel,
    @SerializedName("language")
    val language: String,
    @SerializedName("size")
    val size: Int
)

fun RepositoryRemoteModel.toRepositoryModel(): RepositoryModel =
    RepositoryModel(this.id, this.name, this.language, this.size, this.owner.toRepositoryOwnerModel())