package es.lolrav.buildkiteclient.net

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class BuildkiteApiFactory(
    organizationName: String,
    accessToken: String
) {
    private val baseUri: String by lazy { BUILDKITE_BASE_URI_TEMPLATE.format(organizationName) }
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build())
            }
            .build()
    }

    private val mapper: ObjectMapper by lazy {
        ObjectMapper().apply {
            registerModule(MrBeanModule())
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        }
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUri)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    val api: BuildkiteApi by lazy { retrofit.create(BuildkiteApi::class.java) }
}

private const val BUILDKITE_BASE_URI_TEMPLATE = "https://api.buildkite.com/v2/organizations/%s/"
