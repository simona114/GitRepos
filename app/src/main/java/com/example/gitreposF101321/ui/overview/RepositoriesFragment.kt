package com.example.gitreposF101321.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.databinding.FragmentRepositoriesBinding
import com.example.gitreposF101321.data.adapter.RepositoryAdapter
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.ui.RepositoriesViewModel
import com.example.gitreposF101321.utils.NetworkConnectivityObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@AndroidEntryPoint
class RepositoriesFragment : Fragment(), IRepositoryClickListener {

    private lateinit var binding: FragmentRepositoriesBinding
    private val reposViewModel: RepositoriesViewModel by viewModels()
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

        networkConnectivityObserver?.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                reposViewModel.requestReposWhenOnline()
            } else {
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_LONG).show()
                reposViewModel.requestReposWhenOffline()
            }
        }

        reposViewModel.liveDataRepos.observe(viewLifecycleOwner) { reposList ->

           if(reposList.isEmpty())
           {
               binding.tvNoAvailableRepositories.visibility = View.VISIBLE
           }
            else{
               repoAdapter = RepositoryAdapter(this)
               repoAdapter?.injectList(reposList as List<RepositoryModel>)

               binding.rvRepositories.apply {
                   adapter = repoAdapter
                   layoutManager = LinearLayoutManager(activity)
               }
           }

        }

        reposViewModel.isLoading.observe(viewLifecycleOwner)
        { isLoading ->
            if (isLoading) {
                binding.apply{
                    pbRepositories.visibility = View.VISIBLE
                    pbRepositories.visibility = View.GONE
                    tvNoAvailableRepositories.visibility = View.GONE
                }


            } else {
                binding.pbRepositories.visibility = View.GONE
            }

        }
    }

    override fun onRepositoryClick(selectedRepository: RepositoryModel) {

        networkConnectivityObserver?.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                reposViewModel.liveDataSelectedRepo.postValue(selectedRepository)
                val action =
                    RepositoriesFragmentDirections
                        .actionRepositoriesFragmentToRepositoryDetailsFragment()
                findNavController().navigate(action)
            } else {
                Snackbar.make(
                    binding.root,
                    "No internet connection. Cannot load repository details",
                    Snackbar.LENGTH_LONG
                ).show()

            }
        }

    }
}
