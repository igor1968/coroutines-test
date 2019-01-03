package com.igordanilchik.coroutinestest.flows.location.model

import android.location.Location
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * @author Igor Danilchik
 */
interface ILocationModel {
    suspend fun address(location: Location): String
    suspend fun location(): ReceiveChannel<Location>
    fun dispose()
}