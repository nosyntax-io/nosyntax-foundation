package app.mynta.template.android.di

import app.mynta.template.android.data.source.remote.CoreAPI
import app.mynta.template.android.data.source.remote.EnvatoAPI
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

    @Provides
    @Singleton
    fun provideEnvatoApi(): EnvatoAPI {
        return EnvatoAPI.getInstance()
    }
}