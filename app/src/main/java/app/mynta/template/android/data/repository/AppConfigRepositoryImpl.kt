package app.mynta.template.android.data.repository

import app.mynta.template.android.core.utility.Exceptions
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.data.mapper.toConfiguration
import app.mynta.template.android.data.source.remote.APIService
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(private val service: APIService): AppConfigRepository {
    override suspend fun getAppConfig(): Flow<Resource<AppConfig>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = service.appConfig()
                emit(Resource.Success(response.toConfiguration()))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(Resource.Error(Exceptions.NETWORK_FAILURE_EXCEPTION))
                    is HttpException -> emit(Resource.Error(Exceptions.HTTP_RESPONSE_EXCEPTION))
                    else -> emit(Resource.Error(Exceptions.MALFORMED_REQUEST_EXCEPTION))
                }
            }
        }
    }
}