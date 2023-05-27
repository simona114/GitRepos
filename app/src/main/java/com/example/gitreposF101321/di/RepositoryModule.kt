package com.example.gitreposF101321.di

import com.example.gitreposF101321.data.db.RepoDao
import com.example.gitreposF101321.data.repository.ReposRepository
import com.example.gitreposF101321.data.repository.RepositoriesLocalDataSource
import com.example.gitreposF101321.data.repository.RepositoriesRemoteDataSource
import com.example.gitreposF101321.networking.GitReposService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(dao: RepoDao) = RepositoriesLocalDataSource(dao)

//    @Provides
//    @Singleton
//    fun providesRemoteDataSource(service: GitReposService) = RepositoriesRemoteDataSource(service)

    @Provides
    @Singleton
    fun providesRepository(localDataSource: RepositoriesLocalDataSource) =
        ReposRepository(localDataSource)
    //todo:fix the order
}