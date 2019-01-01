package com.example.poe_app_kt.model

import com.example.benja.poebrowser.model.PoeItem
import com.example.benja.poebrowser.model.PoeStash

class PoeStashAndItemContainer (
       private val stashList: MutableList<PoeStash> = mutableListOf(),
       private val stashPos: MutableMap<String, Int> = mutableMapOf()
) {

    var tail = 0

    fun getStashList(): List<PoeStash> {
        return stashList
    }

    fun addItemToStash(item: PoeItem, stash: PoeStash) {
        val stashId = stash.id
        if (stashPos.containsKey(stashId)) {
            val stashIdx = this.stashPos[stashId]
            stashList[stashIdx!!].items.add(item)
        } else {
            val copy = PoeStash.createEmpty(stash)
            copy.items.add(item)
            stashList.add(copy)
            stashPos[stashId] = tail
            tail += 1
        }
    }
}