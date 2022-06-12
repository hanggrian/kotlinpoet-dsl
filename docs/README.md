[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian/kotlinpoet-dsl)](https://search.maven.org/artifact/com.hendraanggrian/kotlinpoet-dsl)
[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/kotlinpoet-dsl)](https://travis-ci.com/github/hendraanggrian/kotlinpoet-dsl)
[![OpenJDK](https://img.shields.io/badge/JDK-1.8+-informational)](https://openjdk.java.net/projects/jdk8)

# KotlinPoet DSL Primer

Lightweight Kotlin extension of [KotlinPoet](https://github.com/square/kotlinpoet),
providing Kotlin DSL functionality and other convenient solutions.

- Full of convenient methods to achieve minimum code writing possible.
- Options to invoke DSL. For example, `methods.add("main") { ... }` is as good as `methods { "main" { ... } }`. Scroll down for more information.
- Smooth transition, existing KotlinPoet native specs can still be configured with DSL.

```kotlin
buildFileSpec("com.example.helloworld", "HelloWorld") {
    addClass("HelloWorld") {
        addModifiers(KModifier.PUBLIC, KModifier.FINAL)
        methods {
            "main" {
                addModifiers(KModifier.PUBLIC, KModifier.STATIC)
                returns = UNIT
                parameters.add<Array<String>>("args")
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

Snapshots of the development version are available in [Sonatype's snapshots repository](https://s01.oss.sonatype.org/content/repositories/snapshots).

## Usage

### Use `T::class` as parameters

`KClass<*>` can now be used as format arguments. There is also inline reified type function whenever possible.

```kotlin
buildMethodSpec("sortList") {
    returns = int
    parameters.add(classNameOf("java.util", "List").parameterizedBy(hoverboard), "list")
    appendLine("%T.sort(list)", Collections::class)
    appendLine("return list")
}

buildFieldSpec<Int>("count") {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class. These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kotlin
types.addClass("Car") {
    methods {
        "getWheels" {
            returns = int
            statements {
                add("return wheels")
            }
        }
        "setWheels" {
            parameters {
                add(int, "wheels")
            }
            statements {
                add("this.wheels = wheels")
            }
        }
    }
}

types.addClass("Car") {
    methods.add("getWheels") {
        returns = int
        statements.add("return wheels")
    }
    methods.add("setWheels") {
        parameters["wheels"] = int
        statements.add("this.wheels = wheels")
    }
}
```

### Property delegation

In spirit of [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#using_kotlin_delegated_properties), creating a spec can be done by delegating to a property.

```kotlin
val title by buildingParameterSpec(String::class) {
    annotations.add<NotNull>
}

val message by parameters.adding(String::class) {
    annotations.add<Nullable>
}
```

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kotlin
val myClass: ClassName = classOf("com.example", "MyClass")
val listener: LambdaTypeName = null.lambdaBy(returnType = "kotlin".classOf("Unit"))
val memberOfString: MemberTypeName = myClass.memberOf("myField")
val pairOfInteger: ParameterizedTypeName = "kotlin".classOf("Pair").parameterizedBy(Int::class, Int::class)
val tVariable: TypeVariableName = "T".typeVarOf()
val producerOfCharSequence: WildcardTypeName = "kotlin".classOf("CharSequence").producerOf()
```

If you have access to those types, they can also be strongly-typed.

```kotlin
val myClass = com.example.MyClass.asClassName()
val listener = null.lambdaBy(returnType = Unit::class)
val pairOfInteger = parameterizedTypeNameOf<android.util.Pair>(Int::class, Int::class)
val subtypeOfCharSequence = wildcardTypeNameProducerOf<kotlin.CharSequence>()
```
