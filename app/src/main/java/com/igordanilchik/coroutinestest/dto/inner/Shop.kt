package com.igordanilchik.coroutinestest.dto.inner

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "shop", strict = false)
class Shop {

    @field:ElementList(name = "categories")  lateinit var categories: List<Category>
    @field:ElementList(name = "offers") lateinit var offers: List<Offer>

}
