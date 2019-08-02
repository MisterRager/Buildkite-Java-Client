package es.lolrav.buildkiteclient

import es.lolrav.buildkiteclient.model.Artifact
import es.lolrav.buildkiteclient.model.Pipeline
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface BuildkiteApi {
    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/jobs/{jobId}/artifacts/{id}")
    fun getArtifact(): Single<Artifact>

    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/artifacts")
    fun getBuildArtifacts(): Single<Response<List<Artifact>>>

    @GET("pipelines")
    fun getPipelines(): Single<Response<List<Pipeline>>>
}
