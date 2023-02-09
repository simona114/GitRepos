package com.example.gitreposF101321.repos.view.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.databinding.FragmentRepositoriesBinding
import com.example.gitreposF101321.repos.data.model.RepositoryModel
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import com.example.gitreposF101321.utils.NetworkConnectivityObserver
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoriesFragment : Fragment(), IRepositoryClickListener {

    private lateinit var binding: FragmentRepositoriesBinding
    private val reposViewModel by sharedViewModel<RepositoriesViewModel>()
    private var repoAdapter: RepositoryAdapter? = null

    private val networkConnectivityObserver by lazy { context?.let { NetworkConnectivityObserver(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectivityObserver?.observe(viewLifecycleOwner){ isNetworkAvailable->
            if(isNetworkAvailable) {
                Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show()
                reposViewModel.requestReposWhenOnline()
            }
            else{
                Toast.makeText(context, "not connected", Toast.LENGTH_SHORT).show()
                reposViewModel.requestReposWhenOffline()
            }
        }

        reposViewModel.liveDataRepos.observe(viewLifecycleOwner) { reposList ->
            repoAdapter = RepositoryAdapter(this)
            repoAdapter?.injectList(reposList as List<RepositoryModel>)

            binding.rvRepositories.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    override fun onRepositoryClick(selectedRepository: RepositoryModel) {
        reposViewModel.liveDataSelectedRepo.postValue(selectedRepository)
        val action =
            RepositoriesFragmentDirections
                .actionRepositoriesFragmentToRepositoryDetailsFragment()
        findNavController().navigate(action)
    }
}
