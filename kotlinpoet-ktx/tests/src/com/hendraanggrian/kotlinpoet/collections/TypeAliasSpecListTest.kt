package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.typeAliasSpecOf
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class TypeAliasSpecListTest {
    private val container = TypeAliasSpecList(mutableListOf())

    private inline fun container(configuration: TypeAliasSpecListScope.() -> Unit) =
        TypeAliasSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += typeAliasSpecOf<TypeAlias1>("typeAlias1")
        container += listOf(typeAliasSpecOf<TypeAlias2>("typeAlias2"))
        assertThat(container).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecListTest"
        container.add("typeAlias1", ClassName(packageName, "TypeAlias1"))
        container["typeAlias2"] = ClassName(packageName, "TypeAlias2")
        container { "typeAlias3"(ClassName(packageName, "TypeAlias3")) { } }
        assertThat(container).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun javaClass() {
        container.add("typeAlias1", TypeAlias1::class.java)
        container["typeAlias2"] = TypeAlias2::class.java
        container { "typeAlias3"(TypeAlias3::class.java) { } }
        assertThat(container).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun kotlinClass() {
        container.add("typeAlias1", TypeAlias1::class)
        container["typeAlias2"] = TypeAlias2::class
        container { "typeAlias3"(TypeAlias3::class) { } }
        assertThat(container).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2"),
            typeAliasSpecOf<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun reifiedType() {
        container.add<TypeAlias1>("typeAlias1")
        container { "typeAlias2"<TypeAlias2> { } }
        assertThat(container).containsExactly(
            typeAliasSpecOf<TypeAlias1>("typeAlias1"),
            typeAliasSpecOf<TypeAlias2>("typeAlias2")
        )
    }

    class TypeAlias1
    class TypeAlias2
    class TypeAlias3
}