package com.example.gitreposF101321.repos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposF101321.repos.data.ReposRepository
import com.example.gitreposF101321.repos.data.model.CommitModel
import com.example.gitreposF101321.repos.data.model.RepositoryModel
import com.example.gitreposF101321.repos.data.model.toCommitModel
import com.example.gitreposF101321.repos.data.model.toRepositoryModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RepositoriesViewModel(
    private val repository: ReposRepository
) :
    ViewModel() {

    private var _liveDataRepos = MutableLiveData<List<RepositoryModel?>>()
    val liveDataRepos: LiveData<List<RepositoryModel?>> = _liveDataRepos

    var liveDataSelectedRepo = MutableLiveData<RepositoryModel>()

    var liveDataCommits = MutableLiveData<List<CommitModel>>()

    fun requestReposWhenOnline() {
        viewModelScope.launch {
            try {
                val result = repository.getNewRepos()
                result.map { repoRemoteModel -> repoRemoteModel.toRepositoryModel() }
                    .let { repoModelsList ->
                        _liveDataRepos.postValue(
                            repoModelsList
                        )

                        repoModelsList.forEach { repo -> repository.saveRepo(repo) }
                    }
            } catch (e: Exception) {
                if (e is HttpException) {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                } else {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                    e.printStackTrace()
                }
            }
        }
    }

    fun requestReposWhenOffline(){
        viewModelScope.launch {
            try {
               val result =  repository.getSavedRepos()
                _liveDataRepos.postValue(result)
            }
            catch (e:Exception){
                if (e is IOException) {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                } else {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                    e.printStackTrace()
                }
            }
        }
    }

    fun requestRepoCommitsWhenOnline(repoName: String) {
        viewModelScope.launch {
            try {
                val commitHolders = repository.getNewCommitsForRepo(repoName)
                val result = commitHolders.map { commitHolder -> commitHolder.commit }
                result.map { commitRemoteModel -> commitRemoteModel.toCommitModel() }
                    .let { commitModelsList ->
                        liveDataCommits.postValue(commitModelsList)
                    }
            } catch (e: Exception) {
                if (e is HttpException) {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                } else {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                    e.printStackTrace()
                }
            }
        }
    }
}