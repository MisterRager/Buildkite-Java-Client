package es.lolrav.buildkiteclient.model

interface Provider {
    val id: String
    val webhookUrl: String
    val settings: ProviderSettings
}