# Lift
Simple Android Application update logic component

[![Build Status](https://travis-ci.org/Commit451/Lift.svg?branch=master)](https://travis-ci.org/Commit451/Lift) [![](https://jitpack.io/v/Commit451/Lift.svg)](https://jitpack.io/#Commit451/Lift)

# About
When upgrading a database, you get callbacks such as `onUpgrade(oldVersion, newVersion)`. But, these are only useful and usable when you are dealing with database migrations. What about when you need to upgrade `SharedPreferences`, switch from SQLite to Realm, etc? Lift aims to fill those gaps and allow an upgrade path for any possible app version upgrade you might need, making use of the apps version code provided in the `build.gradle`.

# Usage

```java
//Within Application.onCreate()
Lift.check(this, new Lift.Callback() {
    @Override
    public void onUpgrade(int oldVersion, int newVersion) {
        if (oldVersion == 100 && newVersion == 101) {
            prefs.clear();
            MigrationUtil.migrateToRealm();
            //etc.
        }
    }
});
```
Even if you do not have a needed upgrade right away, you should add the `check` and callback to your app so that it starts tracking the current version of the app for when you do eventually want to provide an upgrade. 

# Note
Due to the way that the version is stored in `SharedPreferences`, if you are adding Lift to an existing app, it will get the first call to `onUpgrade` where the `oldVersion` value is equal to `VERSION_NOT_STORED` (-1).

License
--------

    Copyright 2016 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
