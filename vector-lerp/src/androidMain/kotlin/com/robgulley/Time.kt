package com.robgulley

import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal actual fun getTimeNanos(): Long = System.nanoTime()
internal actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual fun getEpochMilliForTime(day: Int, month: Int, year: Int, hour: Int, min: Int, sec: Int, tz: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, min, sec)
    return calendar.timeInMillis
}

internal actual fun getTimeForEpochMilli(epochMilli: Long): SimpleCalendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epochMilli
    return calendar.let {
        SimpleCalendar(
            day = it.get(Calendar.DAY_OF_MONTH),
            month = it.get(Calendar.MONTH),
            year = it.get(Calendar.YEAR),
            hour = it.get(Calendar.HOUR_OF_DAY),
            min = it.get(Calendar.MINUTE),
            sec = it.get(Calendar.SECOND),
        )
    }
}

actual object Sleep {
    actual fun blockFor(timeMillis: Long) = Thread.sleep(timeMillis)
    actual fun blockFor(millis: Long, nanos: Int) = Thread.sleep(millis, nanos)

    @OptIn(ExperimentalTime::class)
    actual fun blockFor(duration: Duration) {
        duration.toComponents { seconds, nanoseconds -> Thread.sleep(seconds * 1000, nanoseconds) }
    }
}