package com.example.gitreposF101321.ui.overview

import com.example.gitreposF101321.data.model.repository.RepositoryModel

interface IRepositoryClickListener {
    fun onRepositoryClick(selectedRepository: RepositoryModel)
}