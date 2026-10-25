package com.rishikeshsingh.trustedclock.data.repository

import com.rishikeshsingh.trustedclock.data.datasource.TimeDataSource

class TimeRepository(private val dataSource: TimeDataSource) {

    fun getTimeInfo(): TimeInfo {
        val trusted = dataSource.getTrustedTimeMillis()
        val device = dataSource.getDeviceTimeMillis()

        return TimeInfo(
            trustedTime = trusted,
            deviceTime = device,
            difference = if (trusted != null) trusted - device else null
        )
    }

}

data class TimeInfo(
    val trustedTime: Long?,
    val deviceTime: Long,
    val difference: Long?
)