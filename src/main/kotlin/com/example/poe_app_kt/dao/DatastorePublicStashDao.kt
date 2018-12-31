package com.example.poe_app_kt.dao

import com.example.benja.poebrowser.model.PoeStash
import com.google.cloud.datastore.Datastore
import org.springframework.beans.factory.annotation.Autowired

class DatastorePublicStashDao(@Autowired val datastore: Datastore) : PublicStashDao {

    val keyFactory = datastore.newKeyFactory().setKind("Stash")

    fun save(public_stash: PoeStash) {
        val key = keyFactory.newKey(public_stash.id)

    }
}