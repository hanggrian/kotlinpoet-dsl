package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.asClassName
import java.io.File
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

/** From `https://square.github.io/kotlinpoet/`. */
@Suppress("ktlint:standard:property-naming")
class GitHubTest {
    @Test
    fun `Example`() {
        assertThat(
            buildFileSpec("com.example.helloworld", "") {
                val Greeter by types.addingClass {
                    setPrimaryConstructor {
                        parameters.add<String>("name")
                    }
                    properties.add("name", STRING) {
                        setInitializer("name")
                    }
                    functions.add("greet") {
                        appendLine("println(%P)", "Hello, \$name")
                    }
                }
                functions.add("main") {
                    parameters.add<String>("args", VARARG)
                    // FIXME appendLine("%T(args[0]).greet()", Greeter)
                    appendLine("Greeter(args[0]).greet()")
                }
            }.toString(),
        ).isEqualTo(
            """
            package com.example.helloworld

            import kotlin.String

            public class Greeter(
              public val name: String,
            ) {
              public fun greet() {
                println(${'"'}${'"'}${'"'}Hello, ${'$'}name${'"'}${'"'}${'"'})
              }
            }

            public fun main(vararg args: String) {
              Greeter(args[0]).greet()
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Code & Control Flow`() {
        assertThat(
            buildFunSpec("main") {
                returns = UNIT
                appendLine("var total = 0")
                beginControlFlow("for (i in 0 until 10)")
                appendLine("total += i")
                endControlFlow()
            }.toString(),
        ).isEqualTo(
            """
            public fun main() {
              var total = 0
              for (i in 0 until 10) {
                total += i
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("multiply10to20") {
                returns = INT
                appendLine("var result = 1")
                beginControlFlow("for (i in 10 until 20)")
                appendLine("result = result * i")
                endControlFlow()
                appendLine("return result")
            }.toString(),
        ).isEqualTo(
            """
            public fun multiply10to20(): kotlin.Int {
              var result = 1
              for (i in 10 until 20) {
                result = result * i
              }
              return result
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `$S for Strings`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                functions {
                    "slimShady" {
                        setReturns<String>()
                        appendLine("return %S", "slimShady")
                    }
                    "eminem" {
                        setReturns<String>()
                        appendLine("return %S", "eminem")
                    }
                    "marshallMathers" {
                        setReturns<String>()
                        appendLine("return %S", "marshallMathers")
                    }
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              public fun slimShady(): kotlin.String = "slimShady"

              public fun eminem(): kotlin.String = "eminem"

              public fun marshallMathers(): kotlin.String = "marshallMathers"
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `%P for String Templates`() {
        assertThat(
            buildFunSpec("printTotal") {
                returns = STRING
                appendLine("return %P", "Your total is ${'$'}amount")
            }.toString(),
        ).isEqualTo(
            """
            public fun printTotal(): kotlin.String = ${'"'}${'"'}${'"'}Your total is ${'$'}amount${'"'}${'"'}${'"'}

            """.trimIndent(),
        )
        assertThat(
            buildFileSpec("com.example", "Digits") {
                functions.add("print") {
                    parameters.add<IntArray>("digits")
                    val contentToString = MemberName("kotlin.collections", "contentToString")
                    appendLine(
                        "println(%P)",
                        buildCodeBlock {
                            append("These are the digits: \${digits.%M()}", contentToString)
                        },
                    )
                }
            }.toString(),
        ).isEqualTo(
            """
            package com.example

            import kotlin.IntArray
            import kotlin.collections.contentToString

            public fun print(digits: IntArray) {
              println(${'"'}${'"'}${'"'}These are the digits: ${'$'}{digits.contentToString()}${'"'}${'"'}${'"'})
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `$T for Types`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                functions.add("today") {
                    setReturns<Date>()
                    appendLine("return %T()", Date::class)
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              public fun today(): java.util.Date = java.util.Date()
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                functions.add("tomorrow") {
                    val hoverboard = classNamed("com.mattel", "Hoverboard")
                    returns = hoverboard
                    appendLine("return %T()", hoverboard)
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              public fun tomorrow(): com.mattel.Hoverboard = com.mattel.Hoverboard()
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                functions.add("beyond") {
                    val hoverboard = classNamed("com.mattel", "Hoverboard")
                    val arrayList = classNamed("kotlin", "ArrayList").parameterizedBy(hoverboard)
                    val listOfHoverboards = classNamed("kotlin", "List").parameterizedBy(hoverboard)
                    returns = listOfHoverboards
                    appendLine("val result = %T()", arrayList)
                    appendLine("result += %T()", hoverboard)
                    appendLine("result += %T()", hoverboard)
                    appendLine("result += %T()", hoverboard)
                    appendLine("return result")
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              public fun beyond(): kotlin.List<com.mattel.Hoverboard> {
                val result = kotlin.ArrayList<com.mattel.Hoverboard>()
                result += com.mattel.Hoverboard()
                result += com.mattel.Hoverboard()
                result += com.mattel.Hoverboard()
                return result
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                properties {
                    add("java", STRING.nullable(), PRIVATE) {
                        isMutable = true
                        setInitializer("null")
                    }
                    add<String>("kotlin", PRIVATE)
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              private var java: kotlin.String? = null

              private val kotlin: kotlin.String
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `%M for Members`() {
        assertThat(
            buildFileSpec("com.squareup.example", "TacoTest") {
                val createTaco = MemberName("com.squareup.tacos", "createTaco")
                val isVegan = MemberName("com.squareup.tacos", "isVegan")
                functions.add("main") {
                    appendLine("val taco = %M()", createTaco)
                    appendLine("println(taco.%M)", isVegan)
                }
            }.toString(),
        ).isEqualTo(
            """
            package com.squareup.example

            import com.squareup.tacos.createTaco
            import com.squareup.tacos.isVegan

            public fun main() {
              val taco = createTaco()
              println(taco.isVegan)
            }

            """.trimIndent(),
        )
        assertThat(
            buildFileSpec("com.squareup.example", "Test") {
                val createTaco = MemberName("com.squareup.tacos", "createTaco")
                val createCake = MemberName("com.squareup.cakes", "createCake")
                val isTacoVegan = MemberName("com.squareup.tacos", "isVegan")
                val isCakeVegan = MemberName("com.squareup.cakes", "isVegan")
                addAliasedImport(isTacoVegan, "isTacoVegan")
                addAliasedImport(isCakeVegan, "isCakeVegan")
                functions.add("main") {
                    appendLine("val taco = %M()", createTaco)
                    appendLine("val cake = %M()", createCake)
                    appendLine("println(taco.%M)", isTacoVegan)
                    appendLine("println(cake.%M)", isCakeVegan)
                }
            }.toString(),
        ).isEqualTo(
            """
            package com.squareup.example

            import com.squareup.cakes.createCake
            import com.squareup.tacos.createTaco
            import com.squareup.cakes.isVegan as isCakeVegan
            import com.squareup.tacos.isVegan as isTacoVegan

            public fun main() {
              val taco = createTaco()
              val cake = createCake()
              println(taco.isTacoVegan)
              println(cake.isCakeVegan)
            }

            """.trimIndent(),
        )
        assertThat(
            buildFileSpec("com.example", "Test") {
                val taco = ClassName("com.squareup.tacos", "Taco")
                val meat = ClassName("com.squareup.tacos.ingredient", "Meat")
                val iterator = MemberName("com.squareup.tacos.internal", ITERATOR)
                val minusAssign = MemberName("com.squareup.tacos.internal", MINUS_ASSIGN)
                functions.add("makeTacoHealthy") {
                    parameters.add("taco", taco)
                    beginControlFlow("for (ingredient %M taco)", iterator)
                    appendLine("if (ingredient is %T) taco %M ingredient", meat, minusAssign)
                    endControlFlow()
                    appendLine("return taco")
                }
            }.toString(),
        ).isEqualTo(
            """
            package com.example

            import com.squareup.tacos.Taco
            import com.squareup.tacos.`internal`.iterator
            import com.squareup.tacos.`internal`.minusAssign
            import com.squareup.tacos.ingredient.Meat

            public fun makeTacoHealthy(taco: Taco) {
              for (ingredient in taco) {
                if (ingredient is Meat) taco -= ingredient
              }
              return taco
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `$N for Names`() {
        val hexDigit =
            buildFunSpec("hexDigit") {
                parameters.add("i", INT)
                returns = CHAR
                appendLine(
                    "return (if (i < 10) i + '0'.toInt() else i - 10 + 'a'.toInt()).toChar()",
                )
            }
        val byteToHex =
            buildFunSpec("byteToHex") {
                parameters.add("b", INT)
                setReturns<String>()
                appendLine("val result = CharArray(2)")
                appendLine("result[0] = %N((b ushr 4) and 0xf)", hexDigit)
                appendLine("result[1] = %N(b and 0xf)", hexDigit)
                appendLine("return String(result)")
            }
        assertThat("$byteToHex\n$hexDigit")
            .isEqualTo(
                """
                public fun byteToHex(b: kotlin.Int): kotlin.String {
                  val result = CharArray(2)
                  result[0] = hexDigit((b ushr 4) and 0xf)
                  result[1] = hexDigit(b and 0xf)
                  return String(result)
                }

                public fun hexDigit(i: kotlin.Int): kotlin.Char = (if (i < 10) i + '0'.toInt() else i - 10 + 'a'.toInt()).toChar()

                """.trimIndent(),
            )
    }

    @Test
    fun `$L for Literals`() {
        assertThat(
            buildFunSpec("computeRange") {
                returns = INT
                appendLine("var result = 0")
                beginControlFlow("for (i in %L until %L)", 0, 10)
                appendLine("result = result %L i", PLUS_ASSIGN)
                endControlFlow()
                appendLine("return result")
            }.toString(),
        ).isEqualTo(
            """
            public fun computeRange(): kotlin.Int {
              var result = 0
              for (i in 0 until 10) {
                result = result PLUS_ASSIGN i
              }
              return result
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Code block format strings`() {
        assertThat(codeBlockOf("I ate %L %L", 3, "tacos"))
            .isEqualTo(CodeBlock.of("I ate %L %L", 3, "tacos"))
        assertThat(codeBlockOf("I ate %2L %1L", "tacos", 3))
            .isEqualTo(CodeBlock.of("I ate %2L %1L", "tacos", 3))

        val templates = linkedMapOf<String, Any>()
        templates["food"] = "tacos"
        templates["count"] = 3
        assertThat(buildCodeBlock { appendNamed("I ate %count:L %food:L", templates) })
            .isEqualTo(CodeBlock.builder().addNamed("I ate %count:L %food:L", templates).build())
    }

    @Test
    fun `Functions`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(ABSTRACT)
                functions.add("flux") {
                    addModifiers(PROTECTED, ABSTRACT)
                }
            }.toString(),
        ).isEqualTo(
            """
            public abstract class HelloWorld {
              protected abstract fun flux()
            }

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("square") {
                setReceiver<Int>()
                setReturns<Int>()
                appendLine("val s = this * this")
                appendLine("return s")
            }.toString(),
        ).isEqualTo(
            """
            public fun kotlin.Int.square(): kotlin.Int {
              val s = this * this
              return s
            }

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("abs") {
                setReturns<Int>()
                parameters.add<Int>("x")
                appendLine("return if (x < 0) -x else x")
            }.toString(),
        ).isEqualTo(
            """
            public fun abs(x: kotlin.Int): kotlin.Int = if (x < 0) -x else x

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("add") {
                parameters {
                    add<Int>("a")
                    add("b", INT) { setDefaultValue("%L", 0) }
                }
                appendLine("print(\"a + b = \${a + b}\")")
            }.toString(),
        ).isEqualTo(
            """
            public fun add(a: kotlin.Int, b: kotlin.Int = 0) {
              print("a + b = ${'$'}{a + b}")
            }

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("foo") {
                appendLine(
                    """
                    return (100..10000).map·{ number -> number * number }.map·{ number -> number.toString() }.also·{ string -> println(string) }
                    """.trimIndent(),
                )
            }.toString(),
        ).isEqualTo(
            """
            public fun foo(): kotlin.Unit = (100..10000).map { number -> number * number }.map { number -> number.toString() }.also { string -> println(string) }

            """.trimIndent(),
        )
    }

    @Test
    fun `Constructors`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                properties.add<String>("greeting", PRIVATE)
                functions.addConstructor {
                    parameters.add<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              private val greeting: kotlin.String

              public constructor(greeting: kotlin.String) {
                this.greeting = greeting
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                properties.add("greeting", STRING, PRIVATE) {
                    setInitializer("greeting")
                }
                setPrimaryConstructor {
                    parameters.add<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld(
              private val greeting: kotlin.String,
            ) {
              init {
                this.greeting = greeting
              }
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Parameters`() {
        assertThat(
            buildFunSpec("welcomeOverlords") {
                parameters {
                    add("android", STRING) { setDefaultValue("\"pie\"") }
                    add<String>("robot")
                }
            }.toString(),
        ).isEqualTo(
            """
            public fun welcomeOverlords(android: kotlin.String = "pie", robot: kotlin.String) {
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Properties`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                properties {
                    add<String>("android", PRIVATE)
                    add<String>("robot", PRIVATE)
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              private val android: kotlin.String

              private val robot: kotlin.String
            }

            """.trimIndent(),
        )
        assertThat(
            buildPropertySpec("android", STRING, PRIVATE) {
                setInitializer("%S + %L", "Oreo v.", 8.1)
            }.toString(),
        ).isEqualTo(
            """
            private val android: kotlin.String = "Oreo v." + 8.1

            """.trimIndent(),
        )
        assertThat(
            buildPropertySpec("android", STRING) {
                isMutable = true
                setGetter {
                    modifiers.add(INLINE)
                    appendLine("return %S", "foo")
                }
                setSetter {
                    parameters.add("value", STRING)
                }
            }.toString(),
        ).isEqualTo(
            """
            var android: kotlin.String
              inline get() = "foo"
              set(`value`) {
              }

            """.trimIndent(),
        )
    }

    @Test
    fun `Interfaces`() {
        assertThat(
            buildInterfaceTypeSpec("HelloWorld") {
                properties.add<String>("buzz")
                functions.add("beep") { addModifiers(ABSTRACT) }
            }.toString(),
        ).isEqualTo(
            """
            public interface HelloWorld {
              public val buzz: kotlin.String

              public fun beep()
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Enums`() {
        assertThat(
            buildEnumTypeSpec("Roshambo") {
                addEnumConstant("ROCK")
                addEnumConstant("SCISSORS")
                addEnumConstant("PAPER")
            }.toString(),
        ).isEqualTo(
            """
            public enum class Roshambo {
              ROCK,
              SCISSORS,
              PAPER,
            }

            """.trimIndent(),
        )
        assertThat(
            buildEnumTypeSpec("Roshambo") {
                setPrimaryConstructor {
                    parameters.add<String>("handsign")
                }
                enumConstants["ROCK"] =
                    buildAnonymousTypeSpec {
                        addSuperclassConstructorParameter("%S", "fist")
                        functions.add("toString") {
                            addModifiers(OVERRIDE)
                            appendLine("return %S", "avalanche!")
                            setReturns<String>()
                        }
                    }
                addEnumConstant(
                    "SCISSORS",
                    buildAnonymousTypeSpec { addSuperclassConstructorParameter("%S", "peace") },
                )
                addEnumConstant(
                    "PAPER",
                    buildAnonymousTypeSpec { addSuperclassConstructorParameter("%S", "flat") },
                )
                properties.add("handsign", STRING, PRIVATE) {
                    setInitializer("handsign")
                }
            }.toString(),
        ).isEqualTo(
            """
            public enum class Roshambo(
              private val handsign: kotlin.String,
            ) {
              ROCK("fist") {
                override fun toString(): kotlin.String = "avalanche!"
              },
              SCISSORS("peace"),
              PAPER("flat"),
              ;
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Anonymous Inner Classes`() {
        assertThat(
            buildFunSpec("sortByLength") {
                parameters.add("strings", List::class.parameterizedBy(String::class))
                appendLine(
                    "%N.sortedWith(%L)",
                    "strings",
                    buildAnonymousTypeSpec {
                        addSuperinterface(Comparator::class.parameterizedBy(String::class))
                        functions.add("compare") {
                            addModifiers(OVERRIDE)
                            parameters {
                                add<String>("a")
                                add<String>("b")
                            }
                            returns = INT
                            appendLine("return %N.length - %N.length", "a", "b")
                        }
                    },
                )
            }.toString(),
        ).isEqualTo(
            """
            public fun sortByLength(strings: kotlin.collections.List<kotlin.String>) {
              strings.sortedWith(object : java.util.Comparator<kotlin.String> {
                override fun compare(a: kotlin.String, b: kotlin.String): kotlin.Int = a.length - b.length
              })
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Annotations`() {
        val headers = classNamed("com.example", "Headers")
        val logRecord = classNamed("com.example", "LogRecord")
        val logReceipt = classNamed("com.example", "LogReceipt")
        assertThat(
            buildFunSpec("test string equality") {
                annotations.add<Test>()
                appendLine("assertThat(%1S).isEqualTo(%1S)", "foo")
            }.toString(),
        ).isEqualTo(
            """
            @org.junit.Test
            public fun `test string equality`() {
              assertThat("foo").isEqualTo("foo")
            }

            """.trimIndent(),
        )
        assertThat(
            buildFunSpec("recordEvent") {
                addModifiers(ABSTRACT)
                annotations.add(headers) {
                    addMember("accept = %S", "application/json; charset=utf-8")
                    addMember("userAgent = %S", "Square Cash")
                }
                parameters.add("logRecord", logRecord)
                returns = logReceipt
            }.toString(),
        ).isEqualTo(
            """
            @com.example.Headers(
              accept = "application/json; charset=utf-8",
              userAgent = "Square Cash",
            )
            public abstract fun recordEvent(logRecord: com.example.LogRecord): com.example.LogReceipt

            """.trimIndent(),
        )
    }

    @Test
    fun `Type Aliases`() {
        assertEquals(
            """
            package com.example

            import java.io.File
            import kotlin.Boolean
            import kotlin.String
            import kotlin.collections.Map
            import kotlin.collections.Set

            public typealias Word = String

            public typealias FileTable<K> = Map<K, Set<File>>

            public typealias Predicate<T> = (T) -> Boolean

            """.trimIndent(),
            buildFileSpec("com.example", "HelloWorld") {
                val k = "K".generics
                val t = "T".generics
                typeAliases {
                    add<String>("Word")
                    add(
                        "FileTable",
                        Map::class
                            .asClassName()
                            .parameterizedBy(k, Set::class.parameterizedBy(File::class)),
                    ) { typeVariables.add(k) }
                    add(
                        "Predicate",
                        lambdaTypeNamed(t, returns = Boolean::class.name),
                    ) { typeVariables.add(t) }
                }
            }.toString(),
        )
    }

    @Test
    fun `Callable References`() {
        assertThat(
            buildFunSpec("factories") {
                val helloClass = ClassName("com.example.hello", "Hello")
                appendLine("val hello = %L", helloClass.constructorReference())
                appendLine("val world = %L", helloClass.member("world").reference())
                appendLine(
                    "val bye = %L",
                    helloClass.nestedClass("World").member("bye").reference(),
                )
            }.toString(),
        ).isEqualTo(
            """
            public fun factories() {
              val hello = ::com.example.hello.Hello
              val world = com.example.hello.Hello::world
              val bye = com.example.hello.Hello.World::bye
            }

            """.trimIndent(),
        )
    }
}
