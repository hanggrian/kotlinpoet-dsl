package com.hendraanggrian.kotlinpoet.collections

import com.example.TypeAlias1
import com.example.TypeAlias2
import com.example.TypeAlias3
import com.example.TypeAlias4
import com.example.TypeAlias5
import com.example.TypeAlias6
import com.example.TypeAlias7
import com.example.TypeAlias8
import com.google.common.truth.Truth.assertThat
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
        list.add("alias2", TypeAlias2::class.asTypeName()) { kdoc.append("text2") }
        list.add("alias3", TypeAlias3::class.java)
        list.add("alias4", TypeAlias4::class.java) { kdoc.append("text4") }
        list.add("alias5", TypeAlias5::class)
        list.add("alias6", TypeAlias6::class) { kdoc.append("text6") }
        list.add<TypeAlias7>("alias7")
        list.add<TypeAlias8>("alias8") { kdoc.append("text8") }
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).addKdoc("text2").build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).build(),
            TypeAliasSpec.builder("alias4", TypeAlias4::class).addKdoc("text4").build(),
            TypeAliasSpec.builder("alias5", TypeAlias5::class).build(),
            TypeAliasSpec.builder("alias6", TypeAlias6::class).addKdoc("text6").build(),
            TypeAliasSpec.builder("alias7", TypeAlias7::class).build(),
            TypeAliasSpec.builder("alias8", TypeAlias8::class).addKdoc("text8").build()
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
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val alias1 by list.adding(TypeAlias1::class.asTypeName())
        val alias2 by list.adding(TypeAlias2::class.asTypeName()) { kdoc.append("text2") }
        val alias3 by list.adding(TypeAlias3::class.java)
        val alias4 by list.adding(TypeAlias4::class.java) { kdoc.append("text4") }
        val alias5 by list.adding(TypeAlias5::class)
        val alias6 by list.adding(TypeAlias6::class) { kdoc.append("text6") }
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).addKdoc("text2").build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).build(),
            TypeAliasSpec.builder("alias4", TypeAlias4::class).addKdoc("text4").build(),
            TypeAliasSpec.builder("alias5", TypeAlias5::class).build(),
            TypeAliasSpec.builder("alias6", TypeAlias6::class).addKdoc("text6").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "alias1"(TypeAlias1::class.asTypeName()) { kdoc.append("text1") }
            "alias2"(TypeAlias2::class.asTypeName()) { kdoc.append("text2") }
            "alias3"(TypeAlias3::class.asTypeName()) { kdoc.append("text3") }
        }
        assertThat(list).containsExactly(
            TypeAliasSpec.builder("alias1", TypeAlias1::class).addKdoc("text1").build(),
            TypeAliasSpec.builder("alias2", TypeAlias2::class).addKdoc("text2").build(),
            TypeAliasSpec.builder("alias3", TypeAlias3::class).addKdoc("text3").build()
        )
    }
}
