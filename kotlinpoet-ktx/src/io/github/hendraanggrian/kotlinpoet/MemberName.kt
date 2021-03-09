@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import kotlin.reflect.KClass

/** Returns a [MemberName] using package and target name. */
inline fun String.memberOf(simpleName: String): MemberName = MemberName(this, simpleName)

/** Returns a [MemberName] using enclosing [ClassName] and target name. */
inline fun ClassName.memberOf(simpleName: String): MemberName = MemberName.run { member(simpleName) }

/** Returns a [MemberName] using enclosing [Class] and target name. */
inline fun Class<*>.memberOf(simpleName: String): MemberName = MemberName.run { member(simpleName) }

/** Returns a [MemberName] using enclosing [KClass] and target name. */
inline fun KClass<*>.memberOf(simpleName: String): MemberName = MemberName.run { member(simpleName) }
