package com.hendraanggrian.kotlinpoet.dsl

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

/** A [TypeSpecHandler] is responsible for managing a set of type instances. */
open class TypeSpecHandler(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(classTypeSpecOf(type))

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(classTypeSpecOf(type))

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add expect class type from name. */
    fun addExpectClass(type: String): Boolean = add(expectClassTypeSpecOf(type))

    /** Add expect class type from name with custom initialization [configuration]. */
    fun addExpectClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildExpectClassTypeSpec(type, configuration))

    /** Add expect class type from [ClassName]. */
    fun addExpectClass(type: ClassName): Boolean = add(expectClassTypeSpecOf(type))

    /** Add expect class type from [ClassName] with custom initialization [configuration]. */
    fun addExpectClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildExpectClassTypeSpec(type, configuration))

    /** Add object type from name. */
    fun addObject(type: String): Boolean = add(objectTypeSpecOf(type))

    /** Add object type from name with custom initialization [configuration]. */
    fun addObject(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildObjectTypeSpec(type, configuration))

    /** Add object type from [ClassName]. */
    fun addObject(type: ClassName): Boolean = add(objectTypeSpecOf(type))

    /** Add object type from [ClassName] with custom initialization [configuration]. */
    fun addObject(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildObjectTypeSpec(type, configuration))

    /** Add object type from name. */
    fun addCompanionObject(type: String? = null): Boolean = add(companionObjectTypeSpecOf(type))

    /** Add object type from name with custom initialization [configuration]. */
    fun addCompanionObject(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildCompanionObjectTypeSpec(type, configuration))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add anonymous type from block. */
    fun addAnonymous(): Boolean = add(emptyAnonymousTypeSpec())

    /** Add anonymous type from block with custom initialization [configuration]. */
    fun addAnonymous(configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(configuration))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecDslMarker
open class TypeSpecHandlerScope(actualList: MutableList<TypeSpec>) : TypeSpecHandler(actualList) {

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: String): Boolean = addClass(type)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean = addClass(type, configuration)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: ClassName): Boolean = addClass(type)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean = addClass(type, configuration)

    /** @see TypeSpecHandler.addExpectClass */
    fun expectClass(type: String): Boolean = addExpectClass(type)

    /** @see TypeSpecHandler.addExpectClass */
    fun expectClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addExpectClass(type, configuration)

    /** @see TypeSpecHandler.addExpectClass */
    fun expectClass(type: ClassName): Boolean = addExpectClass(type)

    /** @see TypeSpecHandler.addExpectClass */
    fun expectClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addExpectClass(type, configuration)

    /** @see TypeSpecHandler.addObject */
    fun `object`(type: String): Boolean = addObject(type)

    /** @see TypeSpecHandler.addObject */
    fun `object`(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean = addObject(type, configuration)

    /** @see TypeSpecHandler.addObject */
    fun `object`(type: ClassName): Boolean = add(objectTypeSpecOf(type))

    /** @see TypeSpecHandler.addObject */
    fun `object`(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean = addObject(type, configuration)

    /** @see TypeSpecHandler.addCompanionObject */
    fun companionObject(type: String? = null): Boolean = addCompanionObject(type)

    /** @see TypeSpecHandler.addCompanionObject */
    fun companionObject(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addCompanionObject(type, configuration)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: String): Boolean = addInterface(type)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addInterface(type, configuration)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: ClassName): Boolean = addInterface(type)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addInterface(type, configuration)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: String): Boolean = addEnum(type)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addEnum(type, configuration)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: ClassName): Boolean = addEnum(type)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addEnum(type, configuration)

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(): Boolean = addAnonymous()

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addAnonymous(configuration)

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}
