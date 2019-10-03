package es.lolrav.buildkiteclient

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import java.net.URL

class BuildkitePager<T>(
    private val fetchPage: (page: Int) -> Single<Response<List<T>>>
) {
    fun readPages(fromPage: Int = 0): Flowable<T> =
        fetchPage(fromPage).flatMapPublisher { response ->
            Flowable.fromIterable(response.body()).let { body: Flowable<T> ->
                response
                    .headers()["Location"]
                    ?.let { Regex.fromLiteral("<([^>]*)> rel=\"next\"").find(it) }
                    ?.groupValues
                    ?.get(1)
                    ?.let(::URL)
                    ?.query
                    ?.split("&")
                    ?.map {
                        val (parm, value) = it.split("=")
                        parm to value
                    }
                    ?.toMap()
                    ?.get("page")
                    ?.toIntOrNull()
                    ?.let { page -> body.mergeWith(readPages(page)) }
                    ?: body
            }
        }
}