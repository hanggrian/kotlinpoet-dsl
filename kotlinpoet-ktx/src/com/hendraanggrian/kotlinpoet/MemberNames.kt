package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import kotlin.reflect.KClass

/** Returns a [MemberName], applying `simpleName` to `this`.  */
fun ClassName.member(simpleName: String): MemberName =
    MemberName.run { member(simpleName) }

/** Returns a [MemberName], applying `simpleName` to `this`.  */
fun Class<*>.member(simpleName: String): MemberName =
    MemberName.run { member(simpleName) }

/** Returns a [MemberName], applying `simpleName` to `this`.  */
fun KClass<*>.member(simpleName: String): MemberName =
    MemberName.run { member(simpleName) }
