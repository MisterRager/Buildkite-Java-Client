package es.lolrav.buildkiteclient

import es.lolrav.buildkiteclient.model.Pipeline
import es.lolrav.buildkiteclient.net.BuildkiteApi
import io.reactivex.Flowable
import java.net.URL

class BuildkiteClient(
    private val api: BuildkiteApi
) {
    fun getPipelines(fromPage: Int = 0): Flowable<Pipeline> =
        api.getPipelines(fromPage).flatMapPublisher { response ->
            Flowable.fromIterable(response.body()).let { body: Flowable<Pipeline> ->
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
                    ?.let { page -> body.concatWith(getPipelines(page)) }
                    ?: body
            }
        }
}