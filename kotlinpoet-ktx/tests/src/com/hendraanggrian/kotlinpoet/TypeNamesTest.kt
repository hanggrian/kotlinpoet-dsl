package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.WildcardTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {

    @Test
    fun asProducerWildcardTypeName() {
        assertEquals(
            WildcardTypeName.producerOf(asClassName<String>()),
            asClassName<String>().asProducerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.producerOf(String::class.java),
            String::class.java.asProducerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.producerOf(String::class),
            String::class.asProducerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.producerOf(String::class),
            asProducerWildcardTypeName<String>()
        )
    }

    @Test
    fun asConsumerWildcardTypeName() {
        assertEquals(
            WildcardTypeName.consumerOf(asClassName<String>()),
            asClassName<String>().asConsumerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.consumerOf(String::class.java),
            String::class.java.asConsumerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.consumerOf(String::class),
            String::class.asConsumerWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.consumerOf(String::class),
            asConsumerWildcardTypeName<String>()
        )
    }
}