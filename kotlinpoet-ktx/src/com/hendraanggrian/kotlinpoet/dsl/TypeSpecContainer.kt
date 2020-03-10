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

/** A [TypeSpecContainer] is responsible for managing a set of type instances. */
abstract class TypeSpecContainer {

    /** Add type to this container. */
    abstract fun add(spec: TypeSpec)

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec = buildClassType(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassType(type, builderAction).also { add(it) }

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec = buildClassType(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassType(type, builderAction).also { add(it) }

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: String): TypeSpec = buildExpectClassType(type).also { add(it) }

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassType(type, builderAction).also { add(it) }

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: ClassName): TypeSpec = buildExpectClassType(type).also { add(it) }

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassType(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addObject(type: String): TypeSpec = buildObjectType(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectType(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addObject(type: ClassName): TypeSpec = buildObjectType(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectType(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addCompanionObject(type: String? = null): TypeSpec = buildCompanionObjectType(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addCompanionObject(type: String? = null, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildCompanionObjectType(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec = buildInterfaceType(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceType(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec = buildInterfaceType(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceType(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec = buildEnumType(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumType(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec = buildEnumType(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumType(type, builderAction).also { add(it) }

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(): TypeSpec = buildAnonymousType().also { add(it) }

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousType(builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec = buildAnnotationType(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationType(type, builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec = buildAnnotationType(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationType(type, builderAction).also { add(it) }

    /** Convenient method to add type with operator function. */
    operator fun plusAssign(spec: TypeSpec) {
        add(spec)
    }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class TypeSpecContainerScope(private val container: TypeSpecContainer) : TypeSpecContainer() {

    override fun add(spec: TypeSpec) = container.add(spec)

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)
}
