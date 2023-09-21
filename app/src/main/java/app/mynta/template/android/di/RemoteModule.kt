package app.mynta.template.android.di

import app.mynta.template.android.data.source.remote.CoreApi
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
    fun provideCoreApi(): CoreApi {
        return CoreApi.getInstance()
    }
}