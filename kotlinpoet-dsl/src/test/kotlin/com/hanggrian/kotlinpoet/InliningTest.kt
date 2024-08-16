package com.hanggrian.kotlinpoet

/**
 * Function without inline should not be able to assign final global variable. For example, code
 * below should fail.
 *
 * ```
 * val HelloWorld by types.addingClass {
 *     variable = 0
 * }
 * ```
 */
class InliningTest {
    val variable: Int

    init {
        buildFileSpec("com.example", "HelloWorld") {
            types.addClass("HelloWorld") {
                variable = 0
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            InliningTest()
        }
    }
}
