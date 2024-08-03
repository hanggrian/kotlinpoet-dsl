package com.hanggrian.kotlinpoet

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** Property delegate accessor when configuring with `by` keyword. */
public class SpecDelegateProvider<T>(private val getSpec: (String) -> T) {
    public operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>,
    ): ReadOnlyProperty<Any?, T> {
        val spec = getSpec(property.name)
        return ReadOnlyProperty { _, _ -> spec }
    }
}
