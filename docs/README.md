[![version](https://img.shields.io/maven-central/v/com.hendraanggrian/kotlinpoet-ktx)](https://search.maven.org/artifact/com.hendraanggrian/kotlinpoet-ktx)
[![build](https://img.shields.io/travis/com/hendraanggrian/kotlinpoet-ktx)](https://travis-ci.com/hendraanggrian/kotlinpoet-ktx)
[![analysis](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081)](https://ktlint.github.io)

KotlinPoet KTX
==============

Lightweight Kotlin extension of [KotlinPoet](https://github.com/square/kotlinpoet),
providing Kotlin DSL functionality and other convenient solutions.
* Full of convenient methods to achieve minimum code writing possible.
* Options to invoke DSL. For example, `methods.add("main") { ... }` is as good as `methods { "main" { ... } }`. Scroll down for more information.
* Smooth transition, existing KotlinPoet native specs can still be configured with DSL.

```kotlin
buildFileSpec("com.example.helloworld", "HelloWorld") {
    addClass("HelloWorld") {
        addModifiers(KModifier.PUBLIC, KModifier.FINAL)
        methods {
            "main" {
                addModifiers(KModifier.PUBLIC, KModifier.STATIC)
                returns = UNIT
                parameters.add<Array<String>>("args")
                appendln("%T.out.println(%S)", System::class, "Hello, KotlinPoet!")
            }
        }
    }
}.writeTo(System.out)
```

Download
--------

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "com.hendraanggrian:kotlinpoet-ktx:$version"
}
```

Snapshots of the development version are available in [Sonatype's snapshots repository](https://s01.oss.sonatype.org/content/repositories/snapshots/).

Usage
-----

### Use `T::class` as parameters

`KClass<*>` can now be used as format arguments. There is also inline reified type function whenever possible.

```kotlin
buildMethodSpec("sortList") {
    returns = int
    parameters.add(classNameOf("java.util", "List").parameterizedBy(hoverboard), "list")
    appendln("%T.sort(list)", Collections::class)
    appendln("return list")
}

buildFieldSpec<Int>("count") {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class. These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kotlin
addClass("Car") {
    annotations {
        SuppressWarnings::class {
            members {
                "value" {
                    add("deprecation")
                }
            }
        }
    }
    fields {
        "wheels"(int) {
            initializer = "4"
        }
    }
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

addClass("Car") {
    annotations.add<SuppressWarnings> {
        members.add("value", "deprecation")
    }
    fields.add("wheels", int) {
        initializer = "4"
    }
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

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kotlin
val myClass: ClassName = "com.example".classOf("MyClass")
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
