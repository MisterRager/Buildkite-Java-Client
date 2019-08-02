package es.lolrav.buildkiteclient.net

import es.lolrav.buildkiteclient.model.Artifact
import es.lolrav.buildkiteclient.model.Pipeline
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BuildkiteApi {
    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/jobs/{jobId}/artifacts/{id}")
    fun getArtifact(): Single<Artifact>

    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/artifacts")
    fun getBuildArtifacts(): Single<Response<List<Artifact>>>

    @GET("pipelines")
    fun getPipelines(@Query("page") page: Int): Single<Response<List<Pipeline>>>
}
