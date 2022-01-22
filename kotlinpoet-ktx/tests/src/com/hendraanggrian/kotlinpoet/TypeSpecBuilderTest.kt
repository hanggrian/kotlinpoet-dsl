package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.internal.Annotation1
import com.hendraanggrian.kotlinpoet.internal.Parameter1
import com.hendraanggrian.kotlinpoet.internal.Property1
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeSpecBuilderTest {

    @Test
    fun kdoc() {
        assertEquals(
            buildClassTypeSpec("class1") { kdoc.append("some doc") },
            TypeSpec.classBuilder("class1").addKdoc("some doc").build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildClassTypeSpec("class1") { annotations { add<Annotation1>() } },
            TypeSpec.classBuilder("class1").addAnnotation(Annotation1::class).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildClassTypeSpec("class1") { addModifiers(PUBLIC, FINAL, CONST) },
            TypeSpec.classBuilder("class1").addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST).build()
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildClassTypeSpec("class1") { typeVariables.add("typeVar1", Annotation1::class) },
            TypeSpec.classBuilder("class1").addTypeVariable(TypeVariableName("typeVar1", Annotation1::class)).build()
        )
    }

    @Test
    fun primaryConstructor() {
        assertEquals(
            buildClassTypeSpec("class1") { primaryConstructor { parameters.add<Parameter1>("parameter1") } },
            TypeSpec.classBuilder("class1")
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter("parameter1", Parameter1::class)
                        .build()
                )
                .build()
        )
    }

    @Test
    fun superclass() {
        assertEquals(
            buildClassTypeSpec("class1") { superclass = Property1::class.asClassName() },
            TypeSpec.classBuilder("class1").superclass(Property1::class.asClassName()).build()
        )
        assertEquals(
            buildClassTypeSpec("class2") { superclass(Property1::class.java) },
            TypeSpec.classBuilder("class2").superclass(Property1::class).build()
        )
        assertEquals(
            buildClassTypeSpec("class3") { superclass(Property1::class) },
            TypeSpec.classBuilder("class3").superclass(Property1::class).build()
        )
        assertEquals(
            buildClassTypeSpec("class4") { superclass<Property1>() },
            TypeSpec.classBuilder("class4").superclass(Property1::class).build()
        )
    }

    @Test
    fun addSuperclassConstructorParameter() {
        assertEquals(
            buildClassTypeSpec("class1") { addSuperclassConstructorParameter("some code") },
            TypeSpec.classBuilder("class1").addSuperclassConstructorParameter("some code").build()
        )
    }

    @Test
    fun superinterfaces() {
        assertEquals(
            buildClassTypeSpec("class1") { superinterfaces.put<Property1>() },
            TypeSpec.classBuilder("class1")
                .addSuperinterface(Property1::class)
                .build()
        )
    }

    @Test
    fun enumConstants() {
        assertEquals(
            buildEnumTypeSpec("class1") { enumConstants.put("VALUE") },
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build()
        )
    }

    @Test
    fun properties() {
        assertEquals(
            buildClassTypeSpec("class1") { properties.add<Property1>("property1") },
            TypeSpec.classBuilder("class1").addProperty("property1", Property1::class).build()
        )
    }

    @Test
    fun addInitializerBlock() {
        assertEquals(
            buildClassTypeSpec("class1") { addInitializerBlock(codeBlockOf("some code")) },
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build()
        )
    }

    @Test
    fun functions() {
        assertEquals(
            buildClassTypeSpec("class1") { functions.add("function1") },
            TypeSpec.classBuilder("class1")
                .addFunction(FunSpec.builder("function1").build())
                .build()
        )
    }

    @Test
    fun types() {
        assertEquals(
            buildClassTypeSpec("class1") { types.addClass("class2") },
            TypeSpec.classBuilder("class1")
                .addType(TypeSpec.classBuilder("class2").build())
                .build()
        )
    }
}