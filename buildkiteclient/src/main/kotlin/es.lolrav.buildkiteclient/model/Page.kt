package es.lolrav.buildkiteclient.model

interface Page<T> {
    val items: List<T>

    val first: String?
    val last: String?
    val next: String?
    val prev: String?
}