package com.myPlugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class CleanTestTask extends DefaultTask {
    CleanTestTask() {
        super()
        dependsOn"lint"
    }

    @TaskAction
    def testClean(){
        System.out.println("==================")
        System.out.println("Test Clean Task")
        System.out.println("==================")
    }





}