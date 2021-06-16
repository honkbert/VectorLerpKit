package com.robgulley.time

expect class SimpleCalendar {

    var day: Int
    var month: Int
    var year: Int
    var hour: Int
    var min: Int
    var sec: Int
    var tz: Int //TODO
    
    companion object {
        fun fromEpochMilli(epochMilli: Long): SimpleCalendar
        fun now(): SimpleCalendar
    }

    val epochTime: Long
}