package com.example.poe_app_kt

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.model.PoeItemFilter

abstract class PoeItemFilterChecker {
    abstract fun filter(item: PoeItem, filter: PoeItemFilter): Boolean
    abstract fun shouldUseFilter(filter: PoeItemFilter): Boolean
}
