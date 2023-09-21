package app.mynta.template.android.data.repository

import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.data.mapper.toConfiguration
import app.mynta.template.android.data.source.remote.CoreApi
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(private val service: CoreApi): AppConfigRepository {
    override suspend fun getAppConfig(): Flow<Resource<AppConfig>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = service.appConfig()
                emit(Resource.Success(response.toConfiguration()))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(Resource.Error(Constants.NETWORK_FAILURE_EXCEPTION))
                    is HttpException -> emit(Resource.Error(Constants.HTTP_RESPONSE_EXCEPTION))
                    else -> emit(Resource.Error(Constants.MALFORMED_REQUEST_EXCEPTION))
                }
            }
        }
    }
}