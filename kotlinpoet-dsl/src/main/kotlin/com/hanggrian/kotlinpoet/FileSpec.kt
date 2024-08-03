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
    return FileSpecBuilder(FileSpec.builder(packageName, fileName)).apply(configuration).build()
}

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class FileSpecBuilder(private val nativeBuilder: FileSpec.Builder) :
    AnnotationSpecHandler,
    TypeSpecHandler,
    FunSpecHandler,
    PropertySpecHandler,
    TypeAliasSpecHandler {
    public val packageName: String get() = nativeBuilder.packageName
    public val name: String get() = nativeBuilder.name
    public val isScript: Boolean get() = nativeBuilder.isScript
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val defaultImports: MutableSet<String> get() = nativeBuilder.defaultImports
    public val imports: List<Import> get() = nativeBuilder.imports
    public val members: MutableList<Any> get() = nativeBuilder.members

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun comment(format: String, vararg args: Any) {
        nativeBuilder.addFileComment(format, *args)
    }

    public fun clearComment() {
        nativeBuilder.clearComment()
    }

    public override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    public override fun function(function: FunSpec) {
        nativeBuilder.addFunction(function)
    }

    public override fun property(property: PropertySpec) {
        nativeBuilder.addProperty(property)
    }

    public override fun typeAlias(typeAlias: TypeAliasSpec) {
        nativeBuilder.addTypeAlias(typeAlias)
    }

    public fun import(constant: Enum<*>) {
        nativeBuilder.addImport(constant)
    }

    public fun import(type: ClassName, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    public fun import(type: Class<*>, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    public fun import(type: KClass<*>, vararg names: String) {
        nativeBuilder.addImport(type.java, *names)
    }

    public inline fun <reified T> import(vararg names: String): Unit = import(T::class, *names)

    public fun import(packageName: String, vararg names: String) {
        nativeBuilder.addImport(packageName, *names)
    }

    public fun clearImports() {
        nativeBuilder.clearImports()
    }

    public fun aliasedImport(type: Class<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    public fun aliasedImport(type: KClass<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type.java, `as`)
    }

    public inline fun <reified T> aliasedImport(`as`: String): Unit = aliasedImport(T::class, `as`)

    public fun aliasedImport(type: ClassName, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    public fun aliasedImport(type: ClassName, member: String, `as`: String) {
        nativeBuilder.addAliasedImport(type, member, `as`)
    }

    public fun aliasedImport(member: MemberName, `as`: String) {
        nativeBuilder.addAliasedImport(member, `as`)
    }

    public fun kotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true) {
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

    public fun clear() {
        nativeBuilder.clearBody()
    }

    public fun build(): FileSpec = nativeBuilder.build()
}
