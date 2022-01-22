package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.internal.Property1
import com.hendraanggrian.kotlinpoet.internal.Property2
import com.hendraanggrian.kotlinpoet.internal.Property3
import com.hendraanggrian.kotlinpoet.internal.Property4
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class PropertySpecListTest {

    private val list = PropertySpecList(mutableListOf())
    private fun list(configuration: PropertySpecListScope.() -> Unit) =
        PropertySpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("field1", Property1::class.asTypeName())
        list.add("field2", Property2::class.java)
        list.add("field3", Property3::class)
        list.add<Property4>("field4")
        assertThat(list).containsExactly(
            PropertySpec.builder("field1", Property1::class.java).build(),
            PropertySpec.builder("field2", Property2::class.java).build(),
            PropertySpec.builder("field3", Property3::class.java).build(),
            PropertySpec.builder("field4", Property4::class.java).build()
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
    fun invoke() {
        list {
            "field1"(Property1::class.asTypeName()) { }
            "field2"(Property2::class.java) { }
            "field3"(Property3::class) { }
            "field4"<Property4> { }
        }
        assertThat(list).containsExactly(
            PropertySpec.builder("field1", Property1::class.java).build(),
            PropertySpec.builder("field2", Property2::class.java).build(),
            PropertySpec.builder("field3", Property3::class.java).build(),
            PropertySpec.builder("field4", Property4::class.java).build()
        )
    }
}