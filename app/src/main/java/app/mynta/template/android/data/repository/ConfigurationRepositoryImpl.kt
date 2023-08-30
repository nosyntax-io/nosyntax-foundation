package app.mynta.template.android.data.repository

import app.mynta.template.android.core.utility.Exceptions
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.data.mapper.toConfiguration
import app.mynta.template.android.data.source.remote.APIService
import app.mynta.template.android.domain.model.Configuration
import app.mynta.template.android.domain.repository.ConfigurationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationRepositoryImpl @Inject constructor(private val service: APIService): ConfigurationRepository {
    override suspend fun getConfiguration(): Flow<Resource<Configuration>> {
        return flow {
            emit(Resource.Loading(true))
            delay(1000L)
            try {
                val response = service.configuration()
                emit(Resource.Success(response.toConfiguration()))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(Resource.Error(Exceptions.NETWORK_FAILURE_EXCEPTION))
                    is HttpException -> emit(Resource.Error(Exceptions.HTTP_RESPONSE_EXCEPTION))
                    else -> emit(Resource.Error(Exceptions.MALFORMED_REQUEST_EXCEPTION))
                }
            }
            emit(Resource.Loading(false))
        }
    }
}