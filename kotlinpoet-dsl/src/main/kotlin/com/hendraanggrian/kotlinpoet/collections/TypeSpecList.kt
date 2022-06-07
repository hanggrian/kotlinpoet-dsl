package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.buildAnnotationTypeSpec
import com.hendraanggrian.kotlinpoet.buildAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.buildClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildCompanionObjectTypeSpec
import com.hendraanggrian.kotlinpoet.buildEnumTypeSpec
import com.hendraanggrian.kotlinpoet.buildExpectClassTypeSpec
import com.hendraanggrian.kotlinpoet.buildInterfaceTypeSpec
import com.hendraanggrian.kotlinpoet.buildObjectTypeSpec
import com.hendraanggrian.kotlinpoet.createSpecLoader
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** A [TypeSpecList] is responsible for managing a set of type instances. */
@OptIn(ExperimentalContracts::class)
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildClassTypeSpec(type, configuration).also(::add)
    }

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildClassTypeSpec(type, configuration).also(::add)
    }

    /** Add expect class type from name. */
    fun addExpectClass(type: String): TypeSpec = TypeSpec.expectClassBuilder(type).build().also(::add)

    /** Add expect class type from name with custom initialization [configuration]. */
    fun addExpectClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildExpectClassTypeSpec(type, configuration).also(::add)
    }

    /** Add expect class type from [ClassName]. */
    fun addExpectClass(type: ClassName): TypeSpec = TypeSpec.expectClassBuilder(type).build().also(::add)

    /** Add expect class type from [ClassName] with custom initialization [configuration]. */
    fun addExpectClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildExpectClassTypeSpec(type, configuration).also(::add)
    }

    /** Add object type from name. */
    fun addObject(type: String): TypeSpec = TypeSpec.objectBuilder(type).build().also(::add)

    /** Add object type from name with custom initialization [configuration]. */
    fun addObject(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildObjectTypeSpec(type, configuration).also(::add)
    }

    /** Add object type from [ClassName]. */
    fun addObject(type: ClassName): TypeSpec = TypeSpec.objectBuilder(type).build().also(::add)

    /** Add object type from [ClassName] with custom initialization [configuration]. */
    fun addObject(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildObjectTypeSpec(type, configuration).also(::add)
    }

    /** Add object type from name. */
    fun addCompanionObject(type: String? = null): TypeSpec = TypeSpec.companionObjectBuilder(type).build().also(::add)

    /** Add object type from name with custom initialization [configuration]. */
    fun addCompanionObject(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildCompanionObjectTypeSpec(type, configuration).also(::add)
    }

    /** Add interface type from name. */
    fun addInterface(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildInterfaceTypeSpec(type, configuration).also(::add)
    }

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildInterfaceTypeSpec(type, configuration).also(::add)
    }

    /**
     * Add enum type from name with custom initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildEnumTypeSpec(type, configuration).also(::add)
    }

    /**
     * Add enum type from [ClassName] with custom initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildEnumTypeSpec(type, configuration).also(::add)
    }

    /** Add anonymous type from block. */
    fun addAnonymous(): TypeSpec = TypeSpec.anonymousClassBuilder().build().also(::add)

    /** Add anonymous type from block with custom initialization [configuration]. */
    fun addAnonymous(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnonymousTypeSpec(configuration).also(::add)
    }

    /** Add annotation type from name. */
    fun addAnnotation(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationTypeSpec(type, configuration).also(::add)
    }

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    inline fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationTypeSpec(type, configuration).also(::add)
    }

    /** Property delegate for adding class type from name. */
    val addingClass: SpecLoader<TypeSpec> get() = createSpecLoader(::addClass)

    /** Property delegate for adding class type from name with initialization [configuration]. */
    fun addingClass(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addClass(it, configuration) }
    }

    /** Property delegate for adding expect class type from name. */
    val addingExpectClass: SpecLoader<TypeSpec> get() = createSpecLoader(::addExpectClass)

    /** Property delegate for adding expect class type from name with initialization [configuration]. */
    fun addingExpectClass(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addExpectClass(it, configuration) }
    }

    /** Property delegate for adding object type from name. */
    val addingObject: SpecLoader<TypeSpec> get() = createSpecLoader(::addObject)

    /** Property delegate for adding object type from name with initialization [configuration]. */
    fun addingObject(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addObject(it, configuration) }
    }

    /** Property delegate for adding companion object type from name. */
    val addingCompanionObject: SpecLoader<TypeSpec> get() = createSpecLoader(::addCompanionObject)

    /** Property delegate for adding companion object type from name with initialization [configuration]. */
    fun addingCompanionObject(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addCompanionObject(it, configuration) }
    }

    /** Property delegate for adding interface type from name. */
    val addingInterface: SpecLoader<TypeSpec> get() = createSpecLoader(::addInterface)

    /** Property delegate for adding interface type from name with initialization [configuration]. */
    fun addingInterface(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addInterface(it, configuration) }
    }

    /**
     * Property delegate for adding enum type from name with initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addingEnum(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addEnum(it, configuration) }
    }

    /** Property delegate for adding annotation type from name. */
    val addingAnnotation: SpecLoader<TypeSpec> get() = createSpecLoader(::addAnnotation)

    /** Property delegate for adding annotation type from name with initialization [configuration]. */
    fun addingAnnotation(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addAnnotation(it, configuration) }
    }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeSpecListScope internal constructor(actualList: MutableList<TypeSpec>) : TypeSpecList(actualList)
