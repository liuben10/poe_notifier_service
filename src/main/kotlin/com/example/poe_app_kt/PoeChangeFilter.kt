package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeStash
import com.example.benja.poebrowser.model.PublicStashChanges
import com.example.poe_app_kt.model.PoeItemFilter
import com.example.poe_app_kt.model.PoeItemFilterContainer
import com.example.poe_app_kt.model.PoeItemFilterResults
import com.example.poe_app_kt.model.PoeStashAndItemContainer
import org.slf4j.LoggerFactory

class PoeChangeFilter {
    val log = LoggerFactory.getLogger(PoeChangeFilter::class.simpleName)

    fun filter(stash_changes: PublicStashChanges, filters: List<PoeItemFilter>? = mutableListOf()): PoeItemFilterResults {
        val stashes = stash_changes.stashes

        val filterToItems = mutableMapOf<Long, PoeItemFilterContainer>().withDefault { PoeItemFilterContainer(mutableListOf()) }

        if (filters != null) {
            for (filter in filters) {
                val stashesWithFilteredItems = mutableListOf<PoeStash>()
                for (stash in stashes) {
                    val stashWithItems = PoeStash.createEmpty(stash)
                    for (item in stash.items) {
                        val filterCheckers = PoeFilterLoader.fromFilter(filter)
                        var passesIndividualFilter = true
                        for (checker in filterCheckers) {
                            passesIndividualFilter  = passesIndividualFilter && checker.filter(item, filter)
                        }
                        if (passesIndividualFilter) {
                            log.info("item=${item} Passed the Individual Filter with id=${filter.id}")
                            stashWithItems.items.add(item)
                        }
                    }
                    if (stashWithItems.hasItems()) {
                        stashesWithFilteredItems.add(stashWithItems)
                    }
                }
                filterToItems[filter.id] = PoeItemFilterContainer(stashesWithFilteredItems)
            }
            return PoeItemFilterResults(filterToItems)
        }
        return PoeItemFilterResults(mutableMapOf(-1L to PoeItemFilterContainer(stashes)))
    }
}