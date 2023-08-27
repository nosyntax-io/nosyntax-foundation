package app.mynta.template.android.core.utility

import app.mynta.template.android.core.utility.Exceptions.INTERNET_CONNECTION_EXCEPTION
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Connectivity.getInstance().isOnline()) {
            throw IOException(INTERNET_CONNECTION_EXCEPTION)
        } else {
            return chain.proceed(chain.request())
        }
    }
}