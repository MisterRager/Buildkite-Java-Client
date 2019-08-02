package es.lolrav.buildkiteclient

import es.lolrav.buildkiteclient.model.Pipeline
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

internal class BuildkiteApiFactoryTest {
    private lateinit var apiClient: BuildkiteApi

    @BeforeEach
    fun prepareApiClient() {
        apiClient = BuildkiteApiFactory("east-shore").api
    }

    @Test
    fun testGetPipelinePage() {
        apiClient.getPipelines().test()
            .await()
            .assertValue { page: Response<List<Pipeline>> ->
                /*
                assertEquals(1, page.size)

                page.items[0].id == "849411f9-9e6d-4739-a0d8-e247088e9b52"
                *
                 */
                true
            }
    }
}