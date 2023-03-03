package com.example.gitreposF101321.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class NetworkConnectivityObserver(private val connectivityManager: ConnectivityManager) :
    LiveData<Boolean>() {
    constructor(context: Context) : this(
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    init {
        postValue(false)
    }

    private val networkCallback = createNetworkCallBack()

    //is being observed
    override fun onActive() {
        super.onActive()
        val request = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(request.build(), networkCallback)
    }

    //is not being observed
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallBack() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }
}