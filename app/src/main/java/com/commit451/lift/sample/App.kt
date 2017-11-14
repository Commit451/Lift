package com.commit451.lift.sample

import android.app.Application
import android.widget.Toast

import com.commit451.lift.Lift

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Lift.check(this) { oldVersion, newVersion ->
            if (oldVersion == Lift.VERSION_NOT_STORED) {
                Toast.makeText(this@App, "This must be a first app launch. Nice", Toast.LENGTH_SHORT)
                        .show()
            } else if (oldVersion == 101 && newVersion == 102) {
                Toast.makeText(this@App, "Upgrading from 100 to 101", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}
