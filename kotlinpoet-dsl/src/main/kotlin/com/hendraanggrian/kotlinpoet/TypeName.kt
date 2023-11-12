@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName

inline fun TypeName.nullable(): TypeName = copy(nullable = true)

fun TypeName.annotate(vararg annotations: AnnotationSpec): TypeName =
    copy(annotations = annotations.toList())
