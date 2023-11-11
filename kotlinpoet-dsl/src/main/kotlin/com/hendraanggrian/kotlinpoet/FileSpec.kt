@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

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
 * Creates new [FileSpec] by populating newly created [FileSpecBuilder] using provided
 * [configuration].
 */
inline fun buildFileSpec(
    packageName: String,
    fileName: String,
    configuration: FileSpecBuilder.() -> Unit,
): FileSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FileSpecBuilder(FileSpec.builder(packageName, fileName)).apply(configuration).build()
}

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class FileSpecBuilder(
    private val nativeBuilder: FileSpec.Builder,
) : AnnotationSpecHandler,
    TypeSpecHandler,
    FunSpecHandler,
    PropertySpecHandler,
    TypeAliasSpecHandler {
    val packageName: String get() = nativeBuilder.packageName
    val name: String get() = nativeBuilder.name
    val isScript: Boolean get() = nativeBuilder.isScript
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val defaultImports: MutableSet<String> get() = nativeBuilder.defaultImports
    val imports: List<Import> get() = nativeBuilder.imports
    val members: MutableList<Any> get() = nativeBuilder.members

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun comment(format: String, vararg args: Any) {
        nativeBuilder.addFileComment(format, *args)
    }

    fun clearComment() {
        nativeBuilder.clearComment()
    }

    override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    override fun function(function: FunSpec) {
        nativeBuilder.addFunction(function)
    }

    override fun property(property: PropertySpec) {
        nativeBuilder.addProperty(property)
    }

    override fun typeAlias(typeAlias: TypeAliasSpec) {
        nativeBuilder.addTypeAlias(typeAlias)
    }

    fun import(constant: Enum<*>) {
        nativeBuilder.addImport(constant)
    }

    fun import(type: ClassName, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    fun import(type: Class<*>, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    fun import(type: KClass<*>, vararg names: String) {
        nativeBuilder.addImport(type.java, *names)
    }

    inline fun <reified T> import(vararg names: String): Unit = import(T::class, *names)

    fun import(packageName: String, vararg names: String) {
        nativeBuilder.addImport(packageName, *names)
    }

    fun clearImports() {
        nativeBuilder.clearImports()
    }

    fun aliasedImport(type: Class<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    fun aliasedImport(type: KClass<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type.java, `as`)
    }

    inline fun <reified T> aliasedImport(`as`: String): Unit = aliasedImport(T::class, `as`)

    fun aliasedImport(type: ClassName, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    fun aliasedImport(type: ClassName, member: String, `as`: String) {
        nativeBuilder.addAliasedImport(type, member, `as`)
    }

    fun aliasedImport(member: MemberName, `as`: String) {
        nativeBuilder.addAliasedImport(member, `as`)
    }

    fun kotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true) {
        nativeBuilder.addKotlinDefaultImports(includeJvm, includeJs)
    }

    var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.indent(value)
        }

    var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    fun bodyComment(format: String, vararg args: Any) {
        nativeBuilder.addBodyComment(format, *args)
    }

    fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    fun clear() {
        nativeBuilder.clearBody()
    }

    fun build(): FileSpec = nativeBuilder.build()
}
