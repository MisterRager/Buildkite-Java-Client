package es.lolrav.buildkiteclient.model.request

import com.fasterxml.jackson.annotation.JsonInclude
import es.lolrav.buildkiteclient.model.ProviderSettings

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreatePipeline(
    val name: String,
    val repository: String,
    val steps: Iterable<CreatePipelineStep>,
    val description: String? = null,
    val env: Map<String, String?>? = null,
    val providerSettings: ProviderSettings? = null,
    val branchConfiguration: String? = null,
    val skipQueuedBranchBuilds: Boolean? = null,
    val skipQueuedBranchBuildsFilter: String? = null,
    val cancelRunningBranchBuilds: Boolean? = null,
    val cancelRunningBranchBuildsFilter: String? = null,
    val teamUuids: List<String>? = null)
