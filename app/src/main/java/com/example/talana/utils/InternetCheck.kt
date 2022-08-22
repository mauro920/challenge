package com.example.talana.utils

import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.coroutines.coroutineContext

object InternetCheck {
    suspend fun  isNetworkAvailable() = coroutineScope {
        return@coroutineScope try {
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8",53)
            sock.connect(socketAddress,2000)
            sock.close()
            true
        } catch (e: IOException){
            false
        }
    }
}