package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.annotationTypeSpecOf
import com.hendraanggrian.kotlinpoet.buildAnnotationTypeSpec
import com.hendraanggrian.kotlinpoet.buildAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.buildClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildCompanionObjectTypeSpec
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.buildExpectClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildInterfaceTypeSpec
import com.hendraanggrian.kotlinpoet.buildObjectTypeSpec
import com.hendraanggrian.kotlinpoet.classTypeSpecOf
import com.hendraanggrian.kotlinpoet.companionObjectTypeSpecOf
import com.hendraanggrian.kotlinpoet.emptyAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.enumTypeSpecOf
import com.hendraanggrian.kotlinpoet.expectClassTypeSpecOf
import com.hendraanggrian.kotlinpoet.interfaceTypeSpecOf
import com.hendraanggrian.kotlinpoet.objectTypeSpecOf
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

/** A [TypeSpecList] is responsible for managing a set of type instances. */
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) :
    MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(classTypeSpecOf(type))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(classTypeSpecOf(type))

    /** Add expect class type from name. */
    fun addExpectClass(type: String): Boolean = add(expectClassTypeSpecOf(type))

    /** Add expect class type from [ClassName]. */
    fun addExpectClass(type: ClassName): Boolean = add(expectClassTypeSpecOf(type))

    /** Add object type from name. */
    fun addObject(type: String): Boolean = add(objectTypeSpecOf(type))

    /** Add object type from [ClassName]. */
    fun addObject(type: ClassName): Boolean = add(objectTypeSpecOf(type))

    /** Add object type from name. */
    fun addCompanionObject(type: String? = null): Boolean = add(companionObjectTypeSpecOf(type))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(interfaceTypeSpecOf(type))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(enumTypeSpecOf(type))

    /** Add anonymous type from block. */
    fun addAnonymous(): Boolean = add(emptyAnonymousTypeSpec())

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** Add class type from name with custom initialization [builderAction]. */
    inline fun addClass(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, builderAction))

    /** Add class type from [ClassName] with custom initialization [builderAction]. */
    inline fun addClass(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, builderAction))

    /** Add expect class type from name with custom initialization [builderAction]. */
    inline fun addExpectClass(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildExpectClassTypeSpec(type, builderAction))

    /** Add expect class type from [ClassName] with custom initialization [builderAction]. */
    inline fun addExpectClass(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildExpectClassTypeSpec(type, builderAction))

    /** Add object type from name with custom initialization [builderAction]. */
    inline fun addObject(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildObjectTypeSpec(type, builderAction))

    /** Add object type from [ClassName] with custom initialization [builderAction]. */
    inline fun addObject(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildObjectTypeSpec(type, builderAction))

    /** Add object type from name with custom initialization [builderAction]. */
    inline fun addCompanionObject(
        type: String? = null,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildCompanionObjectTypeSpec(type, builderAction))

    /** Add interface type from name with custom initialization [builderAction]. */
    inline fun addInterface(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, builderAction))

    /** Add interface type from [ClassName] with custom initialization [builderAction]. */
    inline fun addInterface(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, builderAction))

    /** Add enum type from name with custom initialization [builderAction]. */
    inline fun addEnum(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, builderAction))

    /** Add enum type from [ClassName] with custom initialization [builderAction]. */
    inline fun addEnum(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, builderAction))

    /** Add anonymous type from block with custom initialization [builderAction]. */
    inline fun addAnonymous(
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnonymousTypeSpec(builderAction))

    /** Add annotation type from name with custom initialization [builderAction]. */
    inline fun addAnnotation(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, builderAction))

    /** Add annotation type from [ClassName] with custom initialization [builderAction]. */
    inline fun addAnnotation(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, builderAction))
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecDslMarker
open class TypeSpecListScope(actualList: MutableList<TypeSpec>) : TypeSpecList(actualList) {

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, builderAction)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, builderAction)
}
