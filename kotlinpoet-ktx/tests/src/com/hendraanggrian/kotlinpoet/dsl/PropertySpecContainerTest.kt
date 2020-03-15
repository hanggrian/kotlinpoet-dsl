package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.propertySpecOf
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.PropertySpec
import kotlin.test.Test

class PropertySpecContainerTest {
    private val specs = mutableListOf<PropertySpec>()
    private val container = object : PropertySpecContainer() {
        override fun add(spec: PropertySpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: PropertySpecContainerScope.() -> Unit) =
        PropertySpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(propertySpecOf<Property1>("property1"))
        container += propertySpecOf<Property2>("property2")
        assertThat(specs).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.PropertySpecContainerTest"
        container.add("property1", ClassName(packageName, "Property1"))
        container["property2"] = ClassName(packageName, "Property2")
        container { "property3"(ClassName(packageName, "Property3")) { } }
        assertThat(specs).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun javaClass() {
        container.add("property1", Property1::class.java)
        container["property2"] = Property2::class.java
        container { "property3"(Property3::class.java) { } }
        assertThat(specs).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun kotlinClass() {
        container.add("property1", Property1::class)
        container["property2"] = Property2::class
        container { "property3"(Property3::class) { } }
        assertThat(specs).containsExactly(
            propertySpecOf<Property1>("property1"),
            propertySpecOf<Property2>("property2"),
            propertySpecOf<Property3>("property3")
        )
    }

    @Test fun reifiedType() {
        container.add<Property1>("field1")
        container { "field2"<Property2> { } }
        assertThat(specs).containsExactly(
            propertySpecOf<Property1>("field1"),
            propertySpecOf<Property2>("field2")
        )
    }

    class Property1
    class Property2
    class Property3
}