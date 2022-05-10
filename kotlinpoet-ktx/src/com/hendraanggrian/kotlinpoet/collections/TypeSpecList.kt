package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.buildAnnotationTypeSpec
import com.hendraanggrian.kotlinpoet.buildAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.buildClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildCompanionObjectTypeSpec
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.buildExpectClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildInterfaceTypeSpec
import com.hendraanggrian.kotlinpoet.buildObjectTypeSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

/** A [TypeSpecList] is responsible for managing a set of type instances. */
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, configuration).also(::add)

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, configuration).also(::add)

    /** Add expect class type from name. */
    fun addExpectClass(type: String): TypeSpec = TypeSpec.expectClassBuilder(type).build().also(::add)

    /** Add expect class type from name with custom initialization [configuration]. */
    fun addExpectClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassTypeSpec(type, configuration).also(::add)

    /** Add expect class type from [ClassName]. */
    fun addExpectClass(type: ClassName): TypeSpec = TypeSpec.expectClassBuilder(type).build().also(::add)

    /** Add expect class type from [ClassName] with custom initialization [configuration]. */
    fun addExpectClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildExpectClassTypeSpec(type, configuration).also(::add)

    /** Add object type from name. */
    fun addObject(type: String): TypeSpec = TypeSpec.objectBuilder(type).build().also(::add)

    /** Add object type from name with custom initialization [configuration]. */
    fun addObject(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectTypeSpec(type, configuration).also(::add)

    /** Add object type from [ClassName]. */
    fun addObject(type: ClassName): TypeSpec = TypeSpec.objectBuilder(type).build().also(::add)

    /** Add object type from [ClassName] with custom initialization [configuration]. */
    fun addObject(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildObjectTypeSpec(type, configuration).also(::add)

    /** Add object type from name. */
    fun addCompanionObject(type: String? = null): TypeSpec = TypeSpec.companionObjectBuilder(type).build().also(::add)

    /** Add object type from name with custom initialization [configuration]. */
    fun addCompanionObject(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildCompanionObjectTypeSpec(type, configuration).also(::add)

    /** Add interface type from name. */
    fun addInterface(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, configuration).also(::add)

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, configuration).also(::add)

    /** Add enum type from name. */
    fun addEnum(type: String): TypeSpec = TypeSpec.enumBuilder(type).build().also(::add)

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, configuration).also(::add)

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): TypeSpec = TypeSpec.enumBuilder(type).build().also(::add)

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, configuration).also(::add)

    /** Add anonymous type from block. */
    fun addAnonymous(): TypeSpec = TypeSpec.anonymousClassBuilder().build().also(::add)

    /** Add anonymous type from block with custom initialization [configuration]. */
    fun addAnonymous(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(configuration).also(::add)

    /** Add annotation type from name. */
    fun addAnnotation(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, configuration).also(::add)

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, configuration).also(::add)
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecMarker
class TypeSpecListScope internal constructor(actualList: MutableList<TypeSpec>) : TypeSpecList(actualList)
