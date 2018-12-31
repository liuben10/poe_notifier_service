package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PublicStashChanges
import com.example.poe_app_kt.interceptors.LoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*

@RestController
class PublicStashController(
        @Autowired val restTemplate: RestTemplate,
        @Autowired val poeChangeFilter: PoeChangeFilter
) {

    val log = LoggerFactory.getLogger(PublicStashController::class.simpleName)

    @RequestMapping(path = ["/public_stash_items"], method = [RequestMethod.GET])
    fun fetchItems(
            @RequestParam(required = false, defaultValue = "308920373-319864529-301688411-345878146-326915367") id: String?,
            @RequestParam(required = false, defaultValue = "False") shouldFilter: Boolean?): List<PoeItem> {
        log.info("Fetch Items invoked")
        val iterator = PoeStashIterator(restTemplate, checkNotNull(id))
        val changes = iterator.next()
        if (changes != null) {
            return if (shouldFilter != null && shouldFilter == true) {
                poeChangeFilter.filter(changes)
            } else {
                val items = mutableListOf<PoeItem>()
                for(stash in changes.stashes) {
                    for (item in stash.items) {
                        items.add(item)
                    }
                }
                items
            }
        }
        return arrayListOf()
    }

    @RequestMapping(path=["/items_without_parsing"], method = [RequestMethod.GET])
    fun fetchItemsWithoutParsing(@RequestParam(required = false, defaultValue = "308920373-319864529-301688411-345878146-326915367") id: String?): String? {
        log.info("Fetch Items Without Parsing invoked")
        val iterator = PoeStashIterator(restTemplate, checkNotNull(id))
        return iterator.exchange().body
    }

    @RequestMapping(path=["/fast_fib/{n}"], method = [RequestMethod.GET])
    fun calcFib(@PathVariable n: Int): Int {
        var cur = 1
        var prev = 0
        for (i in 0..n) {
            if (i <= 1) {
                prev = 1
                cur = 1
            } else {
                val tmp = cur
                cur = cur + prev
                prev = tmp
            }
        }
        return cur
    }
}