package com.hendraanggrian.kotlinpoet

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** Property delegate accessor when configuring [com.hendraanggrian.javapoet.collections] with `by` keyword. */
interface SpecLoader<T> {

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, T>
}
