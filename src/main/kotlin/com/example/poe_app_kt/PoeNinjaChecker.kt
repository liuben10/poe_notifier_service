package com.example.poe_app_kt

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

typealias WebListener = (String) -> Unit

class PoeNinjaChecker(
        @Autowired val restTemplate: RestTemplate
) {
    val url = "https://poe.ninja/api/Data/GetStats"

    private val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()
            headers.add("User-Agent", "Mozilla/5.0")
            return headers
        }

    fun getNextChangeId(): String {
        val httpEntity = HttpEntity<Any>(null, headers)
        val responseBody = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String::class.java)
        if (!responseBody.hasBody()) {
            return ""
        }
        return extractNextChangeId(responseBody.body)
    }

    private fun extractNextChangeId(raw: String?): String {
        if (raw == null) {
            return ""
        }
        val struct = Klaxon().parse<PoeNinjaIdContainer>(raw)
        return struct!!.nextChangeId
    }
}

data class PoeNinjaIdContainer (
        @Json(name="next_change_id")
        val nextChangeId: String
)