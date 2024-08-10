package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Class2
import com.example.Class3
import com.example.Parameter1
import com.example.Property1
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("ktlint:standard:property-naming")
class TypeSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildClassTypeSpec("test") {
                types.addClass("Class1")
                types.addClass(Annotation1::class.name)
                types.addClass("Class2") { addKdoc("text2") }
                types.addClass(Annotation2::class.name) { addKdoc("text2") }
                types.addObject("Object1")
                types.addObject(Annotation1::class.name)
                types.addObject("Object2") { addKdoc("text2") }
                types.addObject(Annotation2::class.name) { addKdoc("text2") }
                types.addCompanionObject("CompanionObject1")
                types.addInterface("Interface1")
                types.addInterface(Annotation1::class.name)
                types.addInterface("Interface2") { addKdoc("text2") }
                types.addInterface(Annotation2::class.name) { addKdoc("text2") }
                types {
                    addEnum("Enum1")
                    addEnum(Annotation1::class.name)
                    addEnum("Enum2") { enumConstant("B") }
                    addEnum(Annotation2::class.name) { enumConstant("B") }
                    addAnonymous()
                    addAnonymous { addKdoc("text2") }
                    addAnnotation("Annotation1")
                    addAnnotation(Annotation1::class.name)
                    addAnnotation("Annotation2") { addKdoc("text2") }
                    addAnnotation(Annotation2::class.name) { addKdoc("text2") }
                }
            }.typeSpecs,
        ).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder(Annotation1::class.name).build(),
            TypeSpec.classBuilder("Class2").addKdoc("text2").build(),
            TypeSpec.classBuilder(Annotation2::class.name).addKdoc("text2").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder(Annotation1::class.name).build(),
            TypeSpec.objectBuilder("Object2").addKdoc("text2").build(),
            TypeSpec.objectBuilder(Annotation2::class.name).addKdoc("text2").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder(Annotation1::class.name).build(),
            TypeSpec.interfaceBuilder("Interface2").addKdoc("text2").build(),
            TypeSpec.interfaceBuilder(Annotation2::class.name).addKdoc("text2").build(),
            TypeSpec.enumBuilder("Enum1").build(),
            TypeSpec.enumBuilder(Annotation1::class.name).build(),
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            TypeSpec.enumBuilder(Annotation2::class.name).addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder().build(),
            TypeSpec.anonymousClassBuilder().addKdoc("text2").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder(Annotation1::class.name).build(),
            TypeSpec.annotationBuilder("Annotation2").addKdoc("text2").build(),
            TypeSpec.annotationBuilder(Annotation2::class.name).addKdoc("text2").build(),
        )
    }

    @Test
    fun adding() {
        assertThat(
            buildClassTypeSpec("test") {
                val Class1 by types.addingClass()
                val Class2 by types.addingClass { addKdoc("text2") }
                val Object1 by types.addingObject()
                val Object2 by types.addingObject { addKdoc("text2") }
                val CompanionObject1 by types.addingCompanionObject()
                val Interface1 by types.addingInterface()
                val Interface2 by types.addingInterface { addKdoc("text2") }
                val Enum1 by types.addingEnum()
                val Enum2 by types.addingEnum { enumConstant("A") }
                val Annotation1 by types.addingAnnotation()
                val Annotation2 by types.addingAnnotation { addKdoc("text2") }
            }.typeSpecs,
        ).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addKdoc("text2").build(),
            TypeSpec.objectBuilder("Object1").build(),
            TypeSpec.objectBuilder("Object2").addKdoc("text2").build(),
            TypeSpec.companionObjectBuilder("CompanionObject1").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface2").addKdoc("text2").build(),
            TypeSpec.enumBuilder("Enum1").build(),
            TypeSpec.enumBuilder("Enum2").addEnumConstant("A").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation2").addKdoc("text2").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                types { "HelloWorld" { addModifiers(FINAL) } }
            }.typeSpecs
                .first(),
        ).isEqualTo(
            TypeSpec.classBuilder("HelloWorld").addModifiers(FINAL).build(),
        )
    }
}

class TypeSpecBuilderTest {
    @Test
    fun initializerIndex() {
        assertThat(
            buildClassTypeSpec("class1") {
                initializerIndex = 10
                assertEquals(10, initializerIndex)
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .apply { initializerIndex = 10 }
                .build(),
        )
    }

    @Test
    fun addKdoc() {
        assertThat(
            buildClassTypeSpec("class1") {
                addKdoc("kdoc1")
                addKdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addKdoc("kdoc1")
                .addKdoc(codeBlockOf("kdoc2"))
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertThat(
            buildClassTypeSpec("class1") {
                addModifiers(PUBLIC)
                modifiers += listOf(FINAL, CONST)
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addModifiers(KModifier.PUBLIC)
                .addModifiers(listOf(KModifier.FINAL, KModifier.CONST))
                .build(),
        )
    }

    @Test
    fun addTypeVariables() {
        assertThat(
            buildClassTypeSpec("class1") {
                addTypeVariables(
                    "typeVar1".genericsBy(Annotation1::class),
                    "typeVar2".genericsBy(Annotation2::class),
                )
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName("typeVar1", Annotation1::class.java),
                        TypeVariableName("typeVar2", Annotation2::class.java),
                    ),
                ).build(),
        )
    }

    @Test
    fun primaryConstructorFunction() {
        assertThat(
            buildClassTypeSpec("class1") {
                setPrimaryConstructor { parameters.add<Parameter1>("parameter1") }
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .primaryConstructor(
                    FunSpec
                        .constructorBuilder()
                        .addParameter("parameter1", Parameter1::class)
                        .build(),
                ).build(),
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
            buildClassTypeSpec("class2") { setSuperclass(Property1::class.java) },
        ).isEqualTo(
            TypeSpec.classBuilder("class2").superclass(Property1::class).build(),
        )
        assertThat(
            buildClassTypeSpec("class3") { setSuperclass(Property1::class) },
        ).isEqualTo(
            TypeSpec.classBuilder("class3").superclass(Property1::class).build(),
        )
        assertThat(
            buildClassTypeSpec("class4") { setSuperclass<Property1>() },
        ).isEqualTo(
            TypeSpec.classBuilder("class4").superclass(Property1::class).build(),
        )
    }

    @Test
    fun addSetSuperclassConstructorParameter() {
        assertThat(
            buildClassTypeSpec("class1") { addSuperclassConstructorParameter("format", "arg") },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addSuperclassConstructorParameter("format", "arg")
                .build(),
        )
    }

    @Test
    fun superinterfaces() {
        assertThat(buildClassTypeSpec("class1") { addSuperinterface<Class1>() })
            .isEqualTo(
                TypeSpec
                    .classBuilder("class1")
                    .addSuperinterface(Class1::class)
                    .build(),
            )
        assertThat(buildClassTypeSpec("class2") { addSuperinterface(Class2::class.name) })
            .isEqualTo(
                TypeSpec
                    .classBuilder("class2")
                    .addSuperinterface(Class2::class)
                    .build(),
            )
        assertThat(buildClassTypeSpec("class3") { addSuperinterface(Class3::class) })
            .isEqualTo(
                TypeSpec
                    .classBuilder("class3")
                    .addSuperinterface(Class3::class)
                    .build(),
            )
    }

    @Test
    fun enumConstants() {
        assertThat(buildEnumTypeSpec("class1") { enumConstant("VALUE") })
            .isEqualTo(
                TypeSpec
                    .enumBuilder("class1")
                    .addEnumConstant("VALUE")
                    .build(),
            )
    }

    @Test
    fun addInitializerBlock() {
        assertThat(
            buildClassTypeSpec("class1") {
                addInitializerBlock(codeBlockOf("some code"))
                addInitializerBlock("format", "arg")
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addInitializerBlock(codeBlockOf("some code"))
                .addInitializerBlock(codeBlockOf("format", "arg"))
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildClassTypeSpec("class1") {
            assertTrue(tags.isEmpty())
            assertTrue(originatingElements.isEmpty())
            assertTrue(superinterfaces.isEmpty())
            assertTrue(superclassConstructorParameters.isEmpty())
        }
    }
}
