package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.annotationTypeSpecOf
import com.hendraanggrian.kotlinpoet.anonymousTypeSpecOf
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.classTypeSpecOf
import com.hendraanggrian.kotlinpoet.interfaceTypeSpecOf
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
        container.add(classTypeSpecOf("Class1"))
        container += classTypeSpecOf("Class2")
        assertThat(specs).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.TypeSpecContainerTest"
        container {
            "Class1" { }
            (ClassName(packageName, "MyType")) { }
        }
        assertThat(specs).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf(ClassName(packageName, "MyType"))
        )
    }

    @Test fun others() {
        container.addClass("Class1")
        container.addInterface("Interface1")
        container.addEnum("Enum1") { addEnumConstant("A") }
        container.addAnonymous()
        container.addAnnotation("Annotation1")
        assertThat(specs).containsExactly(
            classTypeSpecOf("Class1"),
            interfaceTypeSpecOf("Interface1"),
            buildEnumTypeSpec("Enum1") { addEnumConstant("A") },
            anonymousTypeSpecOf(),
            annotationTypeSpecOf("Annotation1")
        )
    }
}