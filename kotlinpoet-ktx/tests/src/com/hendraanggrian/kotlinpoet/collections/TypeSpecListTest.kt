package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeSpecListTest {

    private val list = TypeSpecList(mutableListOf())

    private class Class2
    private class Expect2
    private object Object2
    private interface Interface2
    private enum class Enum2
    private annotation class Annotation2

    @Test
    fun add() {
        list.addClass("Class1")
        list.addClass(Class2::class.asTypeName())
        list.addExpectClass("Expect1")
        list.addExpectClass(Expect2::class.asTypeName())
        list.addObject("Object1")
        list.addObject(Object2::class.asTypeName())
        list.addCompanionObject()
        list.addInterface("Interface1")
        list.addInterface(Interface2::class.asTypeName())
        list.addEnum("Enum1") { enumConstants.put("A") }
        list.addEnum(Enum2::class.asTypeName()) { enumConstants.put("B") }
        list.addAnonymous()
        list.addAnnotation("Annotation1")
        list.addAnnotation(Annotation2::class.asTypeName())
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder(Class2::class.asTypeName()).build(),
            TypeSpec.expectClassBuilder("Expect1").build(),
            TypeSpec.expectClassBuilder(Expect2::class.asTypeName()).build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder(Object2::class.asTypeName()).build(),
            TypeSpec.companionObjectBuilder().build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder(Interface2::class.asTypeName()).build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.enumBuilder(Enum2::class.asTypeName()).addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder().build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder(Annotation2::class.asTypeName()).build()
        )
    }
}