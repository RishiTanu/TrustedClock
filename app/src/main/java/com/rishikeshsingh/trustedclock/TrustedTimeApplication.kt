package com.rishikeshsingh.trustedclock

import android.app.Application
import com.google.android.gms.time.TrustedTime
import com.google.android.gms.time.TrustedTimeClient

class TrustedTimeApplication : Application()  {
    var trustedTimeClient: TrustedTimeClient? = null
        private set

    override fun onCreate() {
        super.onCreate()

        TrustedTime.createClient(this).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trustedTimeClient = task.result
            }
        }
    }
}