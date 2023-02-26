package com.example.gitreposF101321.data.model.commit

import com.google.gson.annotations.SerializedName

class CommitResponseRemoteModel(
    @SerializedName("commit")
    val commit: CommitRemoteModel,
)