package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.constructorFunSpecOf
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.hendraanggrian.kotlinpoet.getterFunSpecOf
import com.hendraanggrian.kotlinpoet.setterFunSpecOf
import kotlin.test.Test

class FunSpecListTest {
    private val container = FunSpecList(mutableListOf())

    private inline fun container(configuration: FunSpecListScope.() -> Unit) =
        FunSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += funSpecOf("func")
        container += listOf(constructorFunSpecOf())
        assertThat(container).containsExactly(
            funSpecOf("func"),
            constructorFunSpecOf()
        )
    }

    @Test fun string() {
        container.add("func1")
        container += "func2"
        container { "func3" { } }
        assertThat(container).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2"),
            funSpecOf("func3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        container.addGetter()
        container.addSetter()
        assertThat(container).containsExactly(
            constructorFunSpecOf(),
            getterFunSpecOf(),
            setterFunSpecOf()
        )
    }
}