package com.igordanilchik.coroutinestest.data

import com.fasterxml.jackson.annotation.JsonProperty


data class Offers(
        @JsonProperty("offers") val offers: List<Offer>
) {

    data class Offer(
            @JsonProperty("id") val id: Int,
            @JsonProperty("url") val url: String,
            @JsonProperty("name") val name: String,
            @JsonProperty("price") val price: String,
            @JsonProperty("description") val description: String?,
            @JsonProperty("picture") val picture: String?,
            @JsonProperty("categoryId") val categoryId: Int,
            @JsonProperty("param") val param: List<Param>?
    ) {

        data class Param(
                @JsonProperty("name") val name: String,
                @JsonProperty("value") val value: String
        )
    }
}