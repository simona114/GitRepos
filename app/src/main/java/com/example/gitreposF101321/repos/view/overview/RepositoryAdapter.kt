package com.example.gitreposF101321.repos.view.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitreposF101321.databinding.ItemRepositoryBinding
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel

class RepositoryAdapter(var onClick: IRepositoryClickListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    var repositoryList = listOf<RepositoryModel>()

    inner class RepositoryViewHolder(private val repositoriesBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(repositoriesBinding.root) {
        fun bindRepositoryItem(repository: RepositoryModel) {
            repositoriesBinding.apply {
                //provide access to the resources
                val res = itemView.resources

                tvRepositoryTitle.text = repository.title
                tvRepositoryOwner.text = repository.owner.name

                itemView.setOnClickListener {
                    onClick.onRepositoryClick(repository.id)
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