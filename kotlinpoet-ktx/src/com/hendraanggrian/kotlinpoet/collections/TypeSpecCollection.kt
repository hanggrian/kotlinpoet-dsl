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

/** A [TypeSpecCollection] is responsible for managing a set of type instances. */
open class TypeSpecCollection(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(TypeSpec.classBuilder(type).build())

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(TypeSpec.classBuilder(type).build())

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add expect class type from name. */
    fun addExpectClass(type: String): Boolean = add(TypeSpec.expectClassBuilder(type).build())

    /** Add expect class type from name with custom initialization [configuration]. */
    fun addExpectClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildExpectClassTypeSpec(type, configuration))

    /** Add expect class type from [ClassName]. */
    fun addExpectClass(type: ClassName): Boolean = add(TypeSpec.expectClassBuilder(type).build())

    /** Add expect class type from [ClassName] with custom initialization [configuration]. */
    fun addExpectClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildExpectClassTypeSpec(type, configuration))

    /** Add object type from name. */
    fun addObject(type: String): Boolean = add(TypeSpec.objectBuilder(type).build())

    /** Add object type from name with custom initialization [configuration]. */
    fun addObject(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildObjectTypeSpec(type, configuration))

    /** Add object type from [ClassName]. */
    fun addObject(type: ClassName): Boolean = add(TypeSpec.objectBuilder(type).build())

    /** Add object type from [ClassName] with custom initialization [configuration]. */
    fun addObject(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildObjectTypeSpec(type, configuration))

    /** Add object type from name. */
    fun addCompanionObject(type: String? = null): Boolean = add(TypeSpec.companionObjectBuilder(type).build())

    /** Add object type from name with custom initialization [configuration]. */
    fun addCompanionObject(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildCompanionObjectTypeSpec(type, configuration))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add anonymous type from block. */
    fun addAnonymous(): Boolean = add(TypeSpec.anonymousClassBuilder().build())

    /** Add anonymous type from block with custom initialization [configuration]. */
    fun addAnonymous(configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(configuration))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecMarker
class TypeSpecCollectionScope(actualList: MutableList<TypeSpec>) : TypeSpecCollection(actualList)
