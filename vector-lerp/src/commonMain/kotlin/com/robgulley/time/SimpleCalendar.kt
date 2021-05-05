package com.robgulley.time

expect class SimpleCalendar {
    companion object {
        fun fromEpochMilli(epochMilli: Long): SimpleCalendar
        fun now(): SimpleCalendar
    }
    val epochTime: Long
}