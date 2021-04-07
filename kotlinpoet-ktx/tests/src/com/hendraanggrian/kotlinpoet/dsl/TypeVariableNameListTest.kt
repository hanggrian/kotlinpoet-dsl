package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth
import com.squareup.kotlinpoet.asTypeName
import com.hendraanggrian.kotlinpoet.typeVarBy
import com.hendraanggrian.kotlinpoet.typeVarOf
import kotlin.test.Test

class TypeVariableNameListTest {
    private val list = TypeVariableNameHandler(mutableListOf())

    @Test
    fun test() {
        list += "Q"
        list.add("R", String::class.asTypeName())
        list.add("S", String::class.java)
        list.add("T", String::class)
        Truth.assertThat(list).containsExactly(
            "Q".typeVarOf(),
            "R".typeVarBy(String::class.asTypeName()),
            "S".typeVarBy(String::class.java),
            "T".typeVarBy(String::class)
        )
    }
}