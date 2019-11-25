[![bintray](https://img.shields.io/badge/bintray-maven-brightgreen.svg)](https://bintray.com/hendraanggrian/maven)
[![download](https://api.bintray.com/packages/hendraanggrian/maven/kotlinpoet-ktx/images/download.svg)](https://bintray.com/hendraanggrian/maven/kotlinpoet-ktx/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/kotlinpoet-ktx.svg)](https://travis-ci.com/hendraanggrian/kotlinpoet-ktx)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

KotlinPoet KTX
==============
Lightweight Kotlin extension of [KotlinPoet], providing Kotlin DSL functionality and other convenient solutions. 

 * Full of convenient methods to achieve minimum code writing possible.
 * Options to invoke DSL. For example, `methods.add("main") { ... }` is as good as `methods { "main" { ... } }`. Scroll down for more information.
 * Smooth transition, existing KotlinPoet native specs can still be configured with DSL.

```kotlin
buildFile("com.example.helloworld", "HelloWorld") {
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
    jcenter()
}

dependencies {
    implementation "com.hendraanggrian:kotlinpoet-ktx:$version"
}
```

Usage
-----

### Use `T::class` as parameters
`KClass<*>` can now be used as format arguments. There is also inline reified type function whenever possible.

```kotlin
buildMethod("sortList") {
    returns = int
    parameters.add(classNameOf("java.util", "List").parameterizedBy(hoverboard), "list")
    appendln("%T.sort(list)", Collections::class)
    appendln("return list")
}

buildField<Int>("count") {
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

### Type names extensions
Top-level creators of `TypeName` and all its subclasses.

```kotlin
val hoverboard = classNameOf("com.mattel", "Hoverboard")
val list = classNameOf("java.util", "List")
val listOfHoverboards = list.parameterizedBy(hoverboard)
```

License
-------
    Copyright 2019 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[KotlinPoet]: https://github.com/square/kotlinpoet