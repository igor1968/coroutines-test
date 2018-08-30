package com.igordanilchik.coroutinestest.extensions

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.timeunit.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

/**
 * @author Igor Danilchik
 */

fun <T> ReceiveChannel<T>.debounce(
        time: Long,
        unit: TimeUnit = TimeUnit.MILLISECONDS,
        context: CoroutineContext = DefaultDispatcher
): ReceiveChannel<T> = produce(context) {
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