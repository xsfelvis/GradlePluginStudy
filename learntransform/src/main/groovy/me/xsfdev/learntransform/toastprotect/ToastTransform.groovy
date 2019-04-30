package me.xsfdev.learntransform.toastprotect

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils

class ToastTransform extends Transform {

    @Override
    String getName() {
        return "toastProtector"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each {
            // load工程内的input
            it.directoryInputs.each { dirInput ->
                def inputPath = dirInput.file.path + File.separator
                ToastInjector.loadSourcePath(inputPath)
            }
            // load依赖中的input
            it.jarInputs.each { jarInput ->
                ToastInjector.loadSourcePath(jarInput.file.absolutePath)
            }
        }
        def destDir
        transformInvocation.inputs.each {
            // 处理工程内的input
            it.directoryInputs.each { dirInput ->
                def inputPath = dirInput.file.path + File.separator
                dirInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith('.class')) {
                        return
                    }
                    def classNameWithPackage = (file.path - inputPath - '.class').replaceAll(File.separator, '.')
                    ToastInjector.injectToastCompat(inputPath, classNameWithPackage)
                }
                destDir = transformInvocation.outputProvider.getContentLocation(
                        dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dirInput.file, destDir)
            }
            // 处理依赖中的input
            it.jarInputs.each { jarInput ->
                destDir = transformInvocation.outputProvider.getContentLocation(
                        jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                def tempDir = transformInvocation.context.temporaryDir
                def modifyJar = ToastInjector.modifyJar(jarInput.file, tempDir)
                if (!modifyJar) {
                    modifyJar = jarInput.file
                }
                FileUtils.copyFile(modifyJar, destDir)
            }
        }
    }
}