package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.ClassName
import com.hendraanggrian.kotlinpoet.annotationTypeSpecOf
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.classTypeSpecOf
import com.hendraanggrian.kotlinpoet.emptyAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.interfaceTypeSpecOf
import kotlin.test.Test

class TypeSpecHandlerTest {
    private val list = TypeSpecHandler(mutableListOf())

    private inline fun container(configuration: TypeSpecHandlerScope.() -> Unit) =
        TypeSpecHandlerScope(list).configuration()

    @Test
    fun nativeSpec() {
        list += classTypeSpecOf("Class1")
        list += listOf(classTypeSpecOf("Class2"))
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf("Class2")
        )
    }

    @Test
    fun invocation() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.TypeSpecHandlerTest"
        container {
            `class`("Class1")
            `class`(ClassName(packageName, "MyType"))
        }
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf(ClassName(packageName, "MyType"))
        )
    }

    @Test
    fun others() {
        list.addClass("Class1")
        list.addInterface("Interface1")
        list.addEnum("Enum1") { addEnumConstant("A") }
        list.addAnonymous()
        list.addAnnotation("Annotation1")
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            interfaceTypeSpecOf("Interface1"),
            buildEnumTypeSpec("Enum1") { addEnumConstant("A") },
            emptyAnonymousTypeSpec(),
            annotationTypeSpecOf("Annotation1")
        )
    }
}