package com.ybrflight552.fitnessapp.internet.network.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.utils.lib.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Build


class ConnectionLiveData(private val context: Context) : LiveData<Boolean>() {

    private lateinit var cmCallback: ConnectivityManager.NetworkCallback

    private val cm: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val isConnectedSingleLiveEvent =
        SingleLiveEvent<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = isConnectedSingleLiveEvent

    private val validNetworks: MutableSet<Network> = HashSet()
    private val toastLifeEvent: SingleLiveEvent<Int> =
        SingleLiveEvent<Int>()

    val toast: SingleLiveEvent<Int>
        get() = toastLifeEvent

    // проверяем наличие астивных сетей в сети
    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }


    //проверяем если подключены к сети
    private fun checkForActiveNetworks() {
        Log.d(TAG, "Connection status checkForActiveNetworks")
        CoroutineScope(IO).launch {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               if (cm.activeNetwork == null) {
                   Log.d(
                       TAG,
                       "Connection status checkForActiveNetworks status - ${cm.activeNetwork}"
                   )
                   isConnectedSingleLiveEvent.postValue(false)
               } else {
                   Log.d(
                       TAG,
                       "Connection status checkForActiveNetworks status -  ${cm.activeNetwork}"
                   )
                   isConnectedSingleLiveEvent.postValue(true)
               }
           }
        }
    }

    override fun onActive() {
        checkForActiveNetworks()
        cmCallback = createNetworkCallback()
        val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, cmCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(cmCallback)
    }


    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapabilities =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Log.d(TAG, "Connection status $hasInternetCapabilities")
            // проверяем если подключены к интернет сети
            if (hasInternetCapabilities == true) {
                // проверяем если интернет работает
                CoroutineScope(IO).launch {
                    val hasInternet = InternetConnectivity.execute()
                    if (hasInternet) {
                        withContext(Main) {
                            validNetworks.add(network)
                            toastLifeEvent.postValue(R.string.is_connected)
                            Log.d(TAG, "Connection active")
                            checkValidNetworks()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "Connection lost")
            toastLifeEvent.postValue(R.string.is_not_connected)
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }


    companion object {
        private val TAG = "ConnectionLiveData"
    }
}