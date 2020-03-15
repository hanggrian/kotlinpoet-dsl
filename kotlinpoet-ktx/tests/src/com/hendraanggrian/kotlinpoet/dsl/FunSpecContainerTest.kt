package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.constructorFunSpecOf
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.hendraanggrian.kotlinpoet.getterFunSpecOf
import com.hendraanggrian.kotlinpoet.setterFunSpecOf
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
        container.add(funSpecOf("func"))
        container += constructorFunSpecOf()
        assertThat(specs).containsExactly(
            funSpecOf("func"),
            constructorFunSpecOf()
        )
    }

    @Test fun string() {
        container.add("func1")
        container += "func2"
        container { "func3" { } }
        assertThat(specs).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2"),
            funSpecOf("func3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        container.addGetter()
        container.addSetter()
        assertThat(specs).containsExactly(
            constructorFunSpecOf(),
            getterFunSpecOf(),
            setterFunSpecOf()
        )
    }
}