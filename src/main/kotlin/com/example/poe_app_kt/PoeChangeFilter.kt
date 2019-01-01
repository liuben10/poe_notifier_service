package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PublicStashChanges
import com.example.poe_app_kt.model.PoeItemFilter
import org.slf4j.LoggerFactory

class PoeChangeFilter() {
    val log = LoggerFactory.getLogger(PoeChangeFilter::class.simpleName)

    fun filter(stash_changes: PublicStashChanges, filters: List<PoeItemFilter>? = mutableListOf()): List<PoeItem> {
        val stashes = stash_changes.stashes

        val filteredItems = mutableListOf<PoeItem>()

        for (stash in stashes) {
            for (item in stash.items) {
                val matchVector = mutableListOf<Boolean>()
                var passesAllFilters = true
                if (filters != null) {
                    for (filter in filters) {

                        val filterCheckers = PoeFilterLoader.fromFilter(filter)
                        var passesIndividualFilter = true
                        for (checker in filterCheckers) {
                            passesIndividualFilter  = passesIndividualFilter && checker.filter(item, filter)
                        }
                        if (passesIndividualFilter) {
                            log.info("item=${item} Passed the Individual Filter with id=${filter.id}")
                        }
                        passesAllFilters = passesAllFilters && passesIndividualFilter
                    }
                }
                if (passesAllFilters) {
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