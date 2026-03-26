package com.glins.android.apps

import android.app.Application
import com.glins.android.data.di.dataModule
import com.glins.android.database.di.databaseModule
import com.glins.android.network.di.networkModule
import com.glins.android.repository_details.di.repositoryDetailsModule
import com.glins.android.repository_list.di.repositoryListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotlinStarsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotlinStarsApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    dataModule,
                    repositoryDetailsModule,
                    repositoryListModule
                )
            )
        }
    }
}