package com.glins.android.apps

import android.app.Application
import com.glins.android.apps.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotlinStarsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KotlinStarsApplication)
            modules(appModules)
        }
    }
}