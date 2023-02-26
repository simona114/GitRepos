package com.example.gitreposF101321.data.model.commit

import com.example.gitreposF101321.data.model.commit.CommitModel
import com.google.gson.annotations.SerializedName

class CommitRemoteModel(
    @SerializedName("message")
    val message: String,
)

fun CommitRemoteModel.toCommitModel() = CommitModel(this.message)