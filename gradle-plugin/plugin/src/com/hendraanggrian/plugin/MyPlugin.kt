package com.hendraanggrian.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class MyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create<MyExtension>("myPlugin")
    }

    override fun toString(): String = "yo!"
}
