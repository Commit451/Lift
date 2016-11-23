package com.commit451.lift;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * Lift, an overall Application version checker.
 * @see <a href="https://github.com/Commit451/Lift">https://github.com/Commit451/Lift</a>
 */
public class Lift {

    private static final String FILE_NAME = "lift";
    private static final String KEY_VERSION = "version";

    /**
     * Represents the version has not yet been stored
     */
    public static final int VERSION_NOT_STORED = -1;

    /**
     * Check on if an upgrade is needed. Typically called in {@link Application#onCreate()} as early
     * as possible so that you can properly upgrade your app from one version to the next
     *
     * @param context  the application context.
     * @param callback callback to call to perform the upgrade
     */
    public static void check(@NonNull Context context, @NonNull Callback callback) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        int storedVersion = prefs.getInt(KEY_VERSION, VERSION_NOT_STORED);
        int currentVersion;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            currentVersion = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to check version in Lift. Perhaps you are using the wrong context?", e);
        }
        if (storedVersion < currentVersion) {
            callback.onUpgrade(storedVersion, currentVersion);
            storeVersion(prefs, currentVersion);
        }
    }

    private static void storeVersion(SharedPreferences prefs, int version) {
        prefs.edit().putInt(KEY_VERSION, version).apply();
    }

    /**
     * Callback interface for {@link Lift}
     */
    public interface Callback {

        /**
         * Called when a new version of the app has been updated to. Perform update logic here. Will
         * only be called once when the user updates.
         * @param oldVersion the old app version. This can be {@link Lift#VERSION_NOT_STORED} if
         *                   this is the first time the app has run {@link Lift#check(Context, Callback)}
         * @param newVersion the new/current app version
         */
        void onUpgrade(int oldVersion, int newVersion);
    }
}
