package com.hendraanggrian.kotlinpoet

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Property delegate accessor when configuring [com.hendraanggrian.javapoet.collections] with `by`
 * keyword.
 */
class SpecDelegateProvider<T>(private val getSpec: (String) -> T) {
    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, T> {
        val spec = getSpec(property.name)
        return ReadOnlyProperty { _, _ -> spec }
    }
}
