package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.buildConstructorFunction
import com.hendraanggrian.kotlinpoet.buildFunction
import com.hendraanggrian.kotlinpoet.buildGetterFunction
import com.hendraanggrian.kotlinpoet.buildSetterFunction
import com.squareup.kotlinpoet.FunSpec
import kotlin.test.Test

class FunSpecContainerTest {
    private val specs = mutableListOf<FunSpec>()
    private val container = object : FunSpecContainer() {
        override fun add(spec: FunSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: FunSpecContainerScope.() -> Unit) =
        FunSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildFunction("func"))
        container += buildConstructorFunction()
        assertThat(specs).containsExactly(
            buildFunction("func"),
            buildConstructorFunction()
        )
    }

    @Test fun string() {
        container.add("func1")
        container += "func2"
        container { "func3" { } }
        assertThat(specs).containsExactly(
            buildFunction("func1"),
            buildFunction("func2"),
            buildFunction("func3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        container.addGetter()
        container.addSetterFunction()
        assertThat(specs).containsExactly(
            buildConstructorFunction(),
            buildGetterFunction(),
            buildSetterFunction()
        )
    }
}