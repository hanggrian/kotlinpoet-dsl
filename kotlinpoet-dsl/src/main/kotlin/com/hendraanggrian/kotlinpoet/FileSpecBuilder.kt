@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.CodeBlockContainer
import com.hendraanggrian.kotlinpoet.collections.FunSpecList
import com.hendraanggrian.kotlinpoet.collections.FunSpecListScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecList
import com.hendraanggrian.kotlinpoet.collections.PropertySpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeSpecListScope
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

/** Converts type to [FileSpec]. */
inline fun fileSpecOf(type: TypeSpec, packageName: String): FileSpec = FileSpec.get(packageName, type)

/**
 * Builds a new [FileSpec],
 * by populating newly created [FileSpecBuilder] using provided [configuration].
 */
inline fun buildFileSpec(packageName: String, fileName: String, configuration: FileSpecBuilder.() -> Unit): FileSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FileSpecBuilder(FileSpec.builder(packageName, fileName)).apply(configuration).build()
}

/**
 * Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecMarker
class FileSpecBuilder(private val nativeBuilder: FileSpec.Builder) : CodeBlockContainer {
    val packageName: String get() = nativeBuilder.packageName
    val name: String get() = nativeBuilder.name
    val isScript: Boolean get() = nativeBuilder.isScript
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val defaultImports: MutableSet<String> get() = nativeBuilder.defaultImports
    val imports: List<Import> get() = nativeBuilder.imports
    val members: MutableList<Any> get() = nativeBuilder.members

    /** Annotations of this file. */
    val annotations: AnnotationSpecList = object : AnnotationSpecList(nativeBuilder.annotations) {
        /** Must override because it modifies [AnnotationSpec.useSiteTarget]. */
        override fun add(element: AnnotationSpec): Boolean {
            nativeBuilder.addAnnotation(element)
            return true
        }
    }

    /** Configures annotations for this file. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Adds a file-site comment. This is prefixed to the start of the file and different from [addBodyComment]. */
    fun addFileComment(format: String, vararg args: Any) {
        nativeBuilder.addFileComment(format, *args)
    }

    /** Clear file comment. */
    fun clearFileComment() {
        nativeBuilder.clearComment()
    }

    /**
     * Types of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val types: TypeSpecList = object : TypeSpecList(FakeList()) {
        override fun add(element: TypeSpec): Boolean {
            nativeBuilder.addType(element)
            return true
        }
    }

    /** Configures types for this file. */
    fun types(configuration: TypeSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecListScope(types).configuration()
    }

    /**
     * Functions of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val functions: FunSpecList = object : FunSpecList(FakeList()) {
        override fun add(element: FunSpec): Boolean {
            nativeBuilder.addFunction(element)
            return true
        }
    }

    /** Configures functions for this file. */
    fun functions(configuration: FunSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        FunSpecListScope(functions).configuration()
    }

    /**
     * Properties of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val properties: PropertySpecList = object : PropertySpecList(FakeList()) {
        override fun add(element: PropertySpec): Boolean {
            nativeBuilder.addProperty(element)
            return true
        }
    }

    /** Configures properties for this file. */
    fun properties(configuration: PropertySpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        PropertySpecListScope(properties).configuration()
    }

    /**
     * Type aliases of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val typeAliases: TypeAliasSpecList = object : TypeAliasSpecList(FakeList()) {
        override fun add(element: TypeAliasSpec): Boolean {
            nativeBuilder.addTypeAlias(element)
            return true
        }
    }

    /** Configures type aliases for this file. */
    fun typeAliases(configuration: TypeAliasSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeAliasSpecListScope(typeAliases).configuration()
    }

    /** Add import. */
    fun addImport(constant: Enum<*>) {
        nativeBuilder.addImport(constant)
    }

    /** Add import. */
    fun addImport(type: ClassName, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    /** Add import. */
    fun addImport(type: Class<*>, vararg names: String) {
        nativeBuilder.addImport(type, *names)
    }

    /** Add import. */
    fun addImport(type: KClass<*>, vararg names: String) {
        nativeBuilder.addImport(type.java, *names)
    }

    /** Add import with reified function. */
    inline fun <reified T> addImport(vararg names: String): Unit = addImport(T::class, *names)

    /** Add import. */
    fun addImport(packageName: String, vararg names: String) {
        nativeBuilder.addImport(packageName, *names)
    }

    fun clearImports() {
        nativeBuilder.clearImports()
    }

    /** Add aliased import. */
    fun addAliasedImport(type: Class<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    /** Add aliased import. */
    fun addAliasedImport(type: KClass<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type.java, `as`)
    }

    /** Add aliased import with reified function. */
    inline fun <reified T> addAliasedImport(`as`: String): Unit = addAliasedImport(T::class, `as`)

    /** Add aliased import. */
    fun addAliasedImport(type: ClassName, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    /** Add aliased import. */
    fun addAliasedImport(type: ClassName, member: String, `as`: String) {
        nativeBuilder.addAliasedImport(type, member, `as`)
    }

    /** Add aliased import. */
    fun addAliasedImport(member: MemberName, `as`: String) {
        nativeBuilder.addAliasedImport(member, `as`)
    }

    /** Adds Kotlin's standard default package imports. */
    fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true) {
        nativeBuilder.addKotlinDefaultImports(includeJvm, includeJs)
    }

    /** Set indent text. */
    var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.indent(value)
        }

    /** Convenient method to set [indent] with space the length of [indentSize]. */
    var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    /** Adds a comment to the body of this script file in the order that it was added. */
    fun addBodyComment(format: String, vararg args: Any) {
        nativeBuilder.addBodyComment(format, *args)
    }

    override fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    override fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun clear() {
        nativeBuilder.clearBody()
    }

    /** Returns native spec. */
    fun build(): FileSpec = nativeBuilder.build()

    /**
     * @see kotlin.collections.EmptyList
     */
    private class FakeList<T> : MutableList<T> {
        companion object {
            const val ERROR_MESSAGE = "FileSpec doesn't have real access to the requested specs. " +
                "The only supported behavior is `add`."
        }

        override fun equals(other: Any?): Boolean = other is List<*> && other.isEmpty()
        override fun hashCode(): Int = 1
        override fun toString(): String = "[]"

        override val size: Int get() = 0
        override fun isEmpty(): Boolean = true
        override fun contains(element: T): Boolean = false
        override fun containsAll(elements: Collection<T>): Boolean = elements.isEmpty()

        override fun get(index: Int): Nothing =
            throw IndexOutOfBoundsException("Fake list doesn't contain element at index $index.")

        override fun indexOf(element: T): Int = -1
        override fun lastIndexOf(element: T): Int = -1

        override fun iterator(): Nothing = error(ERROR_MESSAGE)
        override fun listIterator(): Nothing = error(ERROR_MESSAGE)
        override fun listIterator(index: Int): Nothing = error(ERROR_MESSAGE)

        override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
            if (fromIndex == 0 && toIndex == 0) return this
            throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex")
        }

        override fun add(element: T): Nothing = error(ERROR_MESSAGE)
        override fun add(index: Int, element: T): Nothing = error(ERROR_MESSAGE)
        override fun addAll(index: Int, elements: Collection<T>): Nothing = error(ERROR_MESSAGE)
        override fun addAll(elements: Collection<T>): Nothing = error(ERROR_MESSAGE)
        override fun clear(): Nothing = error(ERROR_MESSAGE)
        override fun remove(element: T): Nothing = error(ERROR_MESSAGE)
        override fun removeAll(elements: Collection<T>): Nothing = error(ERROR_MESSAGE)
        override fun removeAt(index: Int): Nothing = error(ERROR_MESSAGE)
        override fun retainAll(elements: Collection<T>): Nothing = error(ERROR_MESSAGE)
        override fun set(index: Int, element: T): Nothing = error(ERROR_MESSAGE)
    }
}
