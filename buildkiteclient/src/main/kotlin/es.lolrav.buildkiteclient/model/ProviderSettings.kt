package es.lolrav.buildkiteclient.model

interface ProviderSettings {
    val publishCommitStatus: Boolean
    val buildPullRequests: Boolean
    val buildPullRequestForks: Boolean
    val buildTags: Boolean
    val publishCommitStatusPerStep: Boolean
    val repository: String
    val triggerMode: String
}