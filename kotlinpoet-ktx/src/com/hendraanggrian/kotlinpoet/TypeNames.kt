package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Return a nullable copy of [TypeName] converted from [Type].  */
inline fun Type.asNullable(): TypeName = asTypeName().asNullable()

/** Return a nullable copy of [ClassName] converted from [Class].  */
inline fun Class<*>.asNullable(): ClassName = asClassName().asNullable()

/** Return a nullable copy of [ClassName] converted from [KClass].  */
inline fun KClass<*>.asNullable(): ClassName = asClassName().asNullable()

/** Return a nullable copy of [ClassName].  */
fun ClassName.asNullable(): ClassName = copyOf(nullable = true)

/** Return a nullable copy of [LambdaTypeName].  */
fun LambdaTypeName.asNullable(): LambdaTypeName = copyOf(nullable = true)

/** Return a nullable copy of [ParameterizedTypeName].  */
fun ParameterizedTypeName.asNullable(): ParameterizedTypeName = copyOf(nullable = true)

/** Return a nullable copy of [TypeName].  */
fun TypeName.asNullable(): TypeName = copyOf(nullable = true)

/** Return a nullable copy of [TypeVariableName].  */
fun TypeVariableName.asNullable(): TypeVariableName = copyOf(nullable = true)

/** Return a nullable copy of [WildcardTypeName].  */
fun WildcardTypeName.asNullable(): WildcardTypeName = copyOf(nullable = true)

/** Return a non-nullable copy of [TypeName] converted from [Type].  */
inline fun Type.asNotNull(): TypeName = asTypeName().asNotNull()

/** Return a non-nullable copy of [ClassName] converted from [Class].  */
inline fun Class<*>.asNotNull(): ClassName = asClassName().asNotNull()

/** Return a non-nullable copy of [ClassName] converted from [KClass].  */
inline fun KClass<*>.asNotNull(): ClassName = asClassName().asNotNull()

/** Return a non-nullable copy of [ClassName].  */
fun ClassName.asNotNull(): ClassName = copyOf(nullable = false)

/** Return a non-nullable copy of [LambdaTypeName].  */
fun LambdaTypeName.asNotNull(): LambdaTypeName = copyOf(nullable = false)

/** Return a non-nullable copy of [ParameterizedTypeName].  */
fun ParameterizedTypeName.asNotNull(): ParameterizedTypeName = copyOf(nullable = false)

/** Return a non-nullable copy of [TypeName].  */
fun TypeName.asNotNull(): TypeName = copyOf(nullable = false)

/** Return a non-nullable copy of [TypeVariableName].  */
fun TypeVariableName.asNotNull(): TypeVariableName = copyOf(nullable = false)

/** Return a non-nullable copy of [WildcardTypeName].  */
fun WildcardTypeName.asNotNull(): WildcardTypeName = copyOf(nullable = false)

/** Return a copy of [TypeName] converted from [Type] annotated with a set of [AnnotationSpec]. */
inline fun Type.annotate(vararg specs: AnnotationSpec): TypeName =
    asTypeName().annotate(*specs)

/** Return a copy of [ClassName] converted from [Class] annotated with a set of [AnnotationSpec]. */
inline fun Class<*>.annotate(vararg specs: AnnotationSpec): ClassName =
    asClassName().annotate(*specs)

/** Return a copy of [ClassName] converted from [KClass] annotated with a set of [AnnotationSpec]. */
inline fun KClass<*>.annotate(vararg specs: AnnotationSpec): ClassName =
    asClassName().annotate(*specs)

/** Return a copy of [ClassName] annotated with a set of [AnnotationSpec]. */
fun ClassName.annotate(vararg specs: AnnotationSpec): ClassName =
    copyOf(annotations = specs.toList())

/** Return a copy of [LambdaTypeName] annotated with a set of [AnnotationSpec]. */
fun LambdaTypeName.annotate(vararg specs: AnnotationSpec): LambdaTypeName =
    copyOf(annotations = specs.toList())

/** Return a copy of [ParameterizedTypeName] annotated with a set of [AnnotationSpec]. */
fun ParameterizedTypeName.annotate(vararg specs: AnnotationSpec): ParameterizedTypeName =
    copyOf(annotations = specs.toList())

/** Return a copy of [TypeName] annotated with a set of [AnnotationSpec]. */
fun TypeName.annotate(vararg specs: AnnotationSpec): TypeName =
    copyOf(annotations = specs.toList())

/** Return a copy of [TypeVariableName] annotated with a set of [AnnotationSpec]. */
fun TypeVariableName.annotate(vararg specs: AnnotationSpec): TypeVariableName =
    copyOf(annotations = specs.toList())

/** Return a copy of [WildcardTypeName] annotated with a set of [AnnotationSpec]. */
fun WildcardTypeName.annotate(vararg specs: AnnotationSpec): WildcardTypeName =
    copyOf(annotations = specs.toList())

/** Return a copy of [TypeName] converted from [Type] annotated with a set of [ClassName]. */
inline fun Type.annotate(vararg types: ClassName): TypeName =
    asTypeName().annotate(*types)

/** Return a copy of [ClassName] converted from [Class] annotated with a set of [ClassName]. */
inline fun Class<*>.annotate(vararg types: ClassName): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] converted from [KClass] annotated with a set of [ClassName]. */
inline fun KClass<*>.annotate(vararg types: ClassName): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] annotated with a set of [ClassName]. */
fun ClassName.annotate(vararg types: ClassName): ClassName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [LambdaTypeName] annotated with a set of [ClassName]. */
fun LambdaTypeName.annotate(vararg types: ClassName): LambdaTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [ParameterizedTypeName] annotated with a set of [ClassName]. */
fun ParameterizedTypeName.annotate(vararg types: ClassName): ParameterizedTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] annotated with a set of [ClassName]. */
fun TypeName.annotate(vararg types: ClassName): TypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeVariableName] annotated with a set of [ClassName]. */
fun TypeVariableName.annotate(vararg types: ClassName): TypeVariableName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [WildcardTypeName] annotated with a set of [ClassName]. */
fun WildcardTypeName.annotate(vararg types: ClassName): WildcardTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] converted from [Type] annotated with a set of [Class]. */
inline fun Type.annotate(vararg types: Class<out Annotation>): TypeName =
    asTypeName().annotate(*types)

/** Return a copy of [ClassName] converted from [Class] annotated with a set of [Class]. */
inline fun Class<*>.annotate(vararg types: Class<out Annotation>): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] converted from [KClass] annotated with a set of [Class]. */
inline fun KClass<*>.annotate(vararg types: Class<out Annotation>): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] annotated with a set of [Class]. */
fun ClassName.annotate(vararg types: Class<out Annotation>): ClassName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [LambdaTypeName] annotated with a set of [Class]. */
fun LambdaTypeName.annotate(vararg types: Class<out Annotation>): LambdaTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [ParameterizedTypeName] annotated with a set of [Class]. */
fun ParameterizedTypeName.annotate(vararg types: Class<out Annotation>): ParameterizedTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] annotated with a set of [Class]. */
fun TypeName.annotate(vararg types: Class<out Annotation>): TypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeVariableName] annotated with a set of [Class]. */
fun TypeVariableName.annotate(vararg types: Class<out Annotation>): TypeVariableName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [WildcardTypeName] annotated with a set of [Class]. */
fun WildcardTypeName.annotate(vararg types: Class<out Annotation>): WildcardTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] converted from [Type] annotated with a set of [KClass]. */
inline fun Type.annotate(vararg types: KClass<out Annotation>): TypeName =
    asTypeName().annotate(*types)

/** Return a copy of [ClassName] converted from [Class] annotated with a set of [KClass]. */
inline fun Class<*>.annotate(vararg types: KClass<out Annotation>): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] converted from [KClass] annotated with a set of [KClass]. */
inline fun KClass<*>.annotate(vararg types: KClass<out Annotation>): ClassName =
    asClassName().annotate(*types)

/** Return a copy of [ClassName] annotated with a set of [KClass]. */
fun ClassName.annotate(vararg types: KClass<out Annotation>): ClassName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [LambdaTypeName] annotated with a set of [KClass]. */
fun LambdaTypeName.annotate(vararg types: KClass<out Annotation>): LambdaTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [ParameterizedTypeName] annotated with a set of [KClass]. */
fun ParameterizedTypeName.annotate(vararg types: KClass<out Annotation>): ParameterizedTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] annotated with a set of [KClass]. */
fun TypeName.annotate(vararg types: KClass<out Annotation>): TypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeVariableName] annotated with a set of [KClass]. */
fun TypeVariableName.annotate(vararg types: KClass<out Annotation>): TypeVariableName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [WildcardTypeName] annotated with a set of [KClass]. */
fun WildcardTypeName.annotate(vararg types: KClass<out Annotation>): WildcardTypeName =
    copyOf(annotations = types.map { AnnotationSpec.builder(it).build() })

/** Return a copy of [TypeName] converted from [Type] annotated with [T]. */
inline fun <reified T : Annotation> Type.annotate(): TypeName = annotate(T::class)

/** Return a copy of [ClassName] converted from [Class] annotated with [T]. */
inline fun <reified T : Annotation> Class<*>.annotate(): ClassName = annotate(T::class)

/** Return a copy of [ClassName] converted from [KClass] annotated with [T]. */
inline fun <reified T : Annotation> KClass<*>.annotate(): ClassName = annotate(T::class)

/** Return a copy of [ClassName] annotated with [T]. */
inline fun <reified T : Annotation> ClassName.annotate(): ClassName = annotate(T::class)

/** Return a copy of [LambdaTypeName] annotated with [T]. */
inline fun <reified T : Annotation> LambdaTypeName.annotate(): LambdaTypeName = annotate(T::class)

/** Return a copy of [ParameterizedTypeName] annotated with [T]. */
inline fun <reified T : Annotation> ParameterizedTypeName.annotate(): ParameterizedTypeName = annotate(T::class)

/** Return a copy of [TypeName] annotated with [T]. */
inline fun <reified T : Annotation> TypeName.annotate(): TypeName = annotate(T::class)

/** Return a copy of [TypeVariableName] annotated with [T]. */
inline fun <reified T : Annotation> TypeVariableName.annotate(): TypeVariableName = annotate(T::class)

/** Return a copy of [WildcardTypeName] annotated with [T]. */
inline fun <reified T : Annotation> WildcardTypeName.annotate(): WildcardTypeName = annotate(T::class)

internal inline fun ClassName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): ClassName = copy(nullable, annotations, tags)

internal inline fun LambdaTypeName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): LambdaTypeName = copy(nullable, annotations, tags)

internal inline fun ParameterizedTypeName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): ParameterizedTypeName = copy(nullable, annotations, tags)

internal inline fun TypeName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): TypeName = copy(nullable, annotations, tags)

internal inline fun TypeVariableName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): TypeVariableName = copy(nullable, annotations, tags)

internal inline fun WildcardTypeName.copyOf(
    nullable: Boolean = isNullable,
    annotations: List<AnnotationSpec> = this.annotations
): WildcardTypeName = copy(nullable, annotations, tags)
