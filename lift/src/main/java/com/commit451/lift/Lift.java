package com.commit451.lift;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * Lift, an overall Application version checker.
 */
public class Lift {

    private static final String FILE_NAME = "lift_file_plz_dont_touch";
    private static final String KEY_VERSION = "version";

    /**
     * Check on if an upgrade is needed. Typically called in {@link Application#onCreate()} as early
     * as possible so that you can properly upgrade your app from one version to the next
     *
     * @param context  the application context.
     * @param callback callback to call to perform the upgrade
     */
    public static void check(@NonNull Context context, @NonNull Callback callback) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        int storedVersion = prefs.getInt(KEY_VERSION, 0);
        int currentVersion = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            currentVersion = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //it really won't happen
        }
        if (storedVersion < currentVersion) {
            callback.onUpgrade(storedVersion, currentVersion);
            prefs.edit().putInt(KEY_VERSION, currentVersion).apply();
        }
    }

    /**
     * Callback interface for {@link Lift}
     */
    public interface Callback {

        /**
         * Called when a new version of the app has been updated to. Perform update logic here. Will
         * only be called once when the user updates.
         * @param oldVersion the old app version
         * @param newVersion the new app version
         */
        void onUpgrade(int oldVersion, int newVersion);
    }
}
