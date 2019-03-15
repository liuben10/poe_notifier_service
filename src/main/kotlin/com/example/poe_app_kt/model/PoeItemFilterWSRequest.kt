package com.example.poe_app_kt.model

data class PoeItemFilterWSRequest(
        val nextChangeId: String,
        val poeItemFilters: List<PoeItemFilter>
)