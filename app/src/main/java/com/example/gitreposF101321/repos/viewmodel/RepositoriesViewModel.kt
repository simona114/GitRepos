package com.example.gitreposF101321.repos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitreposF101321.repos.data.ReposRepository
import com.example.gitreposF101321.repos.data.remote.RepositoryRemoteModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepositoriesViewModel(
    private val repository: ReposRepository
) :
    ViewModel() {

    private var _liveDataRepos = MutableLiveData<List<RepositoryRemoteModel>>()
    val liveDataRepos: LiveData<List<RepositoryRemoteModel>> = _liveDataRepos

    fun requestReposWhenOnline() {
        viewModelScope.launch {
//todo:optimise the error handling
            try {
                val result = repository.getNewRepos()
                _liveDataRepos.postValue(result)

            } catch (e: HttpException) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
            } catch (e: Exception) {
                e.message?.let { Log.e(ReposRepository::class.java.simpleName, it) }
                e.printStackTrace()
            }

        }
    }
}