package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.buildAnnotationType
import com.hendraanggrian.kotlinpoet.buildAnonymousType
import com.hendraanggrian.kotlinpoet.buildClassType
import com.hendraanggrian.kotlinpoet.buildCompanionObjectType
import com.hendraanggrian.kotlinpoet.buildEnumType
import com.hendraanggrian.kotlinpoet.buildExpectClassType
import com.hendraanggrian.kotlinpoet.buildInterfaceType
import com.hendraanggrian.kotlinpoet.buildObjectType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

private interface TypeAddable {

    /** Add type to this container, returning the type added. */
    fun add(spec: TypeSpec): TypeSpec
}

abstract class TypeCollection internal constructor() : TypeAddable {

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec =
        add(buildClassType(type))

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(buildClassType(type))

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: String): TypeSpec =
        add(buildExpectClassType(type))

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildExpectClassType(type, builderAction))

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: ClassName): TypeSpec =
        add(buildExpectClassType(type))

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildExpectClassType(type, builderAction))

    /** Add object type from [type], returning the type added. */
    fun addObject(type: String): TypeSpec =
        add(buildObjectType(type))

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildObjectType(type, builderAction))

    /** Add object type from [type], returning the type added. */
    fun addObject(type: ClassName): TypeSpec =
        add(buildObjectType(type))

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildObjectType(type, builderAction))

    /** Add object type from [type], returning the type added. */
    fun addCompanionObject(type: String? = null): TypeSpec =
        add(buildCompanionObjectType(type))

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addCompanionObject(type: String? = null, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildCompanionObjectType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(buildInterfaceType(type))

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(buildInterfaceType(type))

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(buildEnumType(type))

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(buildEnumType(type))

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(): TypeSpec =
        add(buildAnonymousType())

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousType(builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(buildAnnotationType(type))

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(buildAnnotationType(type))

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))
}

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() : TypeCollection() {

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class TypeContainerScope @PublishedApi internal constructor(container: TypeContainer) :
    TypeContainer(), TypeAddable by container
