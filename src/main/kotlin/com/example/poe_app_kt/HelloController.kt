package com.example.poe_app_kt

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    val log = LoggerFactory.getLogger(HelloController::class.simpleName)
    @RequestMapping("/", method = [RequestMethod.GET])
    fun hello(): String {
        log.info("Health Endpoint Invoked")
        return "Hello World!"
    }
}