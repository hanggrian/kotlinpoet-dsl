package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.propertySpecOf
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class PropertySpecListTest {
    private val list = PropertySpecList(mutableListOf())

    private inline fun container(configuration: PropertySpecListScope.() -> Unit) =
        PropertySpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += propertySpecOf<Property1>("property1")
        list += listOf(propertySpecOf<Property2>("property2"))
        assertThat(list).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.collections.PropertySpecListTest"
        list.add("property1", ClassName(packageName, "Property1"))
        list["property2"] = ClassName(packageName, "Property2")
        container { "property3"(ClassName(packageName, "Property3")) { } }
        assertThat(list).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun javaClass() {
        list.add("property1", Property1::class.java)
        list["property2"] = Property2::class.java
        container { "property3"(Property3::class.java) { } }
        assertThat(list).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun kotlinClass() {
        list.add("property1", Property1::class)
        list["property2"] = Property2::class
        container { "property3"(Property3::class) { } }
        assertThat(list).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun reifiedType() {
        list.add<Property1>("field1")
        container { "field2"<Property2> { } }
        assertThat(list).containsExactly(
            propertySpecOf<Property1>("field1"),
            propertySpecOf<Property2>("field2")
        )
    }

    class Property1
    class Property2
    class Property3
}