package com.example.gitreposF101321.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.gitreposF101321.data.repository.ReposRepository
import com.example.gitreposF101321.data.model.repository.RepositoryRemoteModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@HiltWorker
class RefreshRepositoriesDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: ReposRepository,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val WORK_NAME = "com.example.gitReposF101321.worker.RefreshRepositoriesDataWorker"
    }

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            try {
                synchronizeRepositoriesData()
                Log.i("sync_repos_work", "WorkManager: Work request for sync is run ")
            } catch (e: Exception) {
                Log.e("sync_repos_work", "doWork: ${e.message}")
                e.printStackTrace()
                return@withContext Result.retry()
            }
        }
        return Result.success()
    }

    private suspend fun cacheRepo(repo: RepositoryRemoteModel) {
        try {
            repository.saveRepo(repo)
            Log.d("sync_repos_cache", "cacheRepo: successful")

        } catch (e: Exception) {
            if (e is IOException) {
                Log.e("sync_repos_cache", "cacheRepo: ${e.message}")
            } else {
                Log.e("sync_repos_cache", "cacheRepo: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun synchronizeRepositoriesData() {
        try {
            val reposList = repository.getNewRepos()

            reposList.forEach { repoRemoteModel ->
                cacheRepo(repoRemoteModel)
            }
            Log.d("sync_repos", "synchronizeRepositoriesData: successful ")
        } catch (e: Exception) {
            Log.d("sync_repos", "synchronizeRepositoriesData: ${e.message} ")
            e.printStackTrace()
        }
    }

}
