package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeSpecListTest {
    private val list = TypeSpecList(mutableListOf())

    private class Class2
    private class ExpectClass2
    private object Object2
    private interface Interface2
    private enum class Enum2
    private annotation class Annotation2

    @Test
    fun add() {
        list.addClass("Class1")
        list.addClass("Class1") { kdoc.append("text2") }
        list.addClass(Class2::class.asTypeName())
        list.addClass(Class2::class.asTypeName()) { kdoc.append("text4") }
        list.addExpectClass("ExpectClass1")
        list.addExpectClass("ExpectClass1") { kdoc.append("text6") }
        list.addExpectClass(ExpectClass2::class.asTypeName())
        list.addExpectClass(ExpectClass2::class.asTypeName()) { kdoc.append("text8") }
        list.addObject("Object1")
        list.addObject("Object1") { kdoc.append("text10") }
        list.addObject(Object2::class.asTypeName())
        list.addObject(Object2::class.asTypeName()) { kdoc.append("text12") }
        list.addCompanionObject("CompanionObject1")
        list.addCompanionObject("CompanionObject1") { kdoc.append("text14") }
        list.addInterface("Interface1")
        list.addInterface("Interface1") { kdoc.append("text16") }
        list.addInterface(Interface2::class.asTypeName())
        list.addInterface(Interface2::class.asTypeName()) { kdoc.append("text18") }
        list.addEnum("Enum1") { enumConstants.put("A") }
        list.addEnum(Enum2::class.asTypeName()) { enumConstants.put("B") }
        list.addAnonymous()
        list.addAnonymous { kdoc.append("text22") }
        list.addAnnotation("Annotation1")
        list.addAnnotation("Annotation1") { kdoc.append("text24") }
        list.addAnnotation(Annotation2::class.asTypeName())
        list.addAnnotation(Annotation2::class.asTypeName()) { kdoc.append("text26") }
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class1").addKdoc("text2").build(),
            TypeSpec.classBuilder(Class2::class.asTypeName()).build(),
            TypeSpec.classBuilder(Class2::class.asTypeName()).addKdoc("text4").build(),
            TypeSpec.expectClassBuilder("ExpectClass1").build(),
            TypeSpec.expectClassBuilder("ExpectClass1").addKdoc("text6").build(),
            TypeSpec.expectClassBuilder(ExpectClass2::class.asTypeName()).build(),
            TypeSpec.expectClassBuilder(ExpectClass2::class.asTypeName()).addKdoc("text8").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder("Object1").addKdoc("text10").build(),
            TypeSpec.objectBuilder(Object2::class.asTypeName()).build(),
            TypeSpec.objectBuilder(Object2::class.asTypeName()).addKdoc("text12").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").addKdoc("text14").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface1").addKdoc("text16").build(),
            TypeSpec.interfaceBuilder(Interface2::class.asTypeName()).build(),
            TypeSpec.interfaceBuilder(Interface2::class.asTypeName()).addKdoc("text18").build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.enumBuilder(Enum2::class.asTypeName()).addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder().build(),
            TypeSpec.anonymousClassBuilder().addKdoc("text22").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation1").addKdoc("text24").build(),
            TypeSpec.annotationBuilder(Annotation2::class.asTypeName()).build(),
            TypeSpec.annotationBuilder(Annotation2::class.asTypeName()).addKdoc("text26").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE", "LocalVariableName")
    fun adding() {
        val Class1 by list.addingClass
        val Class2 by list.addingClass { kdoc.append("text2") }
        val ExpectClass1 by list.addingExpectClass
        val ExpectClass2 by list.addingExpectClass { kdoc.append("text4") }
        val Object1 by list.addingObject
        val Object2 by list.addingObject { kdoc.append("text6") }
        val CompanionObject1 by list.addingCompanionObject
        val CompanionObject2 by list.addingCompanionObject { kdoc.append("text8") }
        val Interface1 by list.addingInterface
        val Interface2 by list.addingInterface { kdoc.append("text10") }
        val Enum1 by list.addingEnum { enumConstants.put("A") }
        val Annotation1 by list.addingAnnotation
        val Annotation2 by list.addingAnnotation { kdoc.append("text13") }
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addKdoc("text2").build(),
            TypeSpec.expectClassBuilder("ExpectClass1").build(),
            TypeSpec.expectClassBuilder("ExpectClass2").addKdoc("text4").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder("Object2").addKdoc("text6").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.companionObjectBuilder("CompanionObject2").addKdoc("text8").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface2").addKdoc("text10").build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation2").addKdoc("text13").build()
        )
    }
}