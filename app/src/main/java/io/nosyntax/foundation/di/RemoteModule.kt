package io.nosyntax.foundation.di

import io.nosyntax.foundation.data.source.remote.AppService
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
    fun provideAppService(): AppService {
        return AppService.getInstance()
    }
}