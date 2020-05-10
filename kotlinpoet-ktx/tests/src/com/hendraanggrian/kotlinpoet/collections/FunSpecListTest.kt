package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.constructorFunSpecOf
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.hendraanggrian.kotlinpoet.getterFunSpecOf
import com.hendraanggrian.kotlinpoet.setterFunSpecOf
import kotlin.test.Test

class FunSpecListTest {
    private val list = FunSpecList(mutableListOf())

    private inline fun container(configuration: FunSpecListScope.() -> Unit) =
        FunSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += funSpecOf("func")
        list += listOf(constructorFunSpecOf())
        assertThat(list).containsExactly(
            funSpecOf("func"),
            constructorFunSpecOf()
        )
    }

    @Test fun string() {
        list.add("func1")
        list += "func2"
        container { "func3" { } }
        assertThat(list).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2"),
            funSpecOf("func3")
        )
    }

    @Test fun others() {
        list.addConstructor()
        list.addGetter()
        list.addSetter()
        assertThat(list).containsExactly(
            constructorFunSpecOf(),
            getterFunSpecOf(),
            setterFunSpecOf()
        )
    }
}