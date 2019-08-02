package es.lolrav.buildkiteclient.model

interface PipelineStep {
    val type: String
    val name: String
    val command: String
    val artifactPaths: String?
    val branchConfiguration: String?
    val env: Map<String, String>
    val timeoutInMinutes: Int?
    val agentQueryRules: List<String>
    val concurrency: String?
    val parallelism: String?
}