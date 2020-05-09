package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecContainer
import com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecContainerScope
import com.hendraanggrian.kotlinpoet.dsl.FunSpecContainer
import com.hendraanggrian.kotlinpoet.dsl.FunSpecContainerScope
import com.hendraanggrian.kotlinpoet.dsl.PropertySpecContainer
import com.hendraanggrian.kotlinpoet.dsl.PropertySpecContainerScope
import com.hendraanggrian.kotlinpoet.dsl.TypeAliasSpecContainer
import com.hendraanggrian.kotlinpoet.dsl.TypeAliasSpecContainerScope
import com.hendraanggrian.kotlinpoet.dsl.TypeSpecContainer
import com.hendraanggrian.kotlinpoet.dsl.TypeSpecContainerScope
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
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
inline fun buildFileSpec(packageName: String, fileName: String, builderAction: FileSpecBuilder.() -> Unit): FileSpec =
    FileSpec.builder(packageName, fileName).build(builderAction)

/** Modify existing [FileSpec.Builder] using provided [builderAction] and then building it. */
inline fun FileSpec.Builder.build(builderAction: FileSpecBuilder.() -> Unit): FileSpec =
    FileSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class FileSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FileSpec.Builder) {

    /** Tags variables of this file. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Annotations of this file. */
    val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    /** Configure annotations without DSL. */
    val annotations: AnnotationSpecContainer = object : AnnotationSpecContainer() {
        override fun addAll(specs: Iterable<AnnotationSpec>): Boolean = nativeBuilder.annotations.addAll(specs)
        override fun add(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    /** Configure annotations with DSL. */
    inline fun annotations(configuration: AnnotationSpecContainerScope.() -> Unit) =
        AnnotationSpecContainerScope(annotations).configuration()

    /** Add file comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Configure types without DSL. */
    val types: TypeSpecContainer = object : TypeSpecContainer() {
        override fun addAll(specs: Iterable<TypeSpec>): Boolean = specs.manualAddAll(::add)
        override fun add(spec: TypeSpec) {
            nativeBuilder.addType(spec)
        }
    }

    /** Configure types with DSL. */
    inline fun types(configuration: TypeSpecContainerScope.() -> Unit) =
        TypeSpecContainerScope(types).configuration()

    /** Configure functions without DSL. */
    val functions: FunSpecContainer = object : FunSpecContainer() {
        override fun addAll(specs: Iterable<FunSpec>): Boolean = specs.manualAddAll(::add)
        override fun add(spec: FunSpec) {
            nativeBuilder.addFunction(spec)
        }
    }

    /** Configure functions with DSL. */
    inline fun functions(configuration: FunSpecContainerScope.() -> Unit) =
        FunSpecContainerScope(functions).configuration()

    /** Configure properties without DSL. */
    val properties: PropertySpecContainer = object : PropertySpecContainer() {
        override fun addAll(specs: Iterable<PropertySpec>): Boolean = specs.manualAddAll(::add)
        override fun add(spec: PropertySpec) {
            nativeBuilder.addProperty(spec)
        }
    }

    /** Configure properties with DSL. */
    inline fun properties(configuration: PropertySpecContainerScope.() -> Unit) =
        PropertySpecContainerScope(properties).configuration()

    /** Configure type-aliases without DSL. */
    val typeAliases: TypeAliasSpecContainer = object : TypeAliasSpecContainer() {
        override fun addAll(specs: Iterable<TypeAliasSpec>): Boolean = specs.manualAddAll(::add)
        override fun add(spec: TypeAliasSpec) {
            nativeBuilder.addTypeAlias(spec)
        }
    }

    /** Configure type-aliases with DSL. */
    inline fun typeAliases(configuration: TypeAliasSpecContainerScope.() -> Unit) =
        TypeAliasSpecContainerScope(typeAliases).configuration()

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
}
