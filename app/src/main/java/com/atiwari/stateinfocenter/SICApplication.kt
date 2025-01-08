package com.atiwari.stateinfocenter

import android.app.Application

class SICApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SICApplication
    }
}