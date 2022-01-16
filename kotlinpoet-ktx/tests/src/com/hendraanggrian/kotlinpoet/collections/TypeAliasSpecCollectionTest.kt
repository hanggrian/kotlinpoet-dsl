package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeAliasSpecCollectionTest {

    private val list = TypeAliasSpecCollection(mutableListOf())
    private fun list(configuration: TypeAliasSpecCollectionScope.() -> Unit) =
        TypeAliasSpecCollectionScope(list).configuration()

    private class TypeAlias1
    private class TypeAlias2
    private class TypeAlias3
    private class TypeAlias4

    @Test
    fun add() {
        list.add("alias1", TypeAlias1::class.asTypeName())
        list.add("alias2", TypeAlias2::class.java)
        list.add("alias3", TypeAlias3::class)
        list.add<TypeAlias4>("alias4")
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).build(),
            TypeAliasSpec.builder("alias4", TypeAlias4::class).build()
        )
    }

    @Test
    fun set() {
        list["alias1"] = TypeAlias1::class.asTypeName()
        list["alias2"] = TypeAlias2::class.java
        list["alias3"] = TypeAlias3::class
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).build()
        )
    }

    @Test
    fun invoke() {
        list {
            "alias1"(TypeAlias1::class.asTypeName()) { }
            "alias2"(TypeAlias2::class.asTypeName()) { }
            "alias3"(TypeAlias3::class.asTypeName()) { }
            "alias4"<TypeAlias4> { }
        }
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).build(),
            TypeAliasSpec.builder("alias4", TypeAlias4::class).build()
        )
    }
}