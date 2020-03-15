package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.annotationTypeSpecOf
import com.hendraanggrian.kotlinpoet.anonymousTypeSpecOf
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
import com.hendraanggrian.kotlinpoet.enumTypeSpecOf
import com.hendraanggrian.kotlinpoet.expectClassTypeSpecOf
import com.hendraanggrian.kotlinpoet.interfaceTypeSpecOf
import com.hendraanggrian.kotlinpoet.objectTypeSpecOf
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

/** A [TypeSpecContainer] is responsible for managing a set of type instances. */
abstract class TypeSpecContainer {

    /** Add type to this container. */
    abstract fun add(spec: TypeSpec)

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec = classTypeSpecOf(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, builderAction).also { add(it) }

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec = classTypeSpecOf(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, builderAction).also { add(it) }

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: String): TypeSpec = expectClassTypeSpecOf(type).also { add(it) }

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassTypeSpec(type, builderAction).also { add(it) }

    /** Add expect class type from [type], returning the type added. */
    fun addExpectClass(type: ClassName): TypeSpec = expectClassTypeSpecOf(type).also { add(it) }

    /** Add expect class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addExpectClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassTypeSpec(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addObject(type: String): TypeSpec = objectTypeSpecOf(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectTypeSpec(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addObject(type: ClassName): TypeSpec = objectTypeSpecOf(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addObject(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectTypeSpec(type, builderAction).also { add(it) }

    /** Add object type from [type], returning the type added. */
    fun addCompanionObject(type: String? = null): TypeSpec = companionObjectTypeSpecOf(type).also { add(it) }

    /** Add object type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addCompanionObject(type: String? = null, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildCompanionObjectTypeSpec(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec = interfaceTypeSpecOf(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec = interfaceTypeSpecOf(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec = enumTypeSpecOf(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec = enumTypeSpecOf(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, builderAction).also { add(it) }

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(): TypeSpec = anonymousTypeSpecOf().also { add(it) }

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec = annotationTypeSpecOf(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec = annotationTypeSpecOf(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, builderAction).also { add(it) }

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
