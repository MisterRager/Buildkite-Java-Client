package es.lolrav.buildkiteclient.model

interface DetailedPipeline : Pipeline{
    val steps: List<PipelineStep>
    val env: Map<String, String>
}