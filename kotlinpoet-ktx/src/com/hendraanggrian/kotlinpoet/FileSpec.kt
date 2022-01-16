package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollection
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.FunSpecCollection
import com.hendraanggrian.kotlinpoet.collections.FunSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecCollection
import com.hendraanggrian.kotlinpoet.collections.PropertySpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecCollection
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.TypeSpecCollection
import com.hendraanggrian.kotlinpoet.collections.TypeSpecCollectionScope
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.Import
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.KClass

/** Converts type to [FileSpec]. */
fun fileSpecOf(type: TypeSpec, packageName: String): FileSpec = FileSpec.get(packageName, type)

/**
 * Builds a new [FileSpec],
 * by populating newly created [FileSpecBuilder] using provided [configuration].
 */
fun buildFileSpec(packageName: String, fileName: String, configuration: FileSpecBuilder.() -> Unit): FileSpec =
    FileSpecBuilder(FileSpec.builder(packageName, fileName)).apply(configuration).build()

/** Modify existing [FileSpec.Builder] using provided [configuration]. */
fun FileSpec.Builder.edit(configuration: FileSpecBuilder.() -> Unit): FileSpec.Builder =
    FileSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class FileSpecBuilder internal constructor(val nativeBuilder: FileSpec.Builder) {

    /** Package name of this file. */
    val packageName: String get() = nativeBuilder.packageName

    /** Name of this file. */
    val name: String get() = nativeBuilder.name

    /** Tags variables of this file. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Imports of this file. */
    val imports: List<Import> get() = nativeBuilder.imports

    /** Members of this file. */
    val members: MutableList<Any> get() = nativeBuilder.members

    /** Annotations of this file. */
    val annotations: AnnotationSpecCollection = object : AnnotationSpecCollection(nativeBuilder.annotations) {
        /** Must override because it modifies [AnnotationSpec.useSiteTarget]. */
        override fun add(element: AnnotationSpec): Boolean {
            nativeBuilder.addAnnotation(element)
            return true
        }
    }

    /** Configures annotations for this file. */
    fun annotations(configuration: AnnotationSpecCollectionScope.() -> Unit): Unit =
        AnnotationSpecCollectionScope(annotations).configuration()

    /** Add file comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Clear file comment. */
    fun clearComment() {
        nativeBuilder.clearComment()
    }

    /**
     * Types of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val types: TypeSpecCollection = object : TypeSpecCollection(FakeList()) {
        override fun add(element: TypeSpec): Boolean {
            nativeBuilder.addType(element)
            return true
        }
    }

    /** Configures types for this file. */
    fun types(configuration: TypeSpecCollectionScope.() -> Unit): Unit =
        TypeSpecCollectionScope(types).configuration()

    /**
     * Functions of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val functions: FunSpecCollection = object : FunSpecCollection(FakeList()) {
        override fun add(element: FunSpec): Boolean {
            nativeBuilder.addFunction(element)
            return true
        }
    }

    /** Configures functions for this file. */
    fun functions(configuration: FunSpecCollectionScope.() -> Unit): Unit =
        FunSpecCollectionScope(functions).configuration()

    /**
     * Properties of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val properties: PropertySpecCollection = object : PropertySpecCollection(FakeList()) {
        override fun add(element: PropertySpec): Boolean {
            nativeBuilder.addProperty(element)
            return true
        }
    }

    /** Configures properties for this file. */
    fun properties(configuration: PropertySpecCollectionScope.() -> Unit): Unit =
        PropertySpecCollectionScope(properties).configuration()

    /**
     * Type aliases of this file.
     * Returns a fake list where the only supported operation is `add`.
     */
    val typeAliases: TypeAliasSpecCollection = object : TypeAliasSpecCollection(FakeList()) {
        override fun add(element: TypeAliasSpec): Boolean {
            nativeBuilder.addTypeAlias(element)
            return true
        }
    }

    /** Configures type aliases for this file. */
    fun typeAliases(configuration: TypeAliasSpecCollectionScope.() -> Unit): Unit =
        TypeAliasSpecCollectionScope(typeAliases).configuration()

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
