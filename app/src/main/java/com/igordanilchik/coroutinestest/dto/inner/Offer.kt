package com.igordanilchik.coroutinestest.dto.inner

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementMap
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "offers", strict = false)
class Offer {

    @field:Attribute(name = "id") var id: Int = 0
    @field:Element(name = "url") lateinit var url: String
    @field:Element(name = "categoryId") var categoryId: Int = 0
    @field:Element(name = "name") lateinit var name: String
    @field:Element(name = "picture", required = false) var pictureUrl: String? = null
    @field:Element(name = "price") lateinit var price: String
    @field:Element(name = "description", required = false) var description: String? = null
    @field:ElementMap(entry = "param", key = "name", attribute = true, inline = true, required = false)
    var param: HashMap<String, String>? = null
}
