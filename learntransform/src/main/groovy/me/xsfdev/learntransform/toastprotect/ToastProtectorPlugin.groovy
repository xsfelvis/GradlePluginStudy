package me.xsfdev.learntransform.toastprotect

import org.gradle.api.Plugin
import org.gradle.api.Project

@SuppressWarnings("GrMethodMayBeStatic")
class ToastProtectorPlugin implements Plugin<Project> {
    /**
     * SDK路径
     */
    static def SDK_PATH

    @Override
    void apply(Project project) {
        project.extensions.create('ToastInjector', ToastExtensions)
        def toastParams = (ToastExtensions) project['ToastInjector']

        project.android.registerTransform(new ToastTransform())

        project.afterEvaluate {
            if (!project.plugins.hasPlugin('com.android.application')) {
                println('This plugin only works for applications, not libraries')
                return
            }
            if (!toastParams.proxyToastClass || !toastParams.proxyToastClass.trim()) {
                println 'no proxy setting'
                return
            }
            ToastInjector.TOAST_PROXY_CLASS = toastParams.proxyToastClass
            loadSDKPath(project)
            ToastInjector.loadSourcePath(SDK_PATH)
        }
    }

    /**
     * 获取编译版本的SDK jar以取得完整class依赖
     */
    def loadSDKPath(Project project) {
        SDK_PATH = "${project.android.sdkDirectory}/platforms/${project.android.compileSdkVersion}/android.jar"
    }
}