package me.xsfdev.learntransform

import me.xsfdev.learntransform.extension.SExtension
import me.xsfdev.learntransform.extension.Student
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Description:
 *
 * @author xsf
 * @date 2019-04-23
 */
class Hotfix implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.logger.error("======Hello Gradle Plugin =====")
        println("======Hello Gradle Plugin2 =====")
        //创建一个SExtension对象
        project.extensions.create('se', SExtension)
        //创建一个Student对象
        project.extensions.create('student', Student)

        //定义一个task读取build.gradle中的'se'和'student'的属性
        project.task('readExtension')<<{
            def se = project['se']
            def student = project['student']
            println "The SExtension's myName is " + se.myName
            println "The student's name is  " + student.name
            println "The student's phone is " + student.phone
        }

    }
}
