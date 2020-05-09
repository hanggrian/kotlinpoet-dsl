package com.hendraanggrian.kotlinpoet

/** Field deprecation message. */
internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

internal inline fun <T> Iterable<T>.manualAddAll(add: (T) -> Unit): Boolean {
    var added = false
    forEach {
        added = true
        add(it)
    }
    return added
}
