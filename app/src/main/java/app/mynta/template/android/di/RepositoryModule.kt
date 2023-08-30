package app.mynta.template.android.di

import app.mynta.template.android.data.repository.ConfigurationRepositoryImpl
import app.mynta.template.android.data.source.remote.APIService
import app.mynta.template.android.domain.repository.ConfigurationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providerConfigurationRepository(api: APIService): ConfigurationRepository {
        return ConfigurationRepositoryImpl(api)
    }
}