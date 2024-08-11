package io.nosyntax.foundation.data.repository

import android.content.Context
import io.nosyntax.foundation.BuildConfig
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.utility.Resource
import io.nosyntax.foundation.core.utility.Utilities.getDtoFromJson
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
                        is IOException -> Resource.Error(Constants.NETWORK_FAILURE_EXCEPTION)
                        is HttpException -> Resource.Error(Constants.HTTP_RESPONSE_EXCEPTION)
                        else -> Resource.Error(Constants.MALFORMED_REQUEST_EXCEPTION)
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
            ?: Resource.Error(Constants.LOADING_LOCAL_JSON_EXCEPTION)
}