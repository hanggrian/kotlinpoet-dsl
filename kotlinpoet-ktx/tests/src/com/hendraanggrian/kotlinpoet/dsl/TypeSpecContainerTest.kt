package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.buildAnnotationType
import com.hendraanggrian.kotlinpoet.buildAnonymousType
import com.hendraanggrian.kotlinpoet.buildClassType
import com.hendraanggrian.kotlinpoet.buildEnumType
import com.hendraanggrian.kotlinpoet.buildInterfaceType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import kotlin.test.Test

class TypeSpecContainerTest {
    private val specs = mutableListOf<TypeSpec>()
    private val container = object : TypeSpecContainer() {
        override fun add(spec: TypeSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: TypeSpecContainerScope.() -> Unit) =
        TypeSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildClassType("Class1"))
        container += buildClassType("Class2")
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildClassType("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.TypeSpecContainerTest"
        container {
            "Class1" { }
            (ClassName(packageName, "MyType")) { }
        }
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildClassType(ClassName(packageName, "MyType"))
        )
    }

    @Test fun others() {
        container.addClass("Class1")
        container.addInterface("Interface1")
        container.addEnum("Enum1") { addEnumConstant("A") }
        container.addAnonymous()
        container.addAnnotation("Annotation1")
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildInterfaceType("Interface1"),
            buildEnumType("Enum1") { addEnumConstant("A") },
            buildAnonymousType(),
            buildAnnotationType("Annotation1")
        )
    }
}