package com.example.poe_app_kt.model

import com.example.benja.poebrowser.model.PoeItem
import com.example.poe_app_kt.PoeItemFilterChecker
import org.slf4j.LoggerFactory
import java.util.regex.Pattern

class PoeSimpleStringModItemChecker(
        val filterType: PoeStringModFilterType
) : PoeItemFilterChecker() {
    override fun shouldUseFilter(filter: PoeItemFilter): Boolean {
        when(filterType) {
            PoeStringModFilterType.CRAFTED -> return filter.required || !filter.craftedMods.isEmpty()
            PoeStringModFilterType.EXPLICIT -> return filter.required || !filter.explicitMods.isEmpty()
            PoeStringModFilterType.IMPLICIT -> return filter.required || !filter.implicitMods.isEmpty()
            PoeStringModFilterType.ENCHANT -> return filter.required || !filter.enchantMods.isEmpty()
            PoeStringModFilterType.UTILITY -> return filter.required || !filter.utilityMods.isEmpty()
            else -> {
                log.warn("Unrecognized filter type $filterType")
                return false
            }
        }
    }

    val log = LoggerFactory.getLogger(PoeSimpleStringModItemChecker::class.simpleName)

    override fun filter(item: PoeItem, filter: PoeItemFilter): Boolean {
            when(filterType) {
                PoeStringModFilterType.CRAFTED -> return filterCraftedMods(item, filter)
                PoeStringModFilterType.EXPLICIT -> return filterExplicitMods(item, filter)
                PoeStringModFilterType.IMPLICIT -> return filterImplicitMods(item, filter)
                PoeStringModFilterType.ENCHANT -> return filterFromEnchantMods(item, filter)
                PoeStringModFilterType.UTILITY -> return filterFromUtilityMods(item, filter)
                else -> {
                    log.warn("Unrecognized filter type $filterType")
                    return true
                }
            }
        return false
    }

    fun filterCraftedMods(item: PoeItem, filter: PoeItemFilter): Boolean {
        return filterFromModList(item.craftedMods, filter.craftedMods)
    }

    fun filterExplicitMods(item: PoeItem, filter: PoeItemFilter): Boolean {
        return filterFromModList(item.explicitMods, filter.explicitMods)
    }

    fun filterImplicitMods(item: PoeItem, filter: PoeItemFilter): Boolean {
        return filterFromModList(item.implicitMods, filter.implicitMods)
    }

    fun filterFromEnchantMods(item: PoeItem, filter: PoeItemFilter): Boolean {
        return filterFromModList(item.enchantMods, filter.enchantMods)
    }

    fun filterFromUtilityMods(item: PoeItem, filter: PoeItemFilter): Boolean {
        return filterFromModList(item.utilityMods, filter.utilityMods)
    }

    fun filterFromModList(modList: List<String>?, regexFilters: List<PoeModStringItemFilter>): Boolean {
        if (modList == null) {
            return false
        }
        var filterResult = false
        for (mod in modList) {
            for (filter in regexFilters) {
                val pattern = Pattern.compile(filter.regex)
                val matcher = pattern.matcher(mod)
                val numericContainer = mutableListOf<String>()
                while (matcher.find()) {
                    if (matcher.groupCount() >= 1) {
                        numericContainer.add(matcher.group(1))
                    }
                }
                try {
                    filterResult = filterResult || (numericContainer.size > 0 && (numericContainer[0].toInt() >= filter.threshold))
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    log.error("Mod=$mod, regex=$filter.regex")
                }
            }
        }
        return filterResult
    }

}