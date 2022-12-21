package com.example.gitreposF101321.repos.data.remote

import com.example.gitreposF101321.repos.data.domainmodel.CommitModel
import com.google.gson.annotations.SerializedName

class CommitRemoteModel(
    @SerializedName("message")
    val message: String,
)

fun CommitRemoteModel.toCommitModel() = CommitModel(this.message)