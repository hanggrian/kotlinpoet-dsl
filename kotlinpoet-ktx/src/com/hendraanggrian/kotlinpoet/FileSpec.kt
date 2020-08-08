package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.FunSpecList
import com.hendraanggrian.kotlinpoet.collections.FunSpecListScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecList
import com.hendraanggrian.kotlinpoet.collections.PropertySpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeAliasSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeSpecListScope
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
 * by populating newly created [FileSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFileSpec(
    packageName: String,
    fileName: String,
    builderAction: FileSpecBuilder.() -> Unit
): FileSpec = FileSpec.builder(packageName, fileName).build(builderAction)

/** Modify existing [FileSpec.Builder] using provided [builderAction] and then building it. */
inline fun FileSpec.Builder.build(
    builderAction: FileSpecBuilder.() -> Unit
): FileSpec = FileSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class FileSpecBuilder(private val nativeBuilder: FileSpec.Builder) {

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
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations for this file. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

    /** Add file comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
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
    inline fun types(configuration: TypeSpecListScope.() -> Unit) =
        TypeSpecListScope(types).configuration()

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
    inline fun functions(configuration: FunSpecListScope.() -> Unit) =
        FunSpecListScope(functions).configuration()

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
    inline fun properties(configuration: PropertySpecListScope.() -> Unit) =
        PropertySpecListScope(properties).configuration()

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
    inline fun typeAliases(configuration: TypeAliasSpecListScope.() -> Unit) =
        TypeAliasSpecListScope(typeAliases).configuration()

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
    fun addImport(type: KClass<*>, vararg names: String) = addImport(type.java, *names)

    /** Add import with reified function. */
    inline fun <reified T> addImport(vararg names: String) = addImport(T::class, *names)

    /** Add import. */
    fun addImport(packageName: String, vararg names: String) {
        nativeBuilder.addImport(packageName, *names)
    }

    /** Add aliased import. */
    fun addAliasedImport(type: Class<*>, `as`: String) {
        nativeBuilder.addAliasedImport(type, `as`)
    }

    /** Add aliased import. */
    fun addAliasedImport(type: KClass<*>, `as`: String) = addAliasedImport(type.java, `as`)

    /** Add aliased import with reified function. */
    inline fun <reified T> addAliasedImport(`as`: String) = addAliasedImport(T::class, `as`)

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
    inline var indentSize: Int
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
