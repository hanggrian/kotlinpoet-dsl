package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.annotationTypeSpecOf
import com.hendraanggrian.kotlinpoet.anonymousTypeSpecOf
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.classTypeSpecOf
import com.hendraanggrian.kotlinpoet.interfaceTypeSpecOf
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class TypeSpecListTest {
    private val container = TypeSpecList(mutableListOf())

    private inline fun container(configuration: TypeSpecListScope.() -> Unit) =
        TypeSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += classTypeSpecOf("Class1")
        container += listOf(classTypeSpecOf("Class2"))
        assertThat(container).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.kotlinpoet.collections.TypeSpecListTest"
        container {
            "Class1" { }
            (ClassName(packageName, "MyType")) { }
        }
        assertThat(container).containsExactly(
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
        assertThat(container).containsExactly(
            classTypeSpecOf("Class1"),
            interfaceTypeSpecOf("Interface1"),
            buildEnumTypeSpec("Enum1") { addEnumConstant("A") },
            anonymousTypeSpecOf(),
            annotationTypeSpecOf("Annotation1")
        )
    }
}