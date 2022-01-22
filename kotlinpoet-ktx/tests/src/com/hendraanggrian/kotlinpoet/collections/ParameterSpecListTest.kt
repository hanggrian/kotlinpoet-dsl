package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.internal.Parameter1
import com.hendraanggrian.kotlinpoet.internal.Parameter2
import com.hendraanggrian.kotlinpoet.internal.Parameter3
import com.hendraanggrian.kotlinpoet.internal.Parameter4
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class ParameterSpecListTest {

    private val list = ParameterSpecList(mutableListOf())
    private fun list(configuration: ParameterSpecListScope.() -> Unit) =
        ParameterSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("parameter1", Parameter1::class.asTypeName())
        list.add("parameter2", Parameter2::class.java)
        list.add("parameter3", Parameter3::class)
        list.add<Parameter4>("parameter4")
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).build()
        )
    }

    @Test
    fun set() {
        list["parameter1"] = Parameter1::class.asTypeName()
        list["parameter2"] = Parameter2::class.java
        list["parameter3"] = Parameter3::class
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build()
        )
    }

    @Test
    fun invoke() {
        list {
            "parameter1"(Parameter1::class.asTypeName()) { }
            "parameter2"(Parameter2::class.java) { }
            "parameter3"(Parameter3::class) { }
            "parameter4"<Parameter4> { }
        }
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).build()
        )
    }
}