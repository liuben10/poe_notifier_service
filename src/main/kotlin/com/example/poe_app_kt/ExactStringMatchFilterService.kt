package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilter


typealias FieldExtractor = (PoeItemFilter) -> String?
typealias ItemFieldExtractor = (PoeItem) -> String?
class ExactStringMatchFilterService(
        val itemFieldExtractor: ItemFieldExtractor,
        val fieldExtractor: FieldExtractor
) : PoeItemFilterService() {
    override fun filterSingle(item: PoeItem, filter: PoeItemFilter): Boolean {
        val itemVal = itemFieldExtractor(item)
        val filterVal = fieldExtractor(filter)
        if (itemVal != null && filterVal != null) {
            return itemVal == filterVal
        }
        return false
    }

    override fun shouldUseFilter(filter: PoeItemFilter): Boolean {
        return fieldExtractor(filter) != null
    }
}