package com.example.gitreposF101321.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitreposF101321.data.model.owner.RepositoryOwnerModel
import com.example.gitreposF101321.data.model.repository.RepositoryModel

@Entity(tableName = "repos")
class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val id: String,
    @ColumnInfo(name = "repo_title")
    val repoTitle: String,
    @ColumnInfo(name = "repo_owner")
    val repoOwner: String,
    @ColumnInfo(name = "repo_language")
    val repoLanguage: String,
    @ColumnInfo(name = "repo_link")
    val repoLink: String,
)
fun RepoEntity.toRepoModel() = RepositoryModel(id, repoTitle, RepositoryOwnerModel(repoOwner), repoLanguage, repoLink)
