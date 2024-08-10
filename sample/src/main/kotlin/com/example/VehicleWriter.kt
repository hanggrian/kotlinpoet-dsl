package com.example

import com.hanggrian.kotlinpoet.ABSTRACT
import com.hanggrian.kotlinpoet.buildFileSpec
import com.hanggrian.kotlinpoet.addClass
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
            types.addClass("Vehicle") {
                functions {
                    "getName" {
                        addModifiers(ABSTRACT)
                        setReturns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get(SOURCE_PATH))
    }

    fun write(name: String, wheelCount: Int) {
        buildFileSpec(PACKAGE_NAME, name) {
            types.addClass(name) {
                addSuperinterface(VEHICLE_NAME)
                functions {
                    "getName" {
                        addModifiers(KModifier.OVERRIDE)
                        setReturns<String>()
                        appendLine("return %S", name)
                    }
                    "getWheelCount" {
                        addModifiers(KModifier.OVERRIDE)
                        returns = INT
                        appendLine("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get(SOURCE_PATH))
    }
}
