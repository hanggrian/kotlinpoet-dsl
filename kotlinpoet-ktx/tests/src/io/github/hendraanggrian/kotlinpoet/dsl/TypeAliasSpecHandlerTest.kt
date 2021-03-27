package io.github.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.ClassName
import io.github.hendraanggrian.kotlinpoet.typeAliasSpecOf
import kotlin.test.Test

class TypeAliasSpecHandlerTest {
    private val list = TypeAliasSpecHandler(mutableListOf())

    private inline fun container(configuration: TypeAliasSpecHandlerScope.() -> Unit) =
        TypeAliasSpecHandlerScope(list).configuration()

    @Test
    fun nativeSpec() {
        list += typeAliasSpecOf<TypeAlias1>("typeAlias1")
        list += listOf(typeAliasSpecOf<TypeAlias2>("typeAlias2"))
        assertThat(list).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2")
        )
    }

    @Test
    fun className() {
        val packageName = "io.github.hendraanggrian.kotlinpoet.dsl.TypeAliasSpecHandlerTest"
        list.add("typeAlias1", ClassName(packageName, "TypeAlias1"))
        list["typeAlias2"] = ClassName(packageName, "TypeAlias2")
        container { "typeAlias3"(ClassName(packageName, "TypeAlias3")) { } }
        assertThat(list).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test
    fun javaClass() {
        list.add("typeAlias1", TypeAlias1::class.java)
        list["typeAlias2"] = TypeAlias2::class.java
        container { "typeAlias3"(TypeAlias3::class.java) { } }
        assertThat(list).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test
    fun kotlinClass() {
        list.add("typeAlias1", TypeAlias1::class)
        list["typeAlias2"] = TypeAlias2::class
        container { "typeAlias3"(TypeAlias3::class) { } }
        assertThat(list).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test
    fun reifiedType() {
        list.add<TypeAlias1>("typeAlias1")
        container { "typeAlias2"<TypeAlias2> { } }
        assertThat(list).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2")
        )
    }

    class TypeAlias1
    class TypeAlias2
    class TypeAlias3
}