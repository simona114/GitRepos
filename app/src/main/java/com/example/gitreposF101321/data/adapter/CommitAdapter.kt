package com.example.gitreposF101321.data.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitreposF101321.databinding.ItemCommitBinding
import com.example.gitreposF101321.data.model.commit.CommitModel

class CommitAdapter() :
    RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {
    var commitsList = listOf<CommitModel>()

    inner class CommitViewHolder(private val commitsBinding: ItemCommitBinding) :
        RecyclerView.ViewHolder(commitsBinding.root) {
        fun bindRepositoryItem(commit: CommitModel) {
            commitsBinding.apply {
                tvCommitMessage.text = commit.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val binding =
            ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val currentRepository = commitsList[position]
        holder.bindRepositoryItem(currentRepository)
    }

    override fun getItemCount() = commitsList.size

    fun injectList(commitList: List<CommitModel>) {
        this.commitsList = commitList
        notifyDataSetChanged()
    }

}