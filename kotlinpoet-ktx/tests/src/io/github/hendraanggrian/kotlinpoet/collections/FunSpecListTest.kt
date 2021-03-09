package io.github.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import io.github.hendraanggrian.kotlinpoet.emptyConstructorFunSpec
import io.github.hendraanggrian.kotlinpoet.emptyGetterFunSpec
import io.github.hendraanggrian.kotlinpoet.emptySetterFunSpec
import io.github.hendraanggrian.kotlinpoet.funSpecOf
import kotlin.test.Test

class FunSpecListTest {
    private val list = FunSpecList(mutableListOf())

    private inline fun container(configuration: FunSpecListScope.() -> Unit) =
        FunSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += funSpecOf("func")
        list += listOf(emptyConstructorFunSpec())
        assertThat(list).containsExactly(
            funSpecOf("func"),
            emptyConstructorFunSpec()
        )
    }

    @Test fun string() {
        list.add("func1")
        container { "func2" { } }
        assertThat(list).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2")
        )
    }

    @Test fun others() {
        list.addConstructor()
        list.addGetter()
        list.addSetter()
        assertThat(list).containsExactly(
            emptyConstructorFunSpec(),
            emptyGetterFunSpec(),
            emptySetterFunSpec()
        )
    }
}