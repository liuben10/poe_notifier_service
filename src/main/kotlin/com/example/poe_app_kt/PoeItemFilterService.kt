package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilter

data class FilterResult (
        val passed: Boolean,
        val numberPassed: Int? = 0
)

interface PoeItemFilterService {
    fun filter(item: PoeItem, filters: List<PoeItemFilter>): Boolean
}
