package me.xsfdev.learntransform.toastprotect

import javassist.CannotCompileException
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class ToastInjector {
    static def SDK_PATH_LIST = []
    static def TOAST_PROXY_CLASS
    //由于javassit需要在路径里面加载所以需要把path传进来
    static def loadSourcePath(String path) {
        if (!SDK_PATH_LIST.contains(path)) {
            ClassPool.default.appendClassPath(path)
            SDK_PATH_LIST.add(path)
        } else {
            println path + ' has loaded.'
        }
    }

    static def modifyJar(File oldJar, File restorePath) {
        // 读取原jar
        def file = new JarFile(oldJar)
        // 设置输出到的jar
        def hexName = DigestUtils.md5Hex(oldJar.absolutePath)
        def outputJar = new File(restorePath, hexName + oldJar.name)
        def jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        def enumeration = file.entries()

        while (enumeration.hasMoreElements()) {
            def jarEntry = (JarEntry) enumeration.nextElement()
            def inputStream = file.getInputStream(jarEntry)

            def entryName = jarEntry.getName()
            def className

            def zipEntry = new ZipEntry(entryName)

            jarOutputStream.putNextEntry(zipEntry)

            byte[] modifiedClassBytes = null
            byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
            if (entryName.endsWith(".class")) {
                className = (entryName - ".class").replaceAll(File.separator, '.')
                try {
                    def modifyClass = modifyToastClass(className)
                    modifiedClassBytes = modifyClass.toBytecode()
                } catch (Exception e) {
                    System.err.println(e)
                }
            }
            if (modifiedClassBytes == null) {
                jarOutputStream.write(sourceClassBytes)
            } else {
                jarOutputStream.write(modifiedClassBytes)
            }
            jarOutputStream.closeEntry()
        }

        jarOutputStream.close()
        file.close()

        return outputJar
    }

    static def injectToastCompat(String path, String className) {
        try {
            def ctClass = modifyToastClass(className)
            ctClass.writeFile(path)
            ctClass.detach()
        } catch (Exception e) {
            System.err.println(e)
        }
    }

    static def modifyToastClass(String className) {
        def ctClass = ClassPool.default.get(className)
        if (ctClass.frozen) {
            ctClass.defrost()
        }
        ctClass.instrument(new ExprEditor() {

            @Override
            void edit(MethodCall m) throws CannotCompileException {
                if (m.className == 'android.widget.Toast' && m.methodName == 'makeText') {
                    println "find directly toast use in: " + className
                    m.replace('{ $_ = ' + TOAST_PROXY_CLASS + '.makeText($$); }')
                } else if (findSuperClass(m.className, 'android.app.Dialog')) {
                    if (m.methodName == 'show') {
                        println "find dialog use in: " + className
                        def condContextIsActivity = '($0.getContext() instanceof android.app.Activity && ' +
                                '!((android.app.Activity) $0.getContext()).isFinishing())'
                        def condContextIsThemeWrapper = '($0.getContext() instanceof android.content.ContextWrapper && ' +
                                '((android.content.ContextWrapper) $0.getContext()).getBaseContext() instanceof android.app.Activity && ' +
                                '!((android.app.Activity) ((android.content.ContextWrapper) $0.getContext()).getBaseContext()).isFinishing())'
                        m.replace('{ if (' + condContextIsActivity + '||' + condContextIsThemeWrapper + ') {\n' +
                                '  $proceed($$);\n' +
                                '  } }')
                    }
                }
            }
        })
        return ctClass
    }

    static def findSuperClass(String className, String superClassName) {
        def ctClass = ClassPool.default.get(className)
        if (ctClass == null || ctClass.superclass == null ||
                ctClass.name == 'java.lang.Object' || ctClass.superclass.name == 'java.lang.Object') {
            return false
        } else if (ctClass.name == superClassName || ctClass.superclass.name == superClassName) {
            return true
        } else {
            return findSuperClass(ctClass.superclass.name, superClassName)
        }
    }
}