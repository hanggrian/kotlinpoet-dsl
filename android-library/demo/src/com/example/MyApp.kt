package com.example

import android.app.Application
import android.util.Log

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApp", "Started")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("MyApp", "Killed")
    }
}