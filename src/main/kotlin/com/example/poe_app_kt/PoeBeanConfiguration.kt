package com.example.poe_app_kt

import com.example.poe_app_kt.interceptors.LoggingInterceptor
import com.example.poe_app_kt.model.PoeItemFilter
import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class PoeBeanConfiguration {

    @Bean
    fun restTemplate(@Autowired requestFactory: ClientHttpRequestFactory,
                     @Autowired loggingInterceptor: LoggingInterceptor): RestTemplate {
        val restTemplate = RestTemplate(requestFactory)
        restTemplate.interceptors.add(loggingInterceptor)
        return restTemplate
    }

//    @Bean
//    fun datastore(): Datastore {
//        return  DatastoreOptions.getDefaultInstance().service
//    }

    @Bean
    fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        return BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory())
    }

    @Bean
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }

    @Bean
    fun poeNinjaChecker(@Autowired restTemplate: RestTemplate): PoeNinjaChecker {
        return PoeNinjaChecker(restTemplate)
    }

    @Bean
    fun poeItemFilters(): PoeChangeFilter {
        val filter = PoeItemFilter("Loreweave_Filter", "Betrayal", 0)
        filter.name = "Loreweave"
//        filter.explicitMods.addAll(
//                arrayListOf(
//                    PoeModStringItemFilter(
//                            "^\\+(.+) to maximum Energy Shield$",
//                            40,
//                            true
//                    ),
//                        PoeModStringItemFilter(
//                                "^(.+)% increased Energy Shield$",
//                                30,
//                                true
//                        )
//                )
//        )
        val filters = arrayListOf(filter)
        return PoeChangeFilter()
    }

}