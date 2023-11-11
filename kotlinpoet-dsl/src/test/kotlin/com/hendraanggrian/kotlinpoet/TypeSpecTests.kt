package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Parameter1
import com.example.Property1
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertFalse

@Suppress("ktlint:standard:property-naming")
class TypeSpecHandlerTest {
    private class Class2

    private class ExpectClass2

    private object Object2

    private interface Interface2

    private enum class Enum2

    private annotation class Annotation2

    @Test
    fun type() {
        assertThat(
            buildClassTypeSpec("test") {
                classType("Class1")
                classType("Class2") { kdoc("text2") }
                expectClassType("ExpectClass1")
                expectClassType("ExpectClass2") { kdoc("text2") }
                objectType("Object1")
                objectType("Object2") { kdoc("text2") }
                companionObjectType("CompanionObject1")
                interfaceType("Interface1")
                interfaceType("Interface2") { kdoc("text2") }
                enumType("Enum1")
                enumType("Enum2") { enumConstant("B") }
                anonymousType()
                anonymousType { kdoc("text2") }
                annotationType("Annotation1")
                annotationType("Annotation2") { kdoc("text2") }
            }.typeSpecs,
        ).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addKdoc("text2").build(),
            TypeSpec.expectClassBuilder("ExpectClass1").build(),
            TypeSpec.expectClassBuilder("ExpectClass2").addKdoc("text2").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder("Object2").addKdoc("text2").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface2").addKdoc("text2").build(),
            TypeSpec.enumBuilder("Enum1").build(),
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder().build(),
            TypeSpec.anonymousClassBuilder().addKdoc("text2").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation2").addKdoc("text2").build(),
        )
    }

    @Test
    fun typing() {
        assertThat(
            buildClassTypeSpec("test") {
                val Class1 by classTyping()
                val Class2 by classTyping { kdoc("text2") }
                val ExpectClass1 by expectClassTyping()
                val ExpectClass2 by expectClassTyping { kdoc("text2") }
                val Object1 by objectTyping()
                val Object2 by objectTyping { kdoc("text2") }
                val CompanionObject1 by companionObjectTyping()
                val Interface1 by interfaceTyping()
                val Interface2 by interfaceTyping { kdoc("text2") }
                val Enum1 by enumTyping { enumConstant("A") }
                val Annotation1 by annotationTyping()
                val Annotation2 by annotationTyping { kdoc("text2") }
            }.typeSpecs,
        ).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addKdoc("text2").build(),
            TypeSpec.expectClassBuilder("ExpectClass1").build(),
            TypeSpec.expectClassBuilder("ExpectClass2").addKdoc("text2").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder("Object2").addKdoc("text2").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface2").addKdoc("text2").build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation2").addKdoc("text2").build(),
        )
    }
}

class TypeSpecBuilderTest {
    @Test
    fun kdoc() {
        assertThat(
            buildClassTypeSpec("class1") {
                kdoc("kdoc1")
                kdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addKdoc("kdoc1")
                .addKdoc(codeBlockOf("kdoc2"))
                .build(),
        )
    }

    @Test
    fun annotations() {
        assertThat(
            buildClassTypeSpec("class1") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class.java)
                annotation(Annotation3::class)
                annotation<Annotation4>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .addAnnotation(Annotation3::class)
                .addAnnotation(Annotation4::class)
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertThat(
            buildClassTypeSpec("class1") { modifiers(PUBLIC, FINAL, CONST) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST)
                .build(),
        )
    }

    @Test
    fun typeVariables() {
        assertThat(
            buildClassTypeSpec("class1") {
                typeVariables(
                    listOf(
                        "typeVar1".genericsBy(Annotation1::class),
                        "typeVar2".genericsBy(Annotation2::class),
                    ),
                )
                typeVariable("typeVar3".genericsBy(Annotation3::class))
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName("typeVar1", Annotation1::class.java),
                        TypeVariableName("typeVar2", Annotation2::class.java),
                    ),
                )
                .addTypeVariable(TypeVariableName("typeVar3", Annotation3::class.java))
                .build(),
        )
    }

    @Test
    fun primaryConstructorFunction() {
        assertThat(
            buildClassTypeSpec("class1") {
                primaryConstructorFunction { parameter<Parameter1>("parameter1") }
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter("parameter1", Parameter1::class)
                        .build(),
                )
                .build(),
        )
    }

    @Test
    fun superclass() {
        assertThat(
            buildClassTypeSpec("class1") { superclass = Property1::class.asClassName() },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").superclass(Property1::class.asClassName()).build(),
        )
        assertThat(
            buildClassTypeSpec("class2") { superclass(Property1::class.java) },
        ).isEqualTo(
            TypeSpec.classBuilder("class2").superclass(Property1::class).build(),
        )
        assertThat(
            buildClassTypeSpec("class3") { superclass(Property1::class) },
        ).isEqualTo(
            TypeSpec.classBuilder("class3").superclass(Property1::class).build(),
        )
        assertThat(
            buildClassTypeSpec("class4") { superclass<Property1>() },
        ).isEqualTo(
            TypeSpec.classBuilder("class4").superclass(Property1::class).build(),
        )
    }

    @Test
    fun superclassConstructorParameter() {
        assertThat(
            buildClassTypeSpec("class1") { superclassConstructorParameter("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addSuperclassConstructorParameter("some code").build(),
        )
    }

    @Test
    fun superinterfaces() {
        assertThat(
            buildClassTypeSpec("class1") { superinterface<Property1>() },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addSuperinterface(Property1::class)
                .build(),
        )
    }

    @Test
    fun enumConstants() {
        assertThat(
            buildEnumTypeSpec("class1") { enumConstant("VALUE") },
        ).isEqualTo(
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build(),
        )
    }

    @Test
    fun property() {
        assertThat(
            buildClassTypeSpec("class1") { property<Property1>("property1") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addProperty("property1", Property1::class).build(),
        )
    }

    @Test
    fun initializerBlock() {
        assertThat(
            buildClassTypeSpec("class1") { initializerBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun functions() {
        assertThat(
            buildClassTypeSpec("class1") { function("function1") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addFunction(FunSpec.builder("function1").build())
                .build(),
        )
    }

    @Test
    fun types() {
        assertThat(
            buildClassTypeSpec("class1") { classType("class2") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addType(TypeSpec.classBuilder("class2").build())
                .build(),
        )
    }
}
