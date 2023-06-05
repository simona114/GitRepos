package com.example.gitreposF101321.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposF101321.data.repository.ReposRepository
import com.example.gitreposF101321.data.db.toRepoModel
import com.example.gitreposF101321.data.model.commit.CommitModel
import com.example.gitreposF101321.data.model.repository.RepositoryModel
import com.example.gitreposF101321.data.model.commit.toCommitModel
import com.example.gitreposF101321.data.model.repository.toRepositoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: ReposRepository
) :
    ViewModel() {

    private var _liveDataRepos = MutableLiveData<List<RepositoryModel?>>()
    val liveDataRepos: LiveData<List<RepositoryModel?>> = _liveDataRepos

    var liveDataSelectedRepo = MutableLiveData<RepositoryModel>()

    var liveDataCommits = MutableLiveData<List<CommitModel>>()

    val isLoading = MutableLiveData(false)

    fun requestReposWhenOnline() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val result = repository.getNewRepos()
                result.map { repoRemoteModel -> repoRemoteModel.toRepositoryModel() }
                    .let { repoModelsList ->
                        _liveDataRepos.postValue(
                            repoModelsList
                        )

                        repoModelsList.forEach { repo -> repository.saveRepo(repo) }
                    }
                isLoading.postValue(false)

            } catch (e: Exception) {
                isLoading.postValue(false)

                if (e is HttpException) {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                } else {
                    e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                    e.printStackTrace()
                }
            }
        }
    }

    fun requestReposWhenOffline() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)

                repository.getSavedRepos()
                    .flowOn(Dispatchers.IO)
                    .map { repoEntitiesList ->
                        repoEntitiesList.map { repoEntity ->
                            repoEntity.toRepoModel()
                        }
                    }
                    .catch {
                        isLoading.postValue(false)
                    }.collect { repoEntitiesList ->
                        _liveDataRepos.postValue(repoEntitiesList)
                        isLoading.postValue(false)
                    }

                isLoading.postValue(false)
            } catch (e: Exception) {
                isLoading.postValue(false)

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
                isLoading.postValue(true)
                val commitHolders = repository.getNewCommitsForRepo(repoName)
                val result = commitHolders.map { commitHolder -> commitHolder.commit }
                result.map { commitRemoteModel -> commitRemoteModel.toCommitModel() }
                    .let { commitModelsList ->
                        liveDataCommits.postValue(commitModelsList)
                    }

                isLoading.postValue(false)

            } catch (e: Exception) {
                isLoading.postValue(false)

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