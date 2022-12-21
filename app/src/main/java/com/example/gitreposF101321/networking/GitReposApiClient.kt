package com.example.gitreposF101321.networking

import com.example.gitreposF101321.utils.Util.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitReposApi {

    fun getClient(): GitReposService {

        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GitReposService::class.java)
    }
}