package com.github.hahnbeelee.aidocwriterplugin.services

import com.intellij.openapi.project.Project
import com.github.hahnbeelee.aidocwriterplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
