package com.example.gitreposF101321.repos.view.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.databinding.FragmentRepositoriesBinding
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : Fragment(), IRepositoryClickListener {
    private lateinit var binding: FragmentRepositoriesBinding
    private var repoAdapter: RepositoryAdapter? = null

    private val reposViewModel by viewModel<RepositoriesViewModel>()

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

        reposViewModel.requestReposWhenOnline()

        reposViewModel.liveDataRepos.observe(viewLifecycleOwner) { reposList ->
            repoAdapter = RepositoryAdapter(this)

            repoAdapter?.injectList(reposList)

            binding.rvRepositories.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(activity)
            }

        }

    }

    override fun onRepositoryClick(selectedRepositoryId: String) {
        Log.d("RepositoriesFragment", "onRepositoryClick: $selectedRepositoryId")
    }
}