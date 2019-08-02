package es.lolrav.buildkiteclient.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PipelineJsonTest {
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setupObjectMapper() {
        objectMapper = ObjectMapper().apply {
            registerModule(MrBeanModule())
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        }
    }

    @Test
    fun testDeserializeList() {
        objectMapper.readValue<List<Pipeline>>(
            ClassLoader.getSystemClassLoader().getResourceAsStream("pipeline_list_body.json"),
            objectMapper.typeFactory.constructCollectionType(List::class.java, Pipeline::class.java)
        ).let { pipelines: List<Pipeline> ->
            assertEquals(pipelines.size, 1)
            pipelines.first().apply {
                assertEquals("849411f9-9e6d-4739-a0d8-e247088e9b52", id)
                assertEquals("https://api.buildkite.com/v2/organizations/acme-inc/pipelines/my-pipeline", url)
                assertEquals("https://buildkite.com/acme-inc/my-pipeline", webUrl)
                assertEquals("My Pipeline", name)
                assertEquals("my-pipeline", slug)
                assertEquals("git@github.com:acme-inc/my-pipeline.git", repository)
                assertNull(branchConfiguration)
                assertEquals("master", defaultBranch)
                assertNotNull(provider)

                provider!!.apply {
                    assertEquals("github", id)
                    assertEquals("https://webhook.buildkite.com/deliver/xxx", webhookUrl)
                    assertNotNull(settings)

                    settings.apply {
                        assertTrue(publishCommitStatus)
                        assertTrue(buildPullRequests)
                        assertFalse(buildPullRequestForks)
                        assertFalse(buildTags)
                        assertFalse(publishCommitStatusPerStep)
                        assertEquals("acme-inc/my-pipeline", repository)
                        assertEquals("code", triggerMode)
                    }
                }

                assertFalse(skipQueuedBranchBuilds)
                assertNull(skipQueuedBranchBuildsFilter)
                assertFalse(cancelRunningBranchBuilds)
                assertNull(skipQueuedBranchBuildsFilter)
                assertEquals("https://api.buildkite.com/v2/organizations/acme-inc/pipelines/my-pipeline/builds", buildsUrl)
                assertEquals("https://badge.buildkite.com/58b3da999635d0ad2daae5f784e56d264343eb02526f129bfb.svg", badgeUrl)
                assertEquals("2013-09-03 13:24:38 UTC", createdAt)
                assertEquals(0, scheduledBuildsCount)
                assertEquals(0, runningBuildsCount)
                assertEquals(0, runningJobsCount)
                assertEquals(0, waitingJobsCount)
                assertEquals("private", visibility)
            }
        }
    }

    @Test
    fun testDeserializeSingle() {
        objectMapper.readValue<DetailedPipeline>(
            ClassLoader.getSystemClassLoader().getResourceAsStream("pipeline_single_body.json"),
            DetailedPipeline::class.java
        ).let { pipeline ->
            assertNotNull(pipeline)
            pipeline!!.apply {
                                assertEquals("849411f9-9e6d-4739-a0d8-e247088e9b52", id)
                assertEquals("https://api.buildkite.com/v2/organizations/acme-inc/pipelines/my-pipeline", url)
                assertEquals("https://buildkite.com/acme-inc/my-pipeline", webUrl)
                assertEquals("My Pipeline", name)
                assertEquals("my-pipeline", slug)
                assertEquals("git@github.com:acme-inc/my-pipeline.git", repository)
                assertNull(branchConfiguration)
                assertEquals("master", defaultBranch)
                assertNotNull(provider)

                provider!!.apply {
                    assertEquals("github", id)
                    assertEquals("https://webhook.buildkite.com/deliver/xxx", webhookUrl)
                    assertNotNull(settings)

                    settings.apply {
                        assertTrue(publishCommitStatus)
                        assertTrue(buildPullRequests)
                        assertFalse(buildPullRequestForks)
                        assertFalse(buildTags)
                        assertFalse(publishCommitStatusPerStep)
                        assertEquals("acme-inc/my-pipeline", repository)
                        assertEquals("code", triggerMode)
                    }
                }

                assertFalse(skipQueuedBranchBuilds)
                assertNull(skipQueuedBranchBuildsFilter)
                assertFalse(cancelRunningBranchBuilds)
                assertNull(skipQueuedBranchBuildsFilter)
                assertEquals("https://api.buildkite.com/v2/organizations/acme-inc/pipelines/my-pipeline/builds", buildsUrl)
                assertEquals("https://badge.buildkite.com/58b3da999635d0ad2daae5f784e56d264343eb02526f129bfb.svg", badgeUrl)
                assertEquals("2013-09-03 13:24:38 UTC", createdAt)
                assertEquals(0, scheduledBuildsCount)
                assertEquals(0, runningBuildsCount)
                assertEquals(0, runningJobsCount)
                assertEquals(0, waitingJobsCount)
                assertEquals("private", visibility)
                assertEquals(mapOf<String, String>(), env)

                assertEquals(steps.size, 1)
                steps[0].apply {
                    assertEquals("script", type)
                    assertEquals("Test ✅", name)
                    assertEquals("script/test.sh", command)
                    assertEquals("results/*", artifactPaths)
                    assertEquals("master feature/*", branchConfiguration)
                    assertEquals(mapOf<String, String>(), env)
                    assertNull(timeoutInMinutes)
                    assertEquals(listOf<String>(), agentQueryRules)
                }
            }
        }
    }
}