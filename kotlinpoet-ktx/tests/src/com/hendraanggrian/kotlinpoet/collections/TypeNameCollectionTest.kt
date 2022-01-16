package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeNameCollectionTest {

    private val map = TypeNameCollection(mutableMapOf())

    @Test
    fun test() {
        map[CHAR] = null
        map[Double::class.java] = null
        map[Boolean::class] = null
        map.add<String>()
        Truth.assertThat(map.keys).containsExactly(
            CHAR,
            Double::class.java.asTypeName(),
            Boolean::class.asTypeName(),
            String::class.asTypeName()
        )
    }
}