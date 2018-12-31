package com.example.poe_app_kt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class PoeNinjaController(@Autowired val poeNinjaChecker: PoeNinjaChecker) {

    @RequestMapping(method = [RequestMethod.GET], path=["/latest_change_id"])
    fun getNextChangeId(): String {
        return poeNinjaChecker.getNextChangeId()
    }
}