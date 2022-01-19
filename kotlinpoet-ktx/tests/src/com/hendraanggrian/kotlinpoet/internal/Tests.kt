package com.hendraanggrian.kotlinpoet.internal

import kotlin.test.assertEquals

annotation class Annotation1
annotation class Annotation2
annotation class Annotation3
annotation class Annotation4

class Property1
class Property2
class Property3
class Property4

class Parameter1
class Parameter2
class Parameter3
class Parameter4

class TypeAlias1
class TypeAlias2
class TypeAlias3
class TypeAlias4

fun <T> assertEqualsAll(expected: T, vararg actual: T, message: String? = null) =
    actual.forEachIndexed { index, t -> assertEquals(expected, t, "$message [$index]") }