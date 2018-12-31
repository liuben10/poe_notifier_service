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
                var passes_all_filters = false
                for (service in filterServices) {
                    passes_all_filters  = passes_all_filters || service.filter(item, filters)
                }
//                val matchedThreshold = vectorThreshold(matchVector)
                if (passes_all_filters) {
                    log.info("item=${item.name} with mods=${item.explicitMods} has passed all filters and is being added")
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