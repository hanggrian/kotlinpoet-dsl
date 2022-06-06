package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.genericsBy
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class TypeVariableNameCollectionTest {
    private val list = TypeVariableNameCollection(mutableListOf())

    @Test
    fun add() {
        list.add("A")
        list.add("B", String::class.asTypeName())
        list.add("C", String::class.java)
        list.add("D", String::class)
        list.add("E", listOf(String::class.asTypeName()))
        list.add("F", listOf(String::class.java))
        list.add("G", listOf(String::class))
        assertThat(list).containsExactly(
            "A".genericsBy(),
            "B".genericsBy(String::class.asTypeName()),
            "C".genericsBy(String::class.java),
            "D".genericsBy(String::class),
            "E".genericsBy(listOf(String::class.asTypeName())),
            "F".genericsBy(listOf(String::class.java)),
            "G".genericsBy(listOf(String::class))
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE", "LocalVariableName")
    fun adding() {
        val A by list.adding
        val B by list.adding(String::class.asTypeName())
        val C by list.adding(String::class.java)
        val D by list.adding(String::class)
        val E by list.adding(listOf(String::class.asTypeName()))
        val F by list.adding(listOf(String::class.java))
        val G by list.adding(listOf(String::class))
        assertThat(list).containsExactly(
            "A".genericsBy(),
            "B".genericsBy(String::class.asTypeName()),
            "C".genericsBy(String::class.java),
            "D".genericsBy(String::class),
            "E".genericsBy(listOf(String::class.asTypeName())),
            "F".genericsBy(listOf(String::class.java)),
            "G".genericsBy(listOf(String::class))
        )
    }

    @Test
    fun plusAssign() {
        list += "A"
        assertThat(list).containsExactly(
            "A".genericsBy(),
        )
    }
}