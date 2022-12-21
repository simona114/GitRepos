package com.example.gitreposF101321.repos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposF101321.repos.data.ReposRepository
import com.example.gitreposF101321.repos.data.domainmodel.CommitModel
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel
import com.example.gitreposF101321.repos.data.remote.toCommitModel
import com.example.gitreposF101321.repos.data.remote.toRepositoryModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepositoriesViewModel(
    private val repository: ReposRepository
) :
    ViewModel() {

    private var _liveDataRepos = MutableLiveData<List<RepositoryModel>>()
    val liveDataRepos: LiveData<List<RepositoryModel>> = _liveDataRepos

    private var _liveDataCommits = MutableLiveData<List<CommitModel>>()
    val liveDataCommits: LiveData<List<CommitModel>> = _liveDataCommits

    fun requestReposWhenOnline() {
        viewModelScope.launch {
        //todo:optimise the error handling
            try {
                val result = repository.getNewRepos()
                result.map { repoRemoteModel -> repoRemoteModel.toRepositoryModel() }
                    .let { repoModelsList ->
                        _liveDataRepos.postValue(
                            repoModelsList
                        )
                    }

            } catch (e: HttpException) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
            } catch (e: Exception) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                e.printStackTrace()
            }

        }
    }

    fun requestRepoCommitsWhenOnline(repoName:String) {
        viewModelScope.launch {
        //todo:optimise the error handling
            try {
                val commitHolders = repository.getNewCommitsForRepo(repoName)
                val result = commitHolders.map { it.commit }
                result.map { commitRemoteModel ->  commitRemoteModel.toCommitModel()}.let {
                    commitModelsList ->
                    _liveDataCommits.postValue(commitModelsList)
                }


            } catch (e: HttpException) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
            } catch (e: Exception) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                e.printStackTrace()
            }

        }
    }


}