package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.constructorFunSpecOf
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.hendraanggrian.kotlinpoet.getterFunSpecOf
import com.hendraanggrian.kotlinpoet.setterFunSpecOf
import com.squareup.kotlinpoet.FunSpec
import kotlin.test.Test

class FunSpecContainerTest {
    private val funs = mutableListOf<FunSpec>()
    private val container = object : FunSpecContainer() {
        override fun addAll(specs: Iterable<FunSpec>): Boolean = funs.addAll(specs)
        override fun add(spec: FunSpec) {
            funs += spec
        }
    }

    private inline fun container(configuration: FunSpecContainerScope.() -> Unit) =
        FunSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container += funSpecOf("func")
        container += listOf(constructorFunSpecOf())
        assertThat(funs).containsExactly(
            funSpecOf("func"),
            constructorFunSpecOf()
        )
    }

    @Test fun string() {
        container.add("func1")
        container += "func2"
        container { "func3" { } }
        assertThat(funs).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2"),
            funSpecOf("func3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        container.addGetter()
        container.addSetter()
        assertThat(funs).containsExactly(
            constructorFunSpecOf(),
            getterFunSpecOf(),
            setterFunSpecOf()
        )
    }
}