package app.mynta.template.android.data.repository

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.data.mapper.envato.toAuthorSale
import app.mynta.template.android.data.mapper.toConfiguration
import app.mynta.template.android.data.source.remote.CoreAPI
import app.mynta.template.android.data.source.remote.EnvatoAPI
import app.mynta.template.android.domain.model.launch.Launch
import app.mynta.template.android.domain.repository.LaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaunchRepositoryImpl @Inject constructor(private val coreApi: CoreAPI, private val envatoApi: EnvatoAPI): LaunchRepository {
    override suspend fun launch(): Flow<Resource<Launch>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                coroutineScope {
                    val appConfig = async(Dispatchers.IO) {
                        coreApi.appConfig()
                    }
                    val authorSale = async(Dispatchers.IO) {
                        envatoApi.authorSale(purchaseCode = "cb2c6258-2496-4230-9fe6-615df09cb5181")
                    }
                    emit(Resource.Success(Launch(
                        appConfig = appConfig.await().toConfiguration(),
                        authorSale = authorSale.await().toAuthorSale()
                    )))
                }
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