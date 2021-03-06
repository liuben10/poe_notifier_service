package com.example.poe_app_kt

import com.example.poe_app_kt.model.PoeItemFilter
import com.example.poe_app_kt.model.PoeItemFilterContainer
import com.example.poe_app_kt.model.PoeItemFilterResults
import com.example.poe_app_kt.model.PoeItemFilterWSRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
class PublicStashController(
        @Autowired val restTemplate: RestTemplate,
        @Autowired val poeChangeFilter: PoeChangeFilter
) {

    val log = LoggerFactory.getLogger(PublicStashController::class.simpleName)

    @RequestMapping(path = ["/public_stash_items"], method = [RequestMethod.POST], consumes=["application/json"])
    fun fetchItems(
            @RequestParam(required = false, defaultValue = "354012738-366441988-345818264-397526415-374386691") id: String?,
            @RequestBody itemFilters: List<PoeItemFilter>? = listOf()): PoeItemFilterResults {
        log.info("Fetch Items invoked")
        val iterator = PoeStashIterator(restTemplate, checkNotNull(id))
        val changes = iterator.next()
        if (changes != null) {
            return poeChangeFilter.filter(changes, itemFilters)
        }
        if (itemFilters != null) {
            return PoeItemFilterResults(itemFilters.map { it.id to PoeItemFilterContainer(emptyList()) }.toMap())
        }
        return PoeItemFilterResults(emptyMap())
    }

    @MessageMapping("/filter")
    @SendTo("/topic/filters")
    fun fetchItemWS(
        request: PoeItemFilterWSRequest
    ): PoeItemFilterResults {
        log.info("Fetch Item WS Invoked!")
        return fetchItems(request.nextChangeId, request.poeItemFilters)
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