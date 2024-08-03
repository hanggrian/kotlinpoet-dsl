[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/kotlinpoet-dsl)](https://app.circleci.com/pipelines/github/hanggrian/kotlinpoet-dsl/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/kotlinpoet-dsl)](https://app.codecov.io/gh/hanggrian/kotlinpoet-dsl/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian/kotlinpoet-dsl)](https://repo1.maven.org/maven2/com/hanggrian/kotlinpoet-dsl/)
[![OpenJDK](https://img.shields.io/badge/jdk-11%2B-informational)](https://openjdk.org/projects/jdk/11/)

# KotlinPoet DSL

Lightweight Kotlin extension of [KotlinPoet](https://github.com/square/kotlinpoet/),
providing Kotlin DSL functionality and other convenient solutions.

- Full of convenient methods to achieve minimum code writing possible.
- Options to invoke DSL. For example, `method("main") { ... }` is as good
  as `methods { "main" { ... } }`. Scroll down for more information.
- Smooth transition, existing KotlinPoet native specs can still be configured
  with DSL.

```kt
buildFileSpec("com.example.helloworld", "HelloWorld") {
    classType("HelloWorld") {
        modifiers(PUBLIC, FINAL)
        methods {
            "main" {
                modifiers(PUBLIC, STATIC)
                returns = UNIT
                parameter("args", STRING.array)
                appendLine("%T.out.println(%S)", System::class, "Hello, KotlinPoet!")
            }
        }
    }
}.writeTo(System.out)
```

## Download

```gradle
repositories {
    mavenCentral()
}
dependencies {
    implementation "com.hendraanggrian:kotlinpoet-dsl:$version"
}
```

## Usage

### Use `T::class` as parameters

`KClass<*>` can now be used as format arguments. There is also inline reified
type function whenever possible.

```kt
buildMethodSpec("sortList") {
    returns = int
    parameters.add(classNamed("java.util", "List").parameterizedBy(hoverboard), "list")
    appendLine("%T.sort(list)", Collections::class)
    appendLine("return list")
}

buildFieldSpec("count", INT) {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class.
These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kt
types.addClass("Car") {
    methods {
        "getWheels" {
            returns = INT
            appendLine("return wheels")
        }
        "setWheels" {
            parameter("wheels", INT)
            appendLine("this.wheels = wheels")
        }
    }
}

types.addClass("Car") {
    method("getWheels") {
        returns = INT
        appendLine("return wheels")
    }
    method("setWheels") {
        paraneter("wheels", INT)
        appendLine("this.wheels = wheels")
    }
}
```

### Property delegation

In spirit of [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#using_kotlin_delegated_properties),
creating a spec can be done by delegating to a property.

```kt
val title by parametering(String::class) {
    annotation<NotNull>
}

val message by parametering(String::class) {
    annotation<Nullable>
}
```

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kt
val myClass: ClassName =
    classNamed("com.example", "MyClass")

val listener: LambdaTypeName =
    STRING.lambdaBy(returns = UNIT)

val memberOfString: MemberTypeName =
    myClass.memberOf("myField")

val pairOfInteger: ParameterizedTypeName =
    Pair::class.name.parameterizedBy(Int::class, Int::class)

val tVariable: TypeVariableName =
    "T".generics

val producerOfCharSequence: WildcardTypeName =
    CharSequence::class.name.producerOf()
```
