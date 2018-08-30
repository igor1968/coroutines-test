package com.igordanilchik.coroutinestest.dto.inner

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "yml_catalog", strict = false)
class Catalogue {

    @field:Element(name = "shop") lateinit var shop: Shop
    @field:Attribute(name = "date") lateinit var date: String
}
