package com.hendraanggrian.kotlinpoet

import kotlin.test.Test
import kotlin.test.assertEquals

class MemberTypeNameTest {

    @Test fun memberOf() {
        assertEquals("my.Class.member", "${"my.Class".memberOf("member")}")
        assertEquals("my.Class.member", "${"my".classOf("Class").memberOf("member")}")
        assertEquals("java.lang.String.member", "${String::class.java.memberOf("member")}")
        assertEquals("kotlin.String.member", "${String::class.memberOf("member")}")
    }
}