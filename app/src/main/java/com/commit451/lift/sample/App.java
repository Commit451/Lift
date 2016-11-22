package com.commit451.lift.sample;

import android.app.Application;
import android.widget.Toast;

import com.commit451.lift.Lift;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lift.check(this, new Lift.Callback() {
            @Override
            public void onUpgrade(int oldVersion, int newVersion) {
                if (oldVersion == Lift.VERSION_NOT_STORED) {
                    Toast.makeText(App.this, "This must be a first app launch. Nice", Toast.LENGTH_SHORT)
                            .show();
                } else if (oldVersion == 101 && newVersion == 102) {
                    Toast.makeText(App.this, "Upgrading from 100 to 101", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
