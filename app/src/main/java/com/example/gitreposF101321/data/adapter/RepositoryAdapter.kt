package com.example.gitreposF101321.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitreposF101321.databinding.ItemRepositoryBinding
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.ui.overview.IRepositoryClickListener

class RepositoryAdapter(var onClick: IRepositoryClickListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    var repositoryList = listOf<RepositoryModel>()

    inner class RepositoryViewHolder(private val repositoriesBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(repositoriesBinding.root) {
        fun bindRepositoryItem(repository: RepositoryModel) {
            repositoriesBinding.apply {
                tvRepositoryTitle.text = repository.title
                tvRepositoryLanguage.text = repository.language

                itemView.setOnClickListener {
                    onClick.onRepositoryClick(repository)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentRepository = repositoryList[position]
        holder.bindRepositoryItem(currentRepository)
    }

    override fun getItemCount() = repositoryList.size

    fun injectList(repoList: List<RepositoryModel>) {
        this.repositoryList = repoList
        notifyDataSetChanged()
    }

}