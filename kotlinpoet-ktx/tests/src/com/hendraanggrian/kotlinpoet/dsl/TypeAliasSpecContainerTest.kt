package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.buildTypeAlias
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeAliasSpec
import kotlin.test.Test

class TypeAliasSpecContainerTest {
    private val specs = mutableListOf<TypeAliasSpec>()
    private val container = object : TypeAliasSpecContainer() {
        override fun add(spec: TypeAliasSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: TypeAliasSpecContainerScope.() -> Unit) =
        TypeAliasSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildTypeAlias<TypeAlias1>("typeAlias1"))
        container += buildTypeAlias<TypeAlias2>("typeAlias2")
        assertThat(specs).containsExactly(
            buildTypeAlias<TypeAlias1>("typeAlias1"),
            buildTypeAlias<TypeAlias2>("typeAlias2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.TypeAliasSpecContainerTest"
        container.add("typeAlias1", ClassName(packageName, "TypeAlias1"))
        container["typeAlias2"] = ClassName(packageName, "TypeAlias2")
        container { "typeAlias3"(ClassName(packageName, "TypeAlias3")) { } }
        assertThat(specs).containsExactly(
            buildTypeAlias<TypeAlias1>("typeAlias1"),
            buildTypeAlias<TypeAlias2>("typeAlias2"),
            buildTypeAlias<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun javaClass() {
        container.add("typeAlias1", TypeAlias1::class.java)
        container["typeAlias2"] = TypeAlias2::class.java
        container { "typeAlias3"(TypeAlias3::class.java) { } }
        assertThat(specs).containsExactly(
            buildTypeAlias<TypeAlias1>("typeAlias1"),
            buildTypeAlias<TypeAlias2>("typeAlias2"),
            buildTypeAlias<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun kotlinClass() {
        container.add("typeAlias1", TypeAlias1::class)
        container["typeAlias2"] = TypeAlias2::class
        container { "typeAlias3"(TypeAlias3::class) { } }
        assertThat(specs).containsExactly(
            buildTypeAlias<TypeAlias1>("typeAlias1"),
            buildTypeAlias<TypeAlias2>("typeAlias2"),
            buildTypeAlias<TypeAlias3>("typeAlias3")
        )
    }

    @Test fun reifiedType() {
        container.add<TypeAlias1>("typeAlias1")
        container { "typeAlias2"<TypeAlias2> { } }
        assertThat(specs).containsExactly(
            buildTypeAlias<TypeAlias1>("typeAlias1"),
            buildTypeAlias<TypeAlias2>("typeAlias2")
        )
    }

    class TypeAlias1
    class TypeAlias2
    class TypeAlias3
}