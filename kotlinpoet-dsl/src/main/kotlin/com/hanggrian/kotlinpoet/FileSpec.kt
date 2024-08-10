@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.Import
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [FileSpec] by populating newly created [FileSpecBuilder] using provided
 * [configuration].
 */
public fun buildFileSpec(
    packageName: String,
    fileName: String,
    configuration: FileSpecBuilder.() -> Unit,
): FileSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FileSpecBuilder(FileSpec.builder(packageName, fileName))
        .apply(configuration)
        .build()
}

/**
 * Builds new [FileSpec] by populating newly created [FileSpecBuilder] using provided
 * [configuration].
 */
public fun buildScriptFileSpec(
    packageName: String,
    fileName: String,
    configuration: FileSpecBuilder.() -> Unit,
): FileSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FileSpecBuilder(FileSpec.scriptBuilder(packageName, fileName))
        .apply(configuration)
        .build()
}

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class FileSpecBuilder(private val nativeBuilder: FileSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    public val types: TypeSpecHandler =
        object : TypeSpecHandler {
            override fun add(type: TypeSpec) {
                nativeBuilder.addType(type)
            }
        }

    public val functions: FunSpecHandler =
        object : FunSpecHandler {
            override fun add(function: FunSpec) {
                nativeBuilder.addFunction(function)
            }
        }

    public val properties: PropertySpecHandler =
        object : PropertySpecHandler {
            override fun add(property: PropertySpec) {
                nativeBuilder.addProperty(property)
            }
        }

    public val typeAliases: TypeAliasSpecHandler =
        object : TypeAliasSpecHandler {
            override fun add(typeAlias: TypeAliasSpec) {
                nativeBuilder.addTypeAlias(typeAlias)
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    /** Invokes DSL to configure [TypeSpec] collection. */
    public fun types(configuration: TypeSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecHandlerScope
            .of(types)
            .configuration()
    }

    /** Invokes DSL to configure [FunSpec] collection. */
    public fun functions(configuration: FunSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        FunSpecHandlerScope
            .of(functions)
            .configuration()
    }

    /** Invokes DSL to configure [PropertySpec] collection. */
    public fun properties(configuration: PropertySpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        PropertySpecHandlerScope
            .of(properties)
            .configuration()
    }

    /** Invokes DSL to configure [TypeAliasSpec] collection. */
    public fun typeAliases(configuration: TypeAliasSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeAliasSpecHandlerScope
            .of(typeAliases)
            .configuration()
    }

    public val packageName: String get() = nativeBuilder.packageName
    public val name: String get() = nativeBuilder.name
    public val isScript: Boolean get() = nativeBuilder.isScript
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val defaultImports: MutableSet<String> get() = nativeBuilder.defaultImports
    public val imports: List<Import> get() = nativeBuilder.imports
    public val members: MutableList<Any> get() = nativeBuilder.members

    public fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addFileComment(format, *args)
    }

    public fun clearComment() {
        nativeBuilder.clearComment()
    }

    public fun addImport(constant: Enum<*>) {
        nativeBuilder.addImport(constant)
    }

    public fun addImport(type: ClassName, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    public fun addImport(type: Class<*>, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    public fun addImport(type: KClass<*>, vararg names: String) {
        nativeBuilder.addImport(type.java, *names)
    }

    public inline fun <reified T> addImport(vararg names: String): Unit =
        addImport(T::class, *names)

    public fun addImport(packageName: String, vararg names: String) {
        nativeBuilder.addImport(packageName, *names)
    }

    public fun clearImports() {
        nativeBuilder.clearImports()
    }

    public fun addAliasedImport(type: Class<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    public fun addAliasedImport(type: KClass<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type.java, `as`)
    }

    public inline fun <reified T> addAliasedImport(`as`: String): Unit =
        addAliasedImport(T::class, `as`)

    public fun addAliasedImport(type: ClassName, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    public fun addAliasedImport(type: ClassName, member: String, `as`: String) {
        nativeBuilder.addAliasedImport(type, member, `as`)
    }

    public fun addAliasedImport(member: MemberName, `as`: String) {
        nativeBuilder.addAliasedImport(member, `as`)
    }

    public fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true) {
        nativeBuilder.addKotlinDefaultImports(includeJvm, includeJs)
    }

    public var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.indent(value)
        }

    public var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    public fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    public fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    public fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    public fun bodyComment(format: String, vararg args: Any) {
        nativeBuilder.addBodyComment(format, *args)
    }

    public fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    public fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    public fun appendLine() {
        nativeBuilder.addStatement("")
    }

    public fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement("%L", code)
    }

    public fun clear() {
        nativeBuilder.clearBody()
    }

    public fun build(): FileSpec = nativeBuilder.build()
}
