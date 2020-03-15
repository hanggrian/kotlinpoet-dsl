package com.hendraanggrian.kotlinpoet.internal

import kotlin.test.assertEquals

fun <T> assertEqualsAll(expected: T, vararg actual: T, message: String? = null) =
    actual.forEachIndexed { index, t -> assertEquals(expected, t, "$message [$index]") }