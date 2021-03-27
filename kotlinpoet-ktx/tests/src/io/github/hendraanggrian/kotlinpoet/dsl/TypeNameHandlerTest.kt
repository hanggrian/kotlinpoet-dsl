package io.github.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeNameHandlerTest {
    private val map = TypeNameHandler(mutableMapOf())

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