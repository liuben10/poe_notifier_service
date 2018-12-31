package com.example.poe_app_kt

data class PoeModStringItemFilter (
    val regex: String,
    val threshold: Int,
    val necessary: Boolean = false,
    val priority: Int? = 0
)