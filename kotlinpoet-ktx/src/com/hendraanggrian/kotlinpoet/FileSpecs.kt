package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.hendraanggrian.kotlinpoet.dsl.FunContainer
import com.hendraanggrian.kotlinpoet.dsl.PropertyContainer
import com.hendraanggrian.kotlinpoet.dsl.TypeAliasContainer
import com.hendraanggrian.kotlinpoet.dsl.TypeContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.KClass

/**
 * Builds a new [FileSpec],
 * by populating newly created [FileSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildJavaFile(packageName: String, name: String, builderAction: FileSpecBuilder.() -> Unit): FileSpec =
    FileSpecBuilder(FileSpec.builder(packageName, name)).apply(builderAction).build()

/** Wrapper of [FileSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class FileSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FileSpec.Builder) {

    /** Annotations of this builder. */
    val annotationSpecs: MutableList<AnnotationSpec>
        get() = nativeBuilder.annotations

    /** Tags variables of this builder. */
    val tags: MutableMap<KClass<*>, *>
        get() = nativeBuilder.tags

    /** Returns native spec. */
    fun build(): FileSpec =
        nativeBuilder.build()

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec =
            spec.also { nativeBuilder.addAnnotation(it) }
    }

    /** Add file comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Collection of types, may be configured with Kotlin DSL. */
    val types: TypeContainer = object : TypeContainer() {
        override fun add(spec: TypeSpec): TypeSpec =
            spec.also { nativeBuilder.addType(it) }
    }

    /** Collection of functions, may be configured with Kotlin DSL. */
    val functions: FunContainer = object : FunContainer() {
        override fun add(spec: FunSpec): FunSpec =
            spec.also { nativeBuilder.addFunction(it) }
    }

    /** Collection of fields, may be configured with Kotlin DSL. */
    val properties: PropertyContainer = object : PropertyContainer() {
        override fun add(spec: PropertySpec): PropertySpec =
            spec.also { nativeBuilder.addProperty(it) }
    }

    /** Collection of type aliases, may be configured with Kotlin DSL. */
    val typeAliases: TypeAliasContainer = object : TypeAliasContainer() {
        override fun add(spec: TypeAliasSpec): TypeAliasSpec =
            spec.also { nativeBuilder.addTypeAlias(it) }
    }
}
