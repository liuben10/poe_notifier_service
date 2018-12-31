package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilter

data class FilterResult (
        val passed: Boolean,
        val numberPassed: Int? = 0
)

abstract class PoeItemFilterService {
    abstract fun shouldUseFilter(filter: PoeItemFilter): Boolean
    fun filter(item: PoeItem, filters: List<PoeItemFilter>): Boolean {
        var passes = false
        for (filter in filters) {
            if (shouldUseFilter(filter)) {
                passes = passes || filterSingle(item, filter)
            }
        }
        return passes
    }

    abstract fun filterSingle(item: PoeItem, filter: PoeItemFilter): Boolean
}
