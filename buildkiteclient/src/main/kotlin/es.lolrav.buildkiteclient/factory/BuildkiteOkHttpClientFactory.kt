package es.lolrav.buildkiteclient.factory

import okhttp3.OkHttpClient

class BuildkiteOkHttpClientFactory(
    accessToken: String
) {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build())
            }
            .build()
    }
}