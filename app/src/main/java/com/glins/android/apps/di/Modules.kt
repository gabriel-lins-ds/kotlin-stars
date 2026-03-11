package com.glins.android.apps.di

import androidx.room.Room
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.remote.KotlinStarsRemoteDataSource
import com.glins.android.apps.data.remote.api.KotlinStarsApi
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.data.repository.KotlinStarsRepositoryImpl
import com.glins.android.apps.presentation.viewmodel.RepositoryDetailsViewModel
import com.glins.android.apps.presentation.viewmodel.RepositoryListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules: List<Module>
    get() = listOf(
        networkModule,
        databaseModule,
        dataModule,
        viewModelModule
    )

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<KotlinStarsApi> {
        get<Retrofit>().create(KotlinStarsApi::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "repositories.db"
        ).build()
    }

    single<RepositoryDao> {
        get<AppDatabase>().repositoryDao()
    }
}

val dataModule = module {
    single {
        KotlinStarsRemoteDataSource(
            api = get()
        )
    }

    single {
        KotlinStarsLocalDataSource(
            dao = get()
        )
    }

    single<KotlinStarsRepository> {
        KotlinStarsRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

val viewModelModule = module {
    viewModel {
        RepositoryListViewModel(
            repository = get()
        )
    }

    viewModel {
        RepositoryDetailsViewModel(
            savedStateHandle = get(),
            repository = get()
        )
    }
}