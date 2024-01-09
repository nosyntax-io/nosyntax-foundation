package io.nosyntax.template.android.di

import io.nosyntax.template.android.data.source.remote.CoreAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideCoreApi(): CoreAPI {
        return CoreAPI.getInstance()
    }
}