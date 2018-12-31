package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PublicStashChanges
import com.example.poe_app_kt.model.PoeItemFilter
import org.slf4j.LoggerFactory

class PoeChangeFilter(
        val filterServices: List<PoeItemFilterService>, // services that will interpret a filter and perform actions.
        val filters: List<PoeItemFilter>, // TODO load from disk.
        val threshold: Int = 1
) {
    val log = LoggerFactory.getLogger(PoeChangeFilter::class.simpleName)

    fun filter(stash_changes: PublicStashChanges): List<PoeItem> {
        val stashes = stash_changes.stashes

        val filteredItems = mutableListOf<PoeItem>()

        for (stash in stashes) {
            for (item in stash.items) {
                val matchVector = mutableListOf<Boolean>()
                var passes_mandatory = true
                for (service in filterServices) {
                    matchVector.add(service.filter(item, filters))
                }
                val matchedThreshold = vectorThreshold(matchVector)
//                log.info("item=${item.name} has match threshold $matchedThreshold")
                if (matchedThreshold >= threshold) {
                    filteredItems.add(item)
                }
            }
        }
        return filteredItems
    }

    fun vectorThreshold(matchVector: List<Boolean>): Int {
        var acc = 0
        for (match in matchVector) {
            if (match) {
                acc += 1
            }
        }
        return acc
    }
}