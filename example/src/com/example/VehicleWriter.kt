package com.example

import com.hendraanggrian.kotlinpoet.buildFileSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import java.nio.file.Paths

class VehicleWriter {

    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private val VEHICLE_NAME = ClassName(PACKAGE_NAME, "Vehicle")

        @JvmStatic
        fun main(args: Array<String>) {
            val writer = VehicleWriter()
            writer.prepare()
            writer.write("Bike", 2)
            writer.write("Car", 4)
            ClassName
        }
    }

    fun prepare() {
        buildFileSpec(PACKAGE_NAME, "Vehicle") {
            types.addInterface("Vehicle") {
                functions {
                    "getName" {
                        addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                        returns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }

    fun write(name: String, wheelCount: Int) {
        buildFileSpec(PACKAGE_NAME, name) {
            types.addClass(name) {
                addSuperinterface(VEHICLE_NAME)
                functions {
                    "getName" {
                        addModifiers(KModifier.PUBLIC)
                        returns<String>()
                        annotations.add<Override>()
                        appendln("return %S", name)
                    }
                    "getWheelCount" {
                        addModifiers(KModifier.PUBLIC)
                        returns = INT
                        annotations.add<Override>()
                        appendln("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }
}