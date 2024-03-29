package com.example.gitreposF101321.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.R
import com.example.gitreposF101321.data.adapter.CommitAdapter
import com.example.gitreposF101321.databinding.FragmentRepositoryDetailsBinding
import com.example.gitreposF101321.ui.RepositoriesViewModel
import com.example.gitreposF101321.utils.NetworkConnectivityObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding

    private val reposViewModel: RepositoriesViewModel by activityViewModels()

    private var commitAdapter: CommitAdapter? = null

    private val networkConnectivityObserver by lazy { context?.let { NetworkConnectivityObserver(it) } }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reposViewModel.liveDataSelectedRepo.observe(viewLifecycleOwner) { selectedRepo ->


            networkConnectivityObserver?.observe(viewLifecycleOwner) { isNetworkAvailable ->
                if (isNetworkAvailable) {
                    reposViewModel.requestRepoCommitsWhenOnline(selectedRepo.title)
                } else {
                    Snackbar.make(binding.root, R.string.no_available_network, Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            binding.apply {
                tvRepoOwner.text = getString(R.string.repo_owner, selectedRepo.owner.name)
                tvRepoLanguage.text = getString(R.string.repo_language, selectedRepo.language)

                val customizedString = SpannableStringBuilder()
                    .append(getString(R.string.repo_link)).bold {
                        underline { append(selectedRepo.link) }
                    }
                tvRepoLink.text = customizedString

                tvRepoLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedRepo.link))
                    startActivity(intent)
                }
            }

        }

        reposViewModel.liveDataCommits.observe(viewLifecycleOwner) { commitsList ->

            if (commitsList.isEmpty()) {
                binding.apply {
                    tvNoAvailableCommits.visibility = View.VISIBLE
                    rvCommits.visibility = View.GONE
                }
            } else {
                binding.apply {
                    tvNoAvailableCommits.visibility = View.GONE
                    rvCommits.visibility = View.VISIBLE
                }

                commitAdapter = CommitAdapter()

                commitAdapter?.injectList(commitsList)

                binding.rvCommits.apply {
                    adapter = commitAdapter
                    layoutManager = LinearLayoutManager(activity)

                    val dividerItemDecoration = DividerItemDecoration(
                        context,
                        (layoutManager as LinearLayoutManager).orientation
                    )
                    addItemDecoration(dividerItemDecoration)
                }
            }


        }

        reposViewModel.isLoading.observe(viewLifecycleOwner)
        { isLoading ->
            if (isLoading) {
                binding.apply {
                    pbCommits.visibility = View.VISIBLE
                    rvCommits.visibility = View.GONE
                    tvNoAvailableCommits.visibility = View.GONE
                }

            } else {
                binding.pbCommits.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        reposViewModel.liveDataSelectedRepo.observe(viewLifecycleOwner) { selectedRepo ->
            (activity as AppCompatActivity).supportActionBar?.title = selectedRepo.title
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        reposViewModel.liveDataCommits.postValue(emptyList())
    }

}