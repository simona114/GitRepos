package com.example.gitreposF101321.repos.data.domainmodel

import com.example.gitreposF101321.owner.RepositoryOwnerModel

data class RepositoryModel(
    val id: String,
    val title: String,
    val owner: RepositoryOwnerModel,
    val language: String,
    val size: Int,
    val link: String,
)