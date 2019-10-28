package es.lolrav.buildkiteclient.model

interface Pipeline {
    val id: String
    val url: String
    val webUrl: String
    val name: String
    val description: String
    val slug: String
    val repository: String
    val branchConfiguration: String?
    val defaultBranch: String
    val provider: Provider?
    val skipQueuedBranchBuilds: Boolean
    val skipQueuedBranchBuildsFilter: String?
    val cancelRunningBranchBuilds: Boolean
    val cancelRunningBranchBuildsFilter: String?
    val buildsUrl: String
    val badgeUrl: String
    val createdAt: String
    val scheduledBuildsCount: Int
    val runningBuildsCount: Int
    val runningJobsCount: Int
    val scheduledJobsCount: Int
    val waitingJobsCount: Int
    val visibility: String
    val configuration: String?
    val env: Map<String, String>
    val steps: List<PipelineStep>
}