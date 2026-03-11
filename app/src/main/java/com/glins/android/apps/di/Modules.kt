package com.glins.android.apps.di

import com.glins.android.apps.data.remote.api.KotlinStarsApi
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.data.repository.KotlinStarsRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules: List<Module>
    get() = listOf(
        networkModule,
        repositoryModule,
//        useCaseModule,
//        viewModelModule
    )

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .build()
    }

    single<KotlinStarsApi> {
        get<Retrofit>().create(KotlinStarsApi::class.java)
    }
}

val repositoryModule = module {
    single<KotlinStarsRepository> {
        KotlinStarsRepositoryImpl(
            remote = get(),
            local = get()
        )
    }
}