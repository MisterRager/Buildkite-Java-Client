package es.lolrav.buildkiteclient.model

interface Artifact {
    val id: String
    val jobId: String
    val url: String
    val downloadUrl: String
    val state: State
    val path: String
    val dirname: String
    val mimeType: String
    val fileSize: Long
    val globPath: String
    val originalPath: String
    val sha1Sum: String

    enum class State {
        NEW, ERROR, FINISHED, DELETED
    }
}