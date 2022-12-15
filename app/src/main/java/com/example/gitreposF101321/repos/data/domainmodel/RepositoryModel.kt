package com.example.gitreposF101321.repos.data.domainmodel

import com.example.gitreposF101321.owner.RepositoryOwnerModel

data class RepositoryModel(
    val id: String,
    val title: String,
    val language: String,
    val size: Int,
    val owner: RepositoryOwnerModel,
)