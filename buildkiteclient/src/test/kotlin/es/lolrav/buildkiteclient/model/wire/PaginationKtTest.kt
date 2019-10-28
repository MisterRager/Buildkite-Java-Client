package es.lolrav.buildkiteclient.model.wire

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.net.URL

class PaginationKtTest {
    @Test
    fun testUnpackUrl() {
        assertEquals(
                "https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=3",
                NEXT_URL.unpackUrl())

        assertEquals(
                "https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=1",
                PREV_URL.unpackUrl())

        assertEquals(
                "https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=1",
                FIRST_URL.unpackUrl())

        assertEquals(
                "https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=4",
                LAST_URL.unpackUrl())
    }

    @Test
    fun testUnpackPageNumber() {
        assertEquals(3, NEXT_URL.unpackUrl().let(::URL).getPageNumber())
        assertEquals(1, PREV_URL.unpackUrl().let(::URL).getPageNumber())
        assertEquals(1, FIRST_URL.unpackUrl().let(::URL).getPageNumber())
        assertEquals(4, LAST_URL.unpackUrl().let(::URL).getPageNumber())
    }

    @Test
    fun testUnpackRel() {
        assertEquals("next", NEXT_REL.unpackRel())
        assertEquals("prev", PREV_REL.unpackRel())
        assertEquals("first", FIRST_REL.unpackRel())
        assertEquals("last", LAST_REL.unpackRel())
    }

    @Test
    fun testUnpackLinkHeader() {
        val links = HEADER.readPaginationHeader()

        assertNotNull(links)
        assertEquals(1, links?.first)
        assertEquals(4, links?.last)
        assertEquals(1, links?.prev)
        assertEquals(3, links?.next)
    }
}

const val NEXT_URL = "<https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=3>"
const val PREV_URL = "<https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=1>"
const val FIRST_URL = "<https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=1>"
const val LAST_URL = "<https://api.buildkite.com/v2/organizations/east-shore/pipelines?page=4>"

const val NEXT_REL = "rel=\"next\""
const val PREV_REL = "rel=\"prev\""
const val FIRST_REL = "rel=\"first\""
const val LAST_REL = "rel=\"last\""

const val HEADER =
        "$NEXT_URL; $NEXT_REL, $PREV_URL; $PREV_REL, $FIRST_URL; $FIRST_REL, $LAST_URL; $LAST_REL"
