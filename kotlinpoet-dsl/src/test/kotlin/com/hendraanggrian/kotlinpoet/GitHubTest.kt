package com.hendraanggrian.kotlinpoet

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
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

/** From `https://square.github.io/kotlinpoet/`. */
class GitHubTest {

    @Test
    fun `Example`() {
        assertEquals(
            """
                package com.example.helloworld

                import kotlin.String
                import kotlin.Unit

                public class Greeter(
                  public val name: String,
                ) {
                  public fun greet(): Unit {
                    println(${'"'}${'"'}${'"'}Hello, ${'$'}name${'"'}${'"'}${'"'})
                  }
                }
                
                public fun main(vararg args: String): Unit {
                  Greeter(args[0]).greet()
                }
                
            """.trimIndent(),
            buildFileSpec("com.example.helloworld", "") {
                val Greeter by types.addingClass {
                    primaryConstructor {
                        parameters.add<String>("name")
                    }
                    properties.add<String>("name") {
                        initializer("name")
                    }
                    functions.add("greet") {
                        appendLine("println(%P)", "Hello, \$name")
                    }
                }
                functions.add("main") {
                    parameters.add<String>("args", VARARG)
                    // FIXME: appendLine("%T(args[0]).greet()", Greeter)
                    appendLine("Greeter(args[0]).greet()")
                }
            }.toString()
        )
    }

    @Test
    fun `Code & Control Flow`() {
        assertEquals(
            """
                public fun main(): kotlin.Unit {
                  var total = 0
                  for (i in 0 until 10) {
                    total += i
                  }
                }
            
            """.trimIndent(),
            buildFunSpec("main") {
                returns = UNIT
                appendLine("var total = 0")
                appendControlFlow("for (i in 0 until 10)") {
                    appendLine("total += i")
                }
            }.toString()
        )
        assertEquals(
            """
                public fun multiply10to20(): kotlin.Int {
                  var result = 1
                  for (i in 10 until 20) {
                    result = result * i
                  }
                  return result
                }

            """.trimIndent(),
            buildFunSpec("multiply10to20") {
                returns = INT
                appendLine("var result = 1")
                appendControlFlow("for (i in 10 until 20)") {
                    appendLine("result = result * i")
                }
                appendLine("return result")
            }.toString()
        )
    }

    @Test
    fun `$S for Strings`() {
        assertEquals(
            """
                public class HelloWorld {
                  public fun slimShady(): kotlin.String = "slimShady"
                
                  public fun eminem(): kotlin.String = "eminem"
                
                  public fun marshallMathers(): kotlin.String = "marshallMathers"
                }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                functions {
                    "slimShady" {
                        returns<String>()
                        appendLine("return %S", "slimShady")
                    }
                    "eminem" {
                        returns<String>()
                        appendLine("return %S", "eminem")
                    }
                    "marshallMathers" {
                        returns<String>()
                        appendLine("return %S", "marshallMathers")
                    }
                }
            }.toString()
        )
    }

    @Test
    fun `%P for String Templates`() {
        assertEquals(
            """
                public fun printTotal(): kotlin.String = ${'"'}${'"'}${'"'}Your total is ${'$'}amount${'"'}${'"'}${'"'}
                
            """.trimIndent(),
            buildFunSpec("printTotal") {
                returns = STRING
                appendLine("return %P", "Your total is ${'$'}amount")
            }.toString()
        )
        assertEquals(
            """
                package com.example

                import kotlin.IntArray
                import kotlin.Unit
                import kotlin.collections.contentToString
                
                public fun print(digits: IntArray): Unit {
                  println(${'"'}${'"'}${'"'}These are the digits: ${'$'}{digits.contentToString()}${'"'}${'"'}${'"'})
                }
                
            """.trimIndent(),
            buildFileSpec("com.example", "Digits") {
                functions.add("print") {
                    parameters.add<IntArray>("digits")
                    val contentToString = MemberName("kotlin.collections", "contentToString")
                    appendLine(
                        "println(%P)",
                        buildCodeBlock {
                            append("These are the digits: \${digits.%M()}", contentToString)
                        }
                    )
                }
            }.toString()
        )
    }

    @Test
    fun `$T for Types`() {
        assertEquals(
            """
                public class HelloWorld {
                  public fun today(): java.util.Date = java.util.Date()
                }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                functions.add("today") {
                    returns<Date>()
                    appendLine("return %T()", Date::class)
                }
            }.toString()
        )
        assertEquals(
            """
                public class HelloWorld {
                  public fun tomorrow(): com.mattel.Hoverboard = com.mattel.Hoverboard()
                }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                functions.add("tomorrow") {
                    val hoverboard = classNameOf("com.mattel", "Hoverboard")
                    returns = hoverboard
                    appendLine("return %T()", hoverboard)
                }
            }.toString()
        )
        assertEquals(
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
            buildClassTypeSpec("HelloWorld") {
                functions.add("beyond") {
                    val hoverboard = classNameOf("com.mattel", "Hoverboard")
                    val arrayList = classNameOf("kotlin", "ArrayList").parameterizedBy(hoverboard)
                    val listOfHoverboards = classNameOf("kotlin", "List").parameterizedBy(hoverboard)
                    returns = listOfHoverboards
                    appendLine("val result = %T()", arrayList)
                    appendLine("result += %T()", hoverboard)
                    appendLine("result += %T()", hoverboard)
                    appendLine("result += %T()", hoverboard)
                    appendLine("return result")
                }
            }.toString()
        )
        assertEquals(
            """
                public class HelloWorld {
                  private var java: kotlin.String? = null
                
                  private val kotlin: kotlin.String
                }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                properties {
                    "java"(String::class.asTypeName().copy(nullable = true), PRIVATE) {
                        isMutable = true
                        initializer("null")
                    }
                    add<String>("kotlin", PRIVATE)
                }
            }.toString()
        )
    }

    @Test
    fun `%M for Members`() {
        assertEquals(
            """
                package com.squareup.example

                import com.squareup.tacos.createTaco
                import com.squareup.tacos.isVegan
                import kotlin.Unit
    
                public fun main(): Unit {
                  val taco = createTaco()
                  println(taco.isVegan)
                }
                
            """.trimIndent(),
            buildFileSpec("com.squareup.example", "TacoTest") {
                val createTaco = MemberName("com.squareup.tacos", "createTaco")
                val isVegan = MemberName("com.squareup.tacos", "isVegan")
                functions.add("main") {
                    appendLine("val taco = %M()", createTaco)
                    appendLine("println(taco.%M)", isVegan)
                }
            }.toString()
        )
        assertEquals(
            """
                package com.squareup.example

                import com.squareup.cakes.createCake
                import com.squareup.tacos.createTaco
                import kotlin.Unit
                import com.squareup.cakes.isVegan as isCakeVegan
                import com.squareup.tacos.isVegan as isTacoVegan

                public fun main(): Unit {
                  val taco = createTaco()
                  val cake = createCake()
                  println(taco.isTacoVegan)
                  println(cake.isCakeVegan)
                }
                
            """.trimIndent(),
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
            }.toString()
        )
        assertEquals(
            """
                package com.example

                import com.squareup.tacos.Taco
                import com.squareup.tacos.`internal`.iterator
                import com.squareup.tacos.`internal`.minusAssign
                import com.squareup.tacos.ingredient.Meat
                import kotlin.Unit

                public fun makeTacoHealthy(taco: Taco): Unit {
                  for (ingredient in taco) {
                    if (ingredient is Meat) taco -= ingredient
                  }
                  return taco
                }
                
            """.trimIndent(),
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
            }.toString()
        )
    }

    @Test
    fun `$N for Names`() {
        val hexDigit by buildingFunSpec {
            parameters.add("i", INT)
            returns = CHAR
            appendLine("return (if (i < 10) i + '0'.toInt() else i - 10 + 'a'.toInt()).toChar()")
        }
        val byteToHex by buildingFunSpec {
            parameters.add("b", INT)
            returns<String>()
            appendLine("val result = CharArray(2)")
            appendLine("result[0] = %N((b ushr 4) and 0xf)", hexDigit)
            appendLine("result[1] = %N(b and 0xf)", hexDigit)
            appendLine("return String(result)")
        }
        assertEquals(
            """
                public fun byteToHex(b: kotlin.Int): kotlin.String {
                  val result = CharArray(2)
                  result[0] = hexDigit((b ushr 4) and 0xf)
                  result[1] = hexDigit(b and 0xf)
                  return String(result)
                }

                public fun hexDigit(i: kotlin.Int): kotlin.Char = (if (i < 10) i + '0'.toInt() else i - 10 + 'a'.toInt()).toChar()

            """.trimIndent(),
            "$byteToHex\n$hexDigit"
        )
    }

    @Test
    fun `$L for Literals`() {
        assertEquals(
            """
                public fun computeRange(): kotlin.Int {
                  var result = 0
                  for (i in 0 until 10) {
                    result = result PLUS_ASSIGN i
                  }
                  return result
                }

            """.trimIndent(),
            buildFunSpec("computeRange") {
                returns = INT
                appendLine("var result = 0")
                appendControlFlow("for (i in %L until %L)", 0, 10) {
                    appendLine("result = result %L i", PLUS_ASSIGN)
                }
                appendLine("return result")
            }.toString()
        )
    }

    @Test
    fun `Code block format strings`() {
        assertEquals(
            CodeBlock.of("I ate %L %L", 3, "tacos"),
            codeBlockOf("I ate %L %L", 3, "tacos")
        )
        assertEquals(
            CodeBlock.of("I ate %2L %1L", "tacos", 3),
            codeBlockOf("I ate %2L %1L", "tacos", 3)
        )
        val map = linkedMapOf<String, Any>()
        map["food"] = "tacos"
        map["count"] = 3
        assertEquals(
            CodeBlock.builder().addNamed("I ate %count:L %food:L", map).build(),
            buildCodeBlock { appendNamed("I ate %count:L %food:L", map) }
        )
    }

    @Test
    fun `Functions`() {
        assertEquals(
            """
                public abstract class HelloWorld {
                  protected abstract fun flux(): kotlin.Unit
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                addModifiers(ABSTRACT)
                functions.add("flux") {
                    addModifiers(PROTECTED, ABSTRACT)
                }
            }.toString()
        )
        assertEquals(
            """
                public fun kotlin.Int.square(): kotlin.Int {
                  val s = this * this
                  return s
                }
                
            """.trimIndent(),
            buildFunSpec("square") {
                receiver<Int>()
                returns<Int>()
                appendLine("val s = this * this")
                appendLine("return s")
            }.toString()
        )
        assertEquals(
            """
                public fun abs(x: kotlin.Int): kotlin.Int = if (x < 0) -x else x
                
            """.trimIndent(),
            buildFunSpec("abs") {
                returns<Int>()
                parameters.add<Int>("x")
                appendLine("return if (x < 0) -x else x")
            }.toString()
        )
        assertEquals(
            """
                public fun add(a: kotlin.Int, b: kotlin.Int = 0): kotlin.Unit {
                  print("a + b = ${'$'}{a + b}")
                }
                
            """.trimIndent(),
            buildFunSpec("add") {
                parameters {
                    add<Int>("a")
                    add<Int>("b") { defaultValue("%L", 0) }
                }
                appendLine("print(\"a + b = \${a + b}\")")
            }.toString()
        )
        assertEquals(
            """
                public fun foo() = (100..10000).map { number -> number * number }.map { number -> number.toString() }.also { string -> println(string) }
                
            """.trimIndent(),
            buildFunSpec("foo") {
                appendLine("return (100..10000).map·{ number -> number * number }.map·{ number -> number.toString() }.also·{ string -> println(string) }")
            }.toString()
        )
    }

    @Test
    fun `Constructors`() {
        assertEquals(
            """
                public class HelloWorld {
                  private val greeting: kotlin.String
                
                  public constructor(greeting: kotlin.String) {
                    this.greeting = greeting
                  }
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                properties.add<String>("greeting", PRIVATE)
                functions.addConstructor {
                    parameters.add<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString()
        )
        assertEquals(
            """
                public class HelloWorld(
                  private val greeting: kotlin.String,
                ) {
                  init {
                    this.greeting = greeting
                  }
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                properties.add<String>("greeting", PRIVATE) {
                    initializer("greeting")
                }
                primaryConstructor {
                    parameters.add<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString()
        )
    }

    @Test
    fun `Parameters`() {
        assertEquals(
            """
                public fun welcomeOverlords(android: kotlin.String = "pie", robot: kotlin.String): kotlin.Unit {
                }

            """.trimIndent(),
            buildFunSpec("welcomeOverlords") {
                parameters {
                    add<String>("android") { defaultValue("\"pie\"") }
                    add<String>("robot")
                }
            }.toString()
        )
    }

    @Test
    fun `Properties`() {
        assertEquals(
            """
                public class HelloWorld {
                  private val android: kotlin.String
                
                  private val robot: kotlin.String
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                properties {
                    add<String>("android", PRIVATE)
                    add<String>("robot", PRIVATE)
                }
            }.toString()
        )
        assertEquals(
            """
                private val android: kotlin.String = "Oreo v." + 8.1
                
            """.trimIndent(),
            buildPropertySpec<String>("android", PRIVATE) {
                initializer("%S + %L", "Oreo v.", 8.1)
            }.toString()
        )
        assertEquals(
            """
                var android: kotlin.String
                  inline get() = "foo"
                  set(`value`) {
                  }
                
            """.trimIndent(),
            buildPropertySpec<String>("android") {
                isMutable = true
                getter {
                    modifiers.add(INLINE)
                    appendLine("return %S", "foo")
                }
                setter {
                    parameters.add<String>("value")
                }
            }.toString()
        )
    }

    @Test
    fun `Interfaces`() {
        assertEquals(
            """
                public interface HelloWorld {
                  public val buzz: kotlin.String
                
                  public fun beep(): kotlin.Unit
                }
                
            """.trimIndent(),
            buildInterfaceTypeSpec("HelloWorld") {
                properties.add<String>("buzz")
                functions.add("beep") { addModifiers(ABSTRACT) }
            }.toString()
        )
    }

    @Test
    fun `Enums`() {
        assertEquals(
            """
                public enum class Roshambo {
                  ROCK,
                  SCISSORS,
                  PAPER,
                }
                
            """.trimIndent(),
            buildEnumTypeSpec("Roshambo") {
                enumConstants {
                    put("ROCK")
                    put("SCISSORS")
                    put("PAPER")
                }
            }.toString()
        )
        assertEquals(
            """
                public enum class Roshambo(
                  private val handsign: kotlin.String,
                ) {
                  ROCK("fist") {
                    public override fun toString(): kotlin.String = "avalanche!"
                  },
                  SCISSORS("peace"),
                  PAPER("flat"),
                  ;
                }
                
            """.trimIndent(),
            buildEnumTypeSpec("Roshambo") {
                primaryConstructor {
                    parameters.add<String>("handsign")
                }
                enumConstants {
                    "ROCK"("%S", "fist") {
                        functions.add("toString") {
                            addModifiers(OVERRIDE)
                            appendLine("return %S", "avalanche!")
                            returns<String>()
                        }
                    }
                    put("SCISSORS", "%S", "peace")
                    put("PAPER", "%S", "flat")
                }
                properties.add<String>("handsign", PRIVATE) {
                    initializer("handsign")
                }
            }.toString()
        )
    }

    @Test
    fun `Anonymous Inner Classes`() {
        assertEquals(
            """
                public fun sortByLength(strings: kotlin.collections.List<kotlin.String>): kotlin.Unit {
                  strings.sortedWith(object : java.util.Comparator<kotlin.String> {
                    public override fun compare(a: kotlin.String, b: kotlin.String): kotlin.Int = a.length - b.length
                  })
                }
                
            """.trimIndent(),
            buildFunSpec("sortByLength") {
                parameters.add("strings", List::class.parameterizedBy(String::class))
                appendLine(
                    "%N.sortedWith(%L)",
                    "strings",
                    buildAnonymousTypeSpec {
                        superinterfaces += Comparator::class.parameterizedBy(String::class)
                        functions.add("compare") {
                            addModifiers(OVERRIDE)
                            parameters {
                                add<String>("a")
                                add<String>("b")
                            }
                            returns = INT
                            appendLine("return %N.length - %N.length", "a", "b")
                        }
                    }
                )
            }.toString()
        )
    }

    @Test
    fun `Annotations`() {
        val headers = classNameOf("com.example", "Headers")
        val logRecord = classNameOf("com.example", "LogRecord")
        val logReceipt = classNameOf("com.example", "LogReceipt")
        assertEquals(
            """
                @org.junit.Test
                public fun `test string equality`(): kotlin.Unit {
                  assertThat("foo").isEqualTo("foo")
                }
                
            """.trimIndent(),
            buildFunSpec("test string equality") {
                annotations.add<Test>()
                appendLine("assertThat(%1S).isEqualTo(%1S)", "foo")
            }.toString()
        )
        assertEquals(
            """
                @com.example.Headers(
                  accept = "application/json; charset=utf-8",
                  userAgent = "Square Cash",
                )
                public abstract fun recordEvent(logRecord: com.example.LogRecord): com.example.LogReceipt
                
            """.trimIndent(),
            buildFunSpec("recordEvent") {
                addModifiers(ABSTRACT)
                annotations.add(headers) {
                    addMember("accept = %S", "application/json; charset=utf-8")
                    addMember("userAgent = %S", "Square Cash")
                }
                parameters.add("logRecord", logRecord)
                returns = logReceipt
            }.toString()
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
                val k = "K".genericsBy()
                val t = "T".genericsBy()
                typeAliases {
                    add<String>("Word")
                    add(
                        "FileTable",
                        Map::class.asClassName().parameterizedBy(k, Set::class.parameterizedBy(File::class))
                    ) { typeVariables.add(k) }
                    add("Predicate", Boolean::class.asClassName().lambdaBy(t)) { typeVariables.add(t) }
                }
            }.toString()
        )
    }

    @Test
    fun `Callable References`() {
        assertEquals(
            """
                public fun factories(): kotlin.Unit {
                  val hello = ::com.example.hello.Hello
                  val world = com.example.hello.Hello::world
                  val bye = com.example.hello.Hello.World::bye
                }
                
            """.trimIndent(),
            buildFunSpec("factories") {
                val helloClass = ClassName("com.example.hello", "Hello")
                appendLine("val hello = %L", helloClass.constructorReference())
                appendLine("val world = %L", helloClass.member("world").reference())
                appendLine("val bye = %L", helloClass.nestedClass("World").member("bye").reference())
            }.toString()
        )
    }
}
