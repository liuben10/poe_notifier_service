package com.example.poe_app_kt

import com.beust.klaxon.Klaxon
import com.example.benja.poebrowser.model.PublicStashChanges
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.*

class PoeStashIterator(private val restTemplate: RestTemplate) : Iterator<PublicStashChanges?> {
    private val poeApiUrl = "http://api.pathofexile.com/public-stash-tabs"

    private val klaxon = Klaxon()
    private var nextChangeId: String? = null

    private val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()
            headers.add("User-Agent", "Mozilla/5.0")
            return headers
        }

    private val urlParams: Map<String, String>
        get() = if (this.nextChangeId != null) {
            hashMapOf("id" to this.nextChangeId!!)
        } else {
            hashMapOf()
        }

    constructor(restTemplate: RestTemplate, seedChangeId: String) : this(restTemplate) {
        this.nextChangeId = seedChangeId
    }

    override fun hasNext(): Boolean {
        return true
    }

    fun exchange(): ResponseEntity<String> {
        val httpEntity = HttpEntity<Any>(null, headers)
        return restTemplate.exchange(
                poeApiUrl + "?id=" + this.nextChangeId,
                HttpMethod.GET,
                httpEntity,
                String::class.java
        )
    }

    override fun next(): PublicStashChanges? {
        val nextBlock = exchange()
        if (nextBlock.body == null) {
            return null
        }
        val nextChange = parseBody(nextBlock.getBody()!!)
        return nextChange
    }

    private fun parseBody(nextBlock: String): PublicStashChanges? {
        val parsed = klaxon.parse<PublicStashChanges>(nextBlock)
        return parsed
    }

    companion object {
        internal val log = LoggerFactory.getLogger(PoeStashIterator::class.java)

        val NEXT_CHANGE_ID_FIELD = "\"next_change_id\""
    }
}