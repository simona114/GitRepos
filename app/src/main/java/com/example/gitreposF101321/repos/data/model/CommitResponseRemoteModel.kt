package com.example.gitreposF101321.repos.data.model

import com.google.gson.annotations.SerializedName

class CommitResponseRemoteModel(
    @SerializedName("commit")
    val commit: CommitRemoteModel,
)