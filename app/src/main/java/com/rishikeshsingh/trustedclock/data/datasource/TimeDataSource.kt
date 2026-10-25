package com.rishikeshsingh.trustedclock.data.datasource

import com.rishikeshsingh.trustedclock.TrustedTimeApplication

class TimeDataSource(private val application: TrustedTimeApplication) {

    fun getTrustedTimeMillis(): Long? {
        return application.trustedTimeClient?.computeCurrentUnixEpochMillis()
    }

    fun getDeviceTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}