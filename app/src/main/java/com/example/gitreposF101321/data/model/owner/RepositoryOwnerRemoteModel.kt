package com.example.gitreposF101321.data.model.owner

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerRemoteModel(
    @SerializedName("login")
    val name:String)

fun RepositoryOwnerRemoteModel.toRepositoryOwnerModel() : RepositoryOwnerModel = RepositoryOwnerModel(this.name)