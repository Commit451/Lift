package com.commit451.lift.sample

import android.app.Application
import android.widget.Toast

import com.commit451.lift.Lift

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Lift.check(this) { oldVersion, newVersion ->
            if (oldVersion == Lift.VERSION_NOT_STORED) {
                Toast.makeText(this@App, "This must be a first app launch. Nice", Toast.LENGTH_SHORT)
                        .show()
            } else if (oldVersion != newVersion) {
                Toast.makeText(this@App, "Upgrading from $oldVersion to $newVersion", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}
