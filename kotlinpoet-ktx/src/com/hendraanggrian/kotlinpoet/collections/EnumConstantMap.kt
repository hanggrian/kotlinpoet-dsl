package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.TypeSpecBuilder
import com.hendraanggrian.kotlinpoet.buildAnonymousTypeSpec
import com.hendraanggrian.kotlinpoet.createSpecLoader
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** A [EnumConstantMap] is responsible for managing a set of enum constants. */
@OptIn(ExperimentalContracts::class)
open class EnumConstantMap internal constructor(actualMap: MutableMap<String, TypeSpec>) :
    MutableMap<String, TypeSpec> by actualMap {

    /** Add enum constant using default type. */
    fun put(name: String): TypeSpec? = put(name, TypeSpec.anonymousClassBuilder().build())

    /** Add enum constant from formatted string. */
    fun put(name: String, constructorParameterFormat: String, vararg constructorParameterArgs: Any): TypeSpec? = put(
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
    ): TypeSpec? {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return put(
            name,
            buildAnonymousTypeSpec {
                addSuperclassConstructorParameter(constructorParameterFormat, *constructorParameterArgs)
                configuration()
            }
        )
    }

    /** Add enum constant from [CodeBlock]. */
    fun put(name: String, constructorParameterCode: CodeBlock): TypeSpec? =
        put(name, buildAnonymousTypeSpec { addSuperclassConstructorParameter(constructorParameterCode) })

    /** Add enum constant from [CodeBlock] with custom initialization [configuration]. */
    fun put(name: String, constructorParameterCode: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec? {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return put(
            name,
            buildAnonymousTypeSpec {
                addSuperclassConstructorParameter(constructorParameterCode)
                configuration()
            }
        )
    }

    /** Convenient method to add enum constant with operator function. */
    inline operator fun set(name: String, constructorParameterFormat: String) {
        put(name, constructorParameterFormat)
    }

    /** Convenient method to add enum constant with operator function. */
    inline operator fun set(name: String, constructorParameterCode: CodeBlock) {
        put(name, constructorParameterCode)
    }

    /** Property delegate for adding enum constant using default type. */
    val putting: SpecLoader<TypeSpec?> get() = createSpecLoader(::put)

    /** Property delegate for adding enum constant from formatted string. */
    fun putting(constructorParameterFormat: String, vararg constructorParameterArgs: Any): SpecLoader<TypeSpec?> =
        createSpecLoader { put(it, constructorParameterFormat, *constructorParameterArgs) }

    /** Property delegate for adding enum constant from formatted string with custom initialization [configuration]. */
    fun putting(
        constructorParameterFormat: String,
        vararg constructorParameterArgs: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): SpecLoader<TypeSpec?> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader {
            put(it, constructorParameterFormat, *constructorParameterArgs, configuration = configuration)
        }
    }

    /** Property delegate for adding enum constant from [CodeBlock]. */
    fun putting(constructorParameterCode: CodeBlock): SpecLoader<TypeSpec?> =
        createSpecLoader { put(it, constructorParameterCode) }

    /** Property delegate for adding enum constant from [CodeBlock] with custom initialization [configuration]. */
    fun putting(constructorParameterCode: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec?> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { put(it, constructorParameterCode, configuration) }
    }
}

/** Receiver for the `enumConstants` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class EnumConstantMapScope internal constructor(actualMap: MutableMap<String, TypeSpec>) : EnumConstantMap(actualMap) {

    /** @see EnumConstantMap.put */
    inline operator fun String.invoke(
        anonymousTypeCode: CodeBlock,
        noinline configuration: TypeSpecBuilder.() -> Unit
    ): TypeSpec? = put(this, anonymousTypeCode, configuration)

    /** @see EnumConstantMap.put */
    inline operator fun String.invoke(
        anonymousTypeFormat: String,
        vararg anonymousTypeArgs: Any,
        noinline configuration: TypeSpecBuilder.() -> Unit
    ): TypeSpec? = put(this, anonymousTypeFormat, *anonymousTypeArgs, configuration = configuration)
}
