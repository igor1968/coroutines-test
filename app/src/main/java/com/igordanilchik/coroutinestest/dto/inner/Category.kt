package com.igordanilchik.coroutinestest.dto.inner

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "categories", strict = false)
class Category {

    @field:Attribute(name = "id") var id: Int = 0
    @field:Text lateinit var title: String
}
