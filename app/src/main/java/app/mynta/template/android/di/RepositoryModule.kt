package app.mynta.template.android.di

import app.mynta.template.android.data.repository.AppConfigRepositoryImpl
import app.mynta.template.android.data.repository.LaunchRepositoryImpl
import app.mynta.template.android.data.repository.PoliciesRepositoryImpl
import app.mynta.template.android.data.source.remote.CoreAPI
import app.mynta.template.android.data.source.remote.EnvatoAPI
import app.mynta.template.android.domain.repository.AppConfigRepository
import app.mynta.template.android.domain.repository.LaunchRepository
import app.mynta.template.android.domain.repository.PoliciesRepository
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
    fun providesAppConfigRepository(coreApi: CoreAPI): AppConfigRepository {
        return AppConfigRepositoryImpl(coreApi)
    }

    @Provides
    @Singleton
    fun provideLaunchRepository(coreApi: CoreAPI, envatoApi: EnvatoAPI): LaunchRepository {
        return LaunchRepositoryImpl(coreApi, envatoApi)
    }

    @Provides
    @Singleton
    fun providesPoliciesRepository(coreApi: CoreAPI): PoliciesRepository {
        return PoliciesRepositoryImpl(coreApi)
    }
}