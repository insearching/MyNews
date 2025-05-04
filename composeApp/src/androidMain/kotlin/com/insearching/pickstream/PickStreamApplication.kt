package com.insearching.pickstream

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.insearching.pickstream.di.initKoin

class PickStreamApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@PickStreamApplication)
        }
    }
}