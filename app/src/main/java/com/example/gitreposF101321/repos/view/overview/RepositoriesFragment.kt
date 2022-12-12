package com.example.gitreposF101321.repos.view.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.R
import com.example.gitreposF101321.databinding.FragmentRepositoriesBinding
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel

class RepositoriesFragment : Fragment() {
    private lateinit var binding: FragmentRepositoriesBinding
    private var repoAdapter: RepositoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myRepos = listOf(
            RepositoryModel("1", "Title1", "Owner"),
            RepositoryModel("2", "Title2", "Owner")
        )

        repoAdapter = RepositoryAdapter()
        repoAdapter?.injectList(myRepos)

        binding.rvRepositories.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}