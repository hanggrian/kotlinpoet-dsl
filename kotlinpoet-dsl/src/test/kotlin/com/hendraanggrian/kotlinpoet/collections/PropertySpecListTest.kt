package com.hendraanggrian.kotlinpoet.collections

import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.example.Property5
import com.example.Property6
import com.example.Property7
import com.example.Property8
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class PropertySpecListTest {
    private val list = PropertySpecList(mutableListOf())
    private fun list(configuration: PropertySpecListScope.() -> Unit) =
        PropertySpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("property1", Property1::class.asTypeName())
        list.add("property2", Property2::class.asTypeName()) { initializer("value2") }
        list.add("property3", Property3::class.java)
        list.add("property4", Property4::class.java) { initializer("value4") }
        list.add("property5", Property5::class)
        list.add("property6", Property6::class) { initializer("value6") }
        list.add<Property7>("property7")
        list.add<Property8>("property8") { initializer("value8") }
        assertThat(list).containsExactly(
            PropertySpec.builder("property1", Property1::class.java).build(),
            PropertySpec.builder("property2", Property2::class.java).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class.java).build(),
            PropertySpec.builder("property4", Property4::class.java).initializer("value4").build(),
            PropertySpec.builder("property5", Property5::class.java).build(),
            PropertySpec.builder("property6", Property6::class.java).initializer("value6").build(),
            PropertySpec.builder("property7", Property7::class.java).build(),
            PropertySpec.builder("property8", Property8::class.java).initializer("value8").build()
        )
    }

    @Test
    fun set() {
        list["field1"] = Property1::class.asTypeName()
        list["field2"] = Property2::class.java
        list["field3"] = Property3::class
        assertThat(list).containsExactly(
            PropertySpec.builder("field1", Property1::class.java).build(),
            PropertySpec.builder("field2", Property2::class.java).build(),
            PropertySpec.builder("field3", Property3::class.java).build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val property1 by list.adding(Property1::class.asTypeName())
        val property2 by list.adding(Property2::class.asTypeName()) { initializer("value2") }
        val property3 by list.adding(Property3::class.java)
        val property4 by list.adding(Property4::class.java) { initializer("value4") }
        val property5 by list.adding(Property5::class)
        val property6 by list.adding(Property6::class) { initializer("value6") }
        assertThat(list).containsExactly(
            PropertySpec.builder("property1", Property1::class.java).build(),
            PropertySpec.builder("property2", Property2::class.java).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class.java).build(),
            PropertySpec.builder("property4", Property4::class.java).initializer("value4").build(),
            PropertySpec.builder("property5", Property5::class.java).build(),
            PropertySpec.builder("property6", Property6::class.java).initializer("value6").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "property1"(Property1::class.asTypeName()) { initializer("value1") }
            "property2"(Property2::class.java) { initializer("value2") }
            "property3"(Property3::class) { initializer("value3") }
        }
        assertThat(list).containsExactly(
            PropertySpec.builder("property1", Property1::class.java).initializer("value1").build(),
            PropertySpec.builder("property2", Property2::class.java).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class.java).initializer("value3").build()
        )
    }
}
