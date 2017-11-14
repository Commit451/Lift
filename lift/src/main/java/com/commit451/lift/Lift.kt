package com.commit451.lift

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Lift, an overall Application version checker.
 *
 * @see [https://github.com/Commit451/Lift](https://github.com/Commit451/Lift)
 */
object Lift {

    private const val FILE_NAME = "lift"
    private const val KEY_VERSION = "version"

    /**
     * Represents the version has not yet been stored
     */
    const val VERSION_NOT_STORED = -1

    /**
     * Keep track of the version changes, storing them properly for use with later version changes.
     * This is the same as calling [.check] with no callback
     *
     * @param context context
     */
    fun track(context: Context) {
        check(context)
    }

    /**
     * Check on if an upgrade is needed. Typically called in [Application.onCreate] as early
     * as possible so that you can properly upgrade your app from one version to the next
     *
     * @param context  context
     * @param callback callback to call to perform the upgrade
     */
    fun check(context: Context, callback: Callback? = null) {
        val prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val storedVersion = prefs.getInt(KEY_VERSION, VERSION_NOT_STORED)
        val currentVersion =  context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        if (storedVersion < currentVersion) {
            callback?.invoke(storedVersion, currentVersion)
            storeVersion(prefs, currentVersion)
        }
    }

    private fun storeVersion(prefs: SharedPreferences, version: Int) {
        prefs.edit().putInt(KEY_VERSION, version).apply()
    }
}

typealias Callback = (oldVersion: Int, newVersion: Int) -> Unit
