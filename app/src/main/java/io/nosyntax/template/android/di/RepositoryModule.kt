package io.nosyntax.template.android.di

import android.content.Context
import io.nosyntax.template.android.data.repository.AppConfigRepositoryImpl
import io.nosyntax.template.android.data.source.remote.CoreAPI
import io.nosyntax.template.android.domain.repository.AppConfigRepository
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
    fun providesAppConfigRepository(@ApplicationContext context: Context, coreApi: CoreAPI): AppConfigRepository {
        return AppConfigRepositoryImpl(context, coreApi)
    }
}