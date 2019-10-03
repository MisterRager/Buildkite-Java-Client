package es.lolrav.buildkiteclient.net

import com.fasterxml.jackson.databind.ObjectMapper
import es.lolrav.buildkiteclient.BuildkitePager
import es.lolrav.buildkiteclient.factory.BuildkiteApiFactory
import es.lolrav.buildkiteclient.factory.BuildkiteOkHttpClientFactory
import es.lolrav.buildkiteclient.factory.JacksonMapperFactory
import es.lolrav.buildkiteclient.model.Pipeline
import es.lolrav.buildkiteclient.model.request.CommandStep
import es.lolrav.buildkiteclient.model.request.CreatePipeline
import es.lolrav.buildkiteclient.model.request.WaitStep
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BuildkiteApiFactoryTest {
    private lateinit var api: BuildkiteApi
    private lateinit var pipelinePager: BuildkitePager<Pipeline>

    @BeforeEach
    fun prepareApiClient() {
        api = BuildkiteApiFactory(
            "east-shore",
            //BuildkiteOkHttpClientFactory("357c613d23c85843e25f5dd746fede014be6cc9c")::client, // read-only all
            BuildkiteOkHttpClientFactory("43f13a63be9bfcffb95209ee61c6d433a8f29177")::client,
            JacksonMapperFactory()::mapper
        ).api

        pipelinePager = BuildkitePager(api::getPipelines)
    }

    @Test
    fun testGetPipelinePage() {
        pipelinePager.readPages()
            .test()
            .await()
            .assertValue { page: Pipeline ->
                true
            }
    }

    @Test
    fun buildPipeline() {
        val pipeline: CreatePipeline =
            CreatePipeline(
                name = "TESTING",
                repository = "git@github.com:MisterRager/Buildkite-Java-Client.git",
                steps = listOf(
                    CommandStep(
                        name = "Build Jar",
                        command = "./gradlew :buildkiteClient:jar"
                    )
                ))

        val objectMapper: ObjectMapper = JacksonMapperFactory().mapper
        println(objectMapper.writeValueAsString(pipeline))

        api.createPipeline(pipeline)
            .test()
            .await()
            .assertValue { newPipeline: Pipeline ->
                true
            }

        api
            .deletePipeline("testing")
            .test()
            .await()
            .assertComplete()
    }
}
