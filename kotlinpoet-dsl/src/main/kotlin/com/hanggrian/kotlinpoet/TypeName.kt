@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type

public inline fun TypeName.nullable(): TypeName = copy(nullable = true)

public fun TypeName.annotate(vararg annotations: AnnotationSpec): TypeName =
    copy(annotations = annotations.toList())

public inline val Type.name: TypeName get() = asTypeName()
