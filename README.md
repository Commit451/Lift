# Lift
Simple Android Application update logic component

[![Build Status](https://travis-ci.org/Commit451/Lift.svg?branch=master)](https://travis-ci.org/Commit451/Lift) [![](https://jitpack.io/v/Commit451/Lift.svg)](https://jitpack.io/#Commit451/Lift)

# About
When upgrading a database, you get callbacks such as `onUpgrade(oldVersion, newVersion)`. But, these are only useful and usable when you are dealing with database migrations. What about when you need to upgrade `SharedPreferences`, switch from SQLite to Realm, etc? Lift aims to fill those gaps and allow an upgrade path for any possible app version upgrade you might need, making use of the apps version code.

# Usage

```kotlin
//Within Application.onCreate()
Lift.check(this, { oldVersion, newVersion ->
    if (oldVersion == Lift.VERSION_NOT_STORED) {
        Toast.makeText(this@App, "This must be a first app launch. Nice", Toast.LENGTH_SHORT)
                .show()
    } else if (oldVersion == 100 && newVersion == 101) {
        Toast.makeText(this@App, "Upgrading from 100 to 101", Toast.LENGTH_SHORT)
                .show()
    }
})
```
Even if you do not have a needed upgrade right away, you should add the `check` and callback to your app so that it starts tracking the current version of the app for when you do eventually want to provide an upgrade. 

# Auto Backup
If you are using [auto backup](https://developer.android.com/guide/topics/data/autobackup.html), you will want to add rules for Lift. See the sample project for a full example, but it should look like this:

Change your manifest to `android:fullBackupContent="@xml/my_backup_rules"`

And your `my_backup_rules` should look like:
```xml
<?xml version="1.0" encoding="utf-8"?>
<full-backup-content>
    <exclude domain="sharedpref" path="lift.xml"/>
</full-backup-content>

```

# Note
Due to the way that the version is stored in `SharedPreferences`, if you are adding Lift to an existing app, and also for the first time the user launches your app, the first callback will have the `oldVersion` value is equal to `VERSION_NOT_STORED` (-1). Make sure you are accounting for this as needed

License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
