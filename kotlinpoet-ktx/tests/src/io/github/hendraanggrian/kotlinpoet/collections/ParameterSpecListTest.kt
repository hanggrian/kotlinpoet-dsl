package io.github.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import io.github.hendraanggrian.kotlinpoet.parameterSpecOf
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class ParameterSpecListTest {
    private val list = ParameterSpecList(mutableListOf())

    private inline fun container(configuration: ParameterSpecListScope.() -> Unit) =
        ParameterSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += parameterSpecOf<Parameter1>("parameter1")
        list += listOf(parameterSpecOf<Parameter2>("parameter2"))
        assertThat(list).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    @Test fun className() {
        val packageName = "io.github.hendraanggrian.kotlinpoet.collections.ParameterSpecListTest"
        list.add("parameter1", ClassName(packageName, "Parameter1"))
        list["parameter2"] = ClassName(packageName, "Parameter2")
        container { "parameter3"(ClassName(packageName, "Parameter3")) { } }
        assertThat(list).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun javaClass() {
        list.add("parameter1", Parameter1::class.java)
        list["parameter2"] = Parameter2::class.java
        container { "parameter3"(Parameter3::class.java) { } }
        assertThat(list).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun kotlinClass() {
        list.add("parameter1", Parameter1::class)
        list["parameter2"] = Parameter2::class
        container { "parameter3"(Parameter3::class) { } }
        assertThat(list).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun reifiedType() {
        list.add<Parameter1>("parameter1")
        container { "parameter2"<Parameter2> { } }
        assertThat(list).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    class Parameter1
    class Parameter2
    class Parameter3
}