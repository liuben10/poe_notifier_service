package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilter

abstract class PoeItemFilterChecker {
    abstract fun filterSingle(item: PoeItem, filter: PoeItemFilter): Boolean
    abstract fun shouldUseFilter(filter: PoeItemFilter): Boolean
    fun filter(item: PoeItem, filters: List<PoeItemFilter>): Boolean {
        var passes = false
        for (filter in filters) {
            passes = passes || filterSingle(item, filter)
        }
        return passes
    }
}
