package es.lolrav.buildkiteclient.net

import es.lolrav.buildkiteclient.model.Artifact
import es.lolrav.buildkiteclient.model.Pipeline
import es.lolrav.buildkiteclient.model.request.CreatePipeline
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface BuildkiteApi {
    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/jobs/{jobId}/artifacts/{id}")
    fun getArtifact(
        @Path("pipelineSlug") pipelineSlug: String,
        @Path("buildNumber") buildNumber: Int,
        @Path("jobId") jobId: String,
        @Path("id") id: String
    ): Single<Artifact>

    @GET("pipelines/{pipelineSlug}/builds/{buildNumber}/artifacts")
    fun getBuildArtifacts(
        @Path("pipelineSlug") pipelineSlug: String,
        @Path("buildNumber") buildNumber: Int
    ): Single<Response<List<Artifact>>>

    @GET("pipelines")
    fun getPipelines(@Query("page") page: Int): Single<Response<List<Pipeline>>>

    @POST("pipelines")
    fun createPipeline(@Body pipeline: CreatePipeline): Single<Pipeline>

    @DELETE("pipelines/{slug}")
    fun deletePipeline(@Path("slug") slug: String): Completable
}
