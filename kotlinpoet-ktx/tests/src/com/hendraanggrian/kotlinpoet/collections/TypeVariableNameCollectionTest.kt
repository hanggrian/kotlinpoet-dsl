package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth
import com.hendraanggrian.kotlinpoet.genericsBy
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeVariableNameCollectionTest {

    private val list = TypeVariableNameCollection(mutableListOf())

    @Test
    fun add() {
        list.add("Q")
        list.add("R", String::class.asTypeName())
        list.add("S", String::class.java)
        list.add("T", String::class)
        Truth.assertThat(list).containsExactly(
            "Q".genericsBy(),
            "R".genericsBy(String::class.asTypeName()),
            "S".genericsBy(String::class.java),
            "T".genericsBy(String::class)
        )
    }

    @Test
    fun plusAssign() {
        list += "Q"
        Truth.assertThat(list).containsExactly(
            "Q".genericsBy(),
        )
    }
}