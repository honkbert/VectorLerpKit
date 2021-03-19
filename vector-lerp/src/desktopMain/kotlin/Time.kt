package com.robgulley

import platform.posix.*

actual fun getTimeNanos(): Long =  kotlin.system.getTimeNanos()