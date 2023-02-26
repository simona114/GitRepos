package com.example.gitreposF101321.data.model.repository

import com.example.gitreposF101321.data.model.owner.RepositoryOwnerModel

data class RepositoryModel(
    val id: String,
    val title: String,
    val owner: RepositoryOwnerModel,
    val language: String,
    val link: String,
)