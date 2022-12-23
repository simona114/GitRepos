package com.example.gitreposF101321.repos.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.R
import com.example.gitreposF101321.databinding.FragmentRepositoryDetailsBinding
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding
    private val reposViewModel by sharedViewModel<RepositoriesViewModel>()
    private var commitAdapter: CommitAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reposViewModel.liveDataSelectedRepo.observe(viewLifecycleOwner) { selectedRepo ->

            reposViewModel.requestRepoCommitsWhenOnline(selectedRepo.title)

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
            commitAdapter = CommitAdapter()

            commitAdapter?.injectList(commitsList)

            binding.rvRepositories.apply {
                adapter = commitAdapter
                layoutManager = LinearLayoutManager(activity)
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