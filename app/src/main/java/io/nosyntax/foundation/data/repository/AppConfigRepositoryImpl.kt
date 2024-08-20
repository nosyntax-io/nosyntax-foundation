package io.nosyntax.foundation.data.repository

import android.content.Context
import io.nosyntax.foundation.BuildConfig
import io.nosyntax.foundation.core.util.Exception
import io.nosyntax.foundation.core.util.Resource
import io.nosyntax.foundation.core.util.Utilities.getDtoFromJson
import io.nosyntax.foundation.data.mapper.toAppConfig
import io.nosyntax.foundation.data.source.remote.AppService
import io.nosyntax.foundation.data.source.remote.dto.app_config.AppConfigDto
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(
    private val context: Context,
    private val service: AppService
) : AppConfigRepository {
    override suspend fun getAppConfig(): Flow<Resource<AppConfig>> = flow {
        emit(Resource.Loading(true))

        val result = if (BuildConfig.DYNAMIC_CONFIG_ENABLED) {
            runCatching { service.appConfig().toAppConfig() }.fold(
                onSuccess = { Resource.Success(it) },
                onFailure = {
                    when (it) {
                        is IOException -> Resource.Error(Exception.NetworkError)
                        is HttpException -> Resource.Error(Exception.ServerError)
                        else -> Resource.Error(Exception.InvalidRequest)
                    }
                }
            )
        } else {
            getLocalAppConfig()
        }

        emit(result)
    }

    private fun getLocalAppConfig(): Resource<AppConfig> =
        getDtoFromJson(context, "local/app-config.json", AppConfigDto::class.java)
            ?.toAppConfig()
            ?.let { Resource.Success(it) }
            ?: Resource.Error(Exception.LocalDataError)
}