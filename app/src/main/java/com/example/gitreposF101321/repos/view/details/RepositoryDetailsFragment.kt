package com.example.gitreposF101321.repos.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gitreposF101321.databinding.FragmentRepositoryDetailsBinding
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding
    private val reposViewModel by sharedViewModel<RepositoriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reposViewModel.liveDataSelectedRepoTitle.observe(viewLifecycleOwner) { selectedRepoTitle ->
            reposViewModel.requestRepoCommitsWhenOnline(selectedRepoTitle)

        }

        //todo:delete
        reposViewModel.liveDataCommits.observe(viewLifecycleOwner){
                commitsList ->
            commitsList.forEach {
                    commit ->
                Log.d("mCommit", "${commit.message} ")
            }
        }
    }

}