package es.lolrav.buildkiteclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class BuildkiteApiFactory(
    private val organizationName: String,
    okHttpFactory: () -> OkHttpClient = ::OkHttpClient
) {
    private val baseUri: String by lazy { BUILDKITE_BASE_URI_TEMPLATE.format(organizationName) }
    private val client: OkHttpClient by lazy(okHttpFactory)
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
            .build()
    }

    val api: BuildkiteApi by lazy { retrofit.create(BuildkiteApi::class.java) }
}

private const val BUILDKITE_BASE_URI_TEMPLATE = "https://api.buildkite.com/v2/organizations/%s/"
