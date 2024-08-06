package io.nosyntax.foundation.di

import android.content.Context
import io.nosyntax.foundation.data.repository.AppConfigRepositoryImpl
import io.nosyntax.foundation.data.source.remote.AppService
import io.nosyntax.foundation.domain.repository.AppConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAppConfigRepository(@ApplicationContext context: Context, appService: AppService): AppConfigRepository {
        return AppConfigRepositoryImpl(context, appService)
    }
}