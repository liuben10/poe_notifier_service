package com.example.benja.poebrowser.model

data class PoeStash(
        val accountName: String = "",
        val lastCharacterName: String = "",
        val id: String = "",
        val stashType: String = "",
        val items: MutableList<PoeItem> = mutableListOf(),
        val public: Boolean = false
) {
    companion object {
        fun createEmpty(other: PoeStash): PoeStash {
            return PoeStash(
                    other.accountName,
                    other.lastCharacterName,
                    other.id,
                    other.stashType,
                    mutableListOf(),
                    other.public)
        }
    }

    fun hasItems(): Boolean {
        return !this.items.isEmpty()
    }
}
