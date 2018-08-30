package com.igordanilchik.coroutinestest.common.factory

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author Igor Danilchik
 */
object ObjectMapperFactory {

    @JvmStatic
    fun newInstance(): ObjectMapper {
        val objectMapper = ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)

        objectMapper
                .registerModule(KotlinModule())

        return objectMapper
    }

}
