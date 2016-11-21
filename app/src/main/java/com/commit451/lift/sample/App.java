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
                if (oldVersion == 100 && newVersion == 101) {
                    Toast.makeText(App.this, "Upgrading from 100 to 101", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
