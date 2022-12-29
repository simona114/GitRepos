package com.example.gitreposF101321.repos.view.overview

import com.example.gitreposF101321.repos.data.model.RepositoryModel

interface IRepositoryClickListener {
    fun onRepositoryClick(selectedRepository:RepositoryModel)
}