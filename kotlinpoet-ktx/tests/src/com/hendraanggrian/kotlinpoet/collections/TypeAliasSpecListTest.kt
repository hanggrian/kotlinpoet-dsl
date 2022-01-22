package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.internal.TypeAlias1
import com.hendraanggrian.kotlinpoet.internal.TypeAlias2
import com.hendraanggrian.kotlinpoet.internal.TypeAlias3
import com.hendraanggrian.kotlinpoet.internal.TypeAlias4
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeAliasSpecListTest {

    private val list = TypeAliasSpecList(mutableListOf())
    private fun list(configuration: TypeAliasSpecListScope.() -> Unit) =
        TypeAliasSpecListScope(list).configuration()

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