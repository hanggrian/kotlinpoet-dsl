package com.example

import com.hendraanggrian.kotlinpoet.ABSTRACT
import com.hendraanggrian.kotlinpoet.buildFileSpec
import com.hendraanggrian.kotlinpoet.classType
import com.hendraanggrian.kotlinpoet.functions
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import java.nio.file.Paths

class VehicleWriter {
    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private val VEHICLE_NAME = ClassName(PACKAGE_NAME, "Vehicle")
        private const val SOURCE_PATH = "sample/src/main/kotlin"

        @JvmStatic
        fun main(args: Array<String>) {
            val writer = VehicleWriter()
            writer.prepare()
            writer.write("Bike", 2)
            writer.write("Car", 4)
        }
    }

    fun prepare() {
        buildFileSpec(PACKAGE_NAME, "Vehicle") {
            classType("Vehicle") {
                functions {
                    "getName" {
                        modifiers(ABSTRACT)
                        returns<String>()
                    }
                    "getWheelCount" {
                        modifiers(ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get(SOURCE_PATH))
    }

    fun write(name: String, wheelCount: Int) {
        buildFileSpec(PACKAGE_NAME, name) {
            classType(name) {
                superinterface(VEHICLE_NAME)
                functions {
                    "getName" {
                        modifiers(KModifier.OVERRIDE)
                        returns<String>()
                        appendLine("return %S", name)
                    }
                    "getWheelCount" {
                        modifiers(KModifier.OVERRIDE)
                        returns = INT
                        appendLine("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get(SOURCE_PATH))
    }
}
