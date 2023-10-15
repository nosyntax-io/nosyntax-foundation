package app.mynta.template.android.data.repository

import android.content.Context
import app.mynta.template.android.BuildConfig
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.core.utility.Utilities.getDtoFromJson
import app.mynta.template.android.data.mapper.toConfiguration
import app.mynta.template.android.data.source.remote.CoreAPI
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(private val context: Context, private val coreApi: CoreAPI): AppConfigRepository {
    override suspend fun getAppConfig(): Flow<Resource<AppConfig>> {
        return flow {
            emit(Resource.Loading(true))
            if (BuildConfig.APP_REMOTE_CONFIG == "enabled") {
                try {
                    val response = coreApi.appConfig()
                    emit(Resource.Success(response.toConfiguration()))
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> emit(Resource.Error(Constants.NETWORK_FAILURE_EXCEPTION))
                        is HttpException -> emit(Resource.Error(Constants.HTTP_RESPONSE_EXCEPTION))
                        else -> emit(Resource.Error(Constants.MALFORMED_REQUEST_EXCEPTION))
                    }
                }
            } else {
                getDtoFromJson(context, "local/app_config.json", AppConfigDto::class.java)?.let { localConfig ->
                    emit(Resource.Success(localConfig.toConfiguration()))
                } ?: run {
                    emit(Resource.Error(Constants.LOADING_LOCAL_JSON_EXCEPTION))
                }
            }
        }
    }
}