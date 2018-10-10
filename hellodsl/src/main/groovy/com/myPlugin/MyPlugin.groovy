package com.myPlugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.android.lintOptions.xmlOutput = new File(project.buildDir, "lintResult.xml")
        }
        project.tasks.create('cleanTest', CleanTestTask)

    }
}

