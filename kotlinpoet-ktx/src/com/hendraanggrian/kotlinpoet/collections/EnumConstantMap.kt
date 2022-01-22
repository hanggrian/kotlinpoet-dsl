package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.buildAnonymousTypeSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec

/** A [EnumConstantMap] is responsible for managing a set of enum constants. */
open class EnumConstantMap internal constructor(actualMap: MutableMap<String, TypeSpec>) :
    MutableMap<String, TypeSpec> by actualMap {

    /** Add enum constant using default type. */
    fun put(name: String): TypeSpec? = put(name, TypeSpec.anonymousClassBuilder().build())

    /** Add enum constant from formatted string. */
    fun put(
        name: String,
        constructorParameterFormat: String,
        vararg constructorParameterArgs: Any
    ): TypeSpec? = put(
        name,
        buildAnonymousTypeSpec {
            addSuperclassConstructorParameter(constructorParameterFormat, *constructorParameterArgs)
        }
    )

    /** Add enum constant from formatted string with custom initialization [configuration]. */
    fun put(
        name: String,
        constructorParameterFormat: String,
        vararg constructorParameterArgs: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): TypeSpec? = put(
        name,
        buildAnonymousTypeSpec {
            addSuperclassConstructorParameter(constructorParameterFormat, *constructorParameterArgs)
            configuration()
        }
    )

    /** Add enum constant from [CodeBlock]. */
    fun put(name: String, constructorParameterCode: CodeBlock): TypeSpec? = put(
        name,
        buildAnonymousTypeSpec {
            addSuperclassConstructorParameter(constructorParameterCode)
        }
    )

    /** Add enum constant from [CodeBlock] with custom initialization [configuration]. */
    fun put(name: String, constructorParameterCode: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec? =
        put(
            name,
            buildAnonymousTypeSpec {
                addSuperclassConstructorParameter(constructorParameterCode)
                configuration()
            }
        )

    /** Convenient method to add enum constant with operator function. */
    inline operator fun set(name: String, constructorParameterFormat: String) {
        put(name, constructorParameterFormat)
    }

    /** Convenient method to add enum constant with operator function. */
    inline operator fun set(name: String, constructorParameterCode: CodeBlock) {
        put(name, constructorParameterCode)
    }
}

/** Receiver for the `enumConstants` block providing an extended set of operators for the configuration. */
@SpecMarker
class EnumConstantMapScope internal constructor(actualMap: MutableMap<String, TypeSpec>) : EnumConstantMap(actualMap) {

    /** @see EnumConstantMap.put */
    operator fun String.invoke(anonymousTypeCode: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec? =
        put(this, anonymousTypeCode, configuration)

    /** @see EnumConstantMap.put */
    operator fun String.invoke(
        anonymousTypeFormat: String,
        vararg anonymousTypeArgs: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): TypeSpec? = put(this, anonymousTypeFormat, *anonymousTypeArgs, configuration = configuration)
}
