package app.mynta.template.android.data.repository

import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.data.mapper.toPolicies
import app.mynta.template.android.data.source.remote.CoreAPI
import app.mynta.template.android.domain.model.policies.Policies
import app.mynta.template.android.domain.repository.PoliciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PoliciesRepositoryImpl @Inject constructor(private val service: CoreAPI): PoliciesRepository {
    override suspend fun getPolicies(): Flow<Resource<Policies>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = service.policies()
                emit(Resource.Success(response.toPolicies()))
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