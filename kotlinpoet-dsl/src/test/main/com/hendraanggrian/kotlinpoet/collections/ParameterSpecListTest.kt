package com.hendraanggrian.kotlinpoet.collections

import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
import com.example.Parameter6
import com.example.Parameter7
import com.example.Parameter8
import com.google.common.truth.Truth.assertThat
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
        list.add("parameter2", Parameter2::class.asTypeName()) { kdoc.append("text2") }
        list.add("parameter3", Parameter3::class.java)
        list.add("parameter4", Parameter4::class.java) { kdoc.append("text4") }
        list.add("parameter5", Parameter5::class)
        list.add("parameter6", Parameter6::class) { kdoc.append("text6") }
        list.add<Parameter7>("parameter7")
        list.add<Parameter8>("parameter8") { kdoc.append("text8") }
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class.java).build(),
            ParameterSpec.builder("parameter2", Parameter2::class.java).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class.java).build(),
            ParameterSpec.builder("parameter4", Parameter4::class.java).addKdoc("text4").build(),
            ParameterSpec.builder("parameter5", Parameter5::class.java).build(),
            ParameterSpec.builder("parameter6", Parameter6::class.java).addKdoc("text6").build(),
            ParameterSpec.builder("parameter7", Parameter7::class.java).build(),
            ParameterSpec.builder("parameter8", Parameter8::class.java).addKdoc("text8").build()
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
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val parameter1 by list.adding(Parameter1::class.asTypeName())
        val parameter2 by list.adding(Parameter2::class.asTypeName()) { kdoc.append("text2") }
        val parameter3 by list.adding(Parameter3::class.java)
        val parameter4 by list.adding(Parameter4::class.java) { kdoc.append("text4") }
        val parameter5 by list.adding(Parameter5::class)
        val parameter6 by list.adding(Parameter6::class) { kdoc.append("text6") }
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class.java).build(),
            ParameterSpec.builder("parameter2", Parameter2::class.java).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class.java).build(),
            ParameterSpec.builder("parameter4", Parameter4::class.java).addKdoc("text4").build(),
            ParameterSpec.builder("parameter5", Parameter5::class.java).build(),
            ParameterSpec.builder("parameter6", Parameter6::class.java).addKdoc("text6").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "parameter1"(Parameter1::class.asTypeName()) { kdoc.append("text1") }
            "parameter2"(Parameter2::class.java) { kdoc.append("text2") }
            "parameter3"(Parameter3::class) { kdoc.append("text3") }
        }
        assertThat(list).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).addKdoc("text1").build(),
            ParameterSpec.builder("parameter2", Parameter2::class).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class).addKdoc("text3").build()
        )
    }
}
