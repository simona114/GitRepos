package com.example.gitreposF101321.repos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposF101321.repos.data.ReposRepository
import com.example.gitreposF101321.repos.data.domainmodel.RepositoryModel
import com.example.gitreposF101321.repos.data.remote.toRepositoryModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepositoriesViewModel(
    private val repository: ReposRepository
) :
    ViewModel() {

    private var _liveDataRepos = MutableLiveData<List<RepositoryModel>>()
    val liveDataRepos: LiveData<List<RepositoryModel>> = _liveDataRepos

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
}