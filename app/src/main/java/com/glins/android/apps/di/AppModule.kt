package com.glins.android.apps.di

import com.glins.android.apps.navigation.AndroidBrowserNavigator
import com.glins.android.common.navigator.BrowserNavigator
import org.koin.dsl.module

val appModule = module {
    single<BrowserNavigator> { AndroidBrowserNavigator(get()) }
}
