package me.xsfdev.learntransform

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.api.ApplicationVariantImpl
import me.xsfdev.learntransform.extension.SExtension
import me.xsfdev.learntransform.extension.Student
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Description:
 *
 * author xsf
 * * date 2019-04-23
 */
class GetExtension implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.logger.error("======Hello Gradle Plugin =====")
        println("======Hello Gradle Plugin2 =====")
        //创建一个SExtension对象
        project.extensions.create('se', SExtension)
        //创建一个Student对象
        project.extensions.create('student', Student)

        //定义一个task读取build.gradle中的'se'和'student'的属性
        project.task('readExtension').doLast {
            def se = project['se']
            def student = project['student']
            println "The SExtension's myName is " + se.myName
            println "The student's name is  " + student.name
            println "The student's phone is " + student.phone
        }

        project.getPlugins().each {
            println "plugin:${it.class.name}"
        }

        //或者 直接 project.android
        BaseExtension extension = project.extensions.getByName("android")
        def android = project.extensions.getByType(AppExtension)
        project.android

        android.applicationVariants
        //可以看出就是遍历集合把对象传递给闭包。               
        android.applicationVariants.all {
            variant ->
                def appID = variant.generateBuildConfig.appPackageName
                def variantData = variant.variantData
                def scope = variantData.scope
                String generateBuildConfigTaskName = variant.getVariantData().getScope().getGenerateBuildConfigTask().name
                println "appID:" + appID
                println "scope:" + scope
                println "generateBuildConfigTaskName:" + generateBuildConfigTaskName
        }


        //读取android属性
        //注册Transform
        //def android = project.extensions.findByType(AppExtension)
        //android.registerTransform(new PreDex(project))

    }
}
