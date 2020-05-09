package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.parameterSpecOf
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterSpec
import kotlin.test.Test

class ParameterSpecContainerTest {
    private val parameters = mutableListOf<ParameterSpec>()
    private val container = object : ParameterSpecContainer() {
        override fun addAll(specs: Iterable<ParameterSpec>): Boolean = parameters.addAll(specs)
        override fun add(spec: ParameterSpec) {
            parameters += spec
        }
    }

    private inline fun container(configuration: ParameterSpecContainerScope.() -> Unit) =
        ParameterSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container += parameterSpecOf<Parameter1>("parameter1")
        container += listOf(parameterSpecOf<Parameter2>("parameter2"))
        assertThat(parameters).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.ParameterSpecContainerTest"
        container.add("parameter1", ClassName(packageName, "Parameter1"))
        container["parameter2"] = ClassName(packageName, "Parameter2")
        container { "parameter3"(ClassName(packageName, "Parameter3")) { } }
        assertThat(parameters).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun javaClass() {
        container.add("parameter1", Parameter1::class.java)
        container["parameter2"] = Parameter2::class.java
        container { "parameter3"(Parameter3::class.java) { } }
        assertThat(parameters).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun kotlinClass() {
        container.add("parameter1", Parameter1::class)
        container["parameter2"] = Parameter2::class
        container { "parameter3"(Parameter3::class) { } }
        assertThat(parameters).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun reifiedType() {
        container.add<Parameter1>("parameter1")
        container { "parameter2"<Parameter2> { } }
        assertThat(parameters).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    class Parameter1
    class Parameter2
    class Parameter3
}