package com.igordanilchik.coroutinestest.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * @author Igor Danilchik
 */

fun <T> ReceiveChannel<T>.debounce(
        time: Long,
        unit: TimeUnit = TimeUnit.MILLISECONDS,
        context: CoroutineContext = Dispatchers.Default
): ReceiveChannel<T> = GlobalScope.produce(context, 0) {
    var nextTime = 0L
    consumeEach {
        val curTime = System.currentTimeMillis()
        if (curTime < nextTime) {
            // not enough time passed from location send
            delay(nextTime - curTime)
            var mostRecent = it
            while (!isEmpty) { mostRecent = receive() } // take the most recently sent without waiting
            nextTime += time // maintain strict time interval between sends
            send(mostRecent)
        } else {
            // big pause between original events
            nextTime = curTime + unit.toMillis(time) // start tracking time interval from scratch
            send(it)
        }
    }
}