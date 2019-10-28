package es.lolrav.buildkiteclient.model.wire

import retrofit2.Response
import java.net.URL

data class PaginationInfo(
        val prev: Int?,
        val next: Int?,
        val first: Int?,
        val last: Int?
)

fun Response<*>.readPagination(): PaginationInfo? = headers()["Link"]?.readPaginationHeader()

internal fun String.readPaginationHeader(): PaginationInfo? =
        split(",")
                .map { it.split(";") }
                .fold(object {
                    var prev: Int? = null
                    var next: Int? = null
                    var first: Int? = null
                    var last: Int? = null
                }) { acc, (url, rel) ->
                    acc.apply {
                        when (rel.unpackRel()) {
                            "next" -> next = url.unpackUrl().let(::URL).getPageNumber()
                            "prev" -> prev = url.unpackUrl().let(::URL).getPageNumber()
                            "first" -> first = url.unpackUrl().let(::URL).getPageNumber()
                            "last" -> last = url.unpackUrl().let(::URL).getPageNumber()
                        }
                    }
                }
                .let {
                    PaginationInfo(
                            prev = it.prev,
                            next = it.next,
                            first = it.first,
                            last = it.last)
                }

internal fun URL.getPageNumber(): Int? =
        ".*[?&]?page=([^&]*)&?"
                .toRegex()
                .find(query)
                ?.groupValues
                ?.get(1)
                ?.toIntOrNull()

internal fun String.unpackRel(): String? =
        "rel=\"([a-z]+)\""
                .toRegex()
                .find(trim())
                ?.groupValues
                ?.get(1)

internal fun String.unpackUrl(): String =
        substring((this.indexOf('<') + 1) until this.lastIndexOf('>'))
