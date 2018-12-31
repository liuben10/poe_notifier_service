package com.example.poe_app_kt

import com.example.poe_app_kt.model.PoeItemFilter
import com.example.poe_app_kt.model.PoeSimpleStringModItemChecker
import com.example.poe_app_kt.model.PoeStringModFilterType

class PoeFilterLoader {

    companion object {
        fun fromFilter(filter: PoeItemFilter): List<PoeItemFilterChecker> {
            val filters = mutableListOf<PoeItemFilterChecker>()

            // --- Required Filters
            addLeagueFilter(filters)

            // --- Dependant Filters
            addNameFilter(filter, filters)
            addAbyssJewel(filter, filters)
            addCraftedModsFilter(filter, filters)
            addEnchantModsFilter(filter, filters)
            addExplicitModsFilter(filter, filters)
            addImplicitModsFilter(filter, filters)
            addUtilityModsFilter(filter, filters)
            return filters
        }

        private fun addNameFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.name != null) {
                filters.add(
                        ExactFieldChecker(
                                {it -> it.name},
                                {filter -> filter.name}
                        )
                )
            }
        }

        private fun addLeagueFilter(filters: MutableList<PoeItemFilterChecker>) {
            filters.add(
                    ExactFieldChecker(
                            {it -> it.league},
                            {filter -> filter.league}
                    )
            )
        }

        private fun addCraftedModsFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.craftedMods.size > 0) {
                filters.add(PoeSimpleStringModItemChecker(PoeStringModFilterType.CRAFTED))
            }
        }

        private fun addExplicitModsFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.explicitMods.size > 0) {
                filters.add(PoeSimpleStringModItemChecker(PoeStringModFilterType.EXPLICIT))
            }
        }

        private fun addImplicitModsFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.implicitMods.size > 0) {
                filters.add(PoeSimpleStringModItemChecker(PoeStringModFilterType.CRAFTED))
            }
        }

        private fun addEnchantModsFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.enchantMods.size > 0) {
                filters.add(PoeSimpleStringModItemChecker(PoeStringModFilterType.ENCHANT))
            }
        }

        private fun addUtilityModsFilter(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.utilityMods.size > 0) {
                filters.add(PoeSimpleStringModItemChecker(PoeStringModFilterType.CRAFTED))
            }
        }

        private fun addAbyssJewel(filter: PoeItemFilter, filters: MutableList<PoeItemFilterChecker>) {
            if (filter.abyssJewel != null) {
                filters.add(
                        ExactFieldChecker(
                                {it -> it.abyssJewel},
                                {filter -> filter.abyssJewel}
                        )
                )
            }
        }
    }
}