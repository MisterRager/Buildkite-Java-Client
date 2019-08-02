package es.lolrav.buildkiteclient.model

interface ProviderSettings {
    val buildPullRequests: Boolean
    val buildPullRequestForks: Boolean
    val publishCommitStatus: Boolean
    val publishCommitStatusPerStep: Boolean
    val buildTags: Boolean
    val repository: String
    val triggerMode: String
    val filterEnabled: Boolean?
    val publishBlockedAsPending: Boolean?
    val skipBuildsForExistingCommits: Boolean?
    val skipPullRequestBuildsForExistingCommits: Boolean?
    val pullRequestBranchFilterEnabled: Boolean?
    val prefixPullRequestForkBranchNames: Boolean?
    val separatePullRequestStatuses: Boolean?
}