package com.igordanilchik.coroutinestest.data

import com.fasterxml.jackson.annotation.JsonProperty


data class Categories(
        @JsonProperty("categories") val categories: List<Category>
) {

    data class Category(
            @JsonProperty("id") val id: Int,
            @JsonProperty("name") val name: String,
            @JsonProperty("pictureUrl") val pictureUrl: String?
    )
}