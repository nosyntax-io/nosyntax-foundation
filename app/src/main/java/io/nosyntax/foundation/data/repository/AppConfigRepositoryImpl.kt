package io.nosyntax.foundation.data.repository

import android.content.Context
import io.nosyntax.foundation.BuildConfig
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.utility.Resource
import io.nosyntax.foundation.core.utility.Utilities.getDtoFromJson
import io.nosyntax.foundation.data.mapper.toAppConfig
import io.nosyntax.foundation.data.source.remote.CoreAPI
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
class AppConfigRepositoryImpl @Inject constructor(private val context: Context, private val coreApi: CoreAPI):
    AppConfigRepository {
    override suspend fun getAppConfig(): Flow<Resource<AppConfig>> {
        return flow {
            emit(Resource.Loading(true))
            if (BuildConfig.APP_REMOTE_CONFIG == "enabled") {
                try {
                    val response = coreApi.appConfig()
                    emit(Resource.Success(response.toAppConfig()))
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> emit(Resource.Error(Constants.NETWORK_FAILURE_EXCEPTION))
                        is HttpException -> emit(Resource.Error(Constants.HTTP_RESPONSE_EXCEPTION))
                        else -> emit(Resource.Error(Constants.MALFORMED_REQUEST_EXCEPTION))
                    }
                }
            } else {
                getDtoFromJson(context, "local/app-config.json", AppConfigDto::class.java)?.let { localConfig ->
                    emit(Resource.Success(localConfig.toAppConfig()))
                } ?: run {
                    emit(Resource.Error(Constants.LOADING_LOCAL_JSON_EXCEPTION))
                }
            }
        }
    }
}