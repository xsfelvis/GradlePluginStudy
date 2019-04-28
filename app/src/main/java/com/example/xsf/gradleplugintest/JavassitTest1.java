package com.example.xsf.gradleplugintest;

/**
 * Description:
 * <p>
 * Author xsf
 * Date 2019-04-28
 */
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

public class JavassitTest1 {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("bean.User");

        //创建属性
        CtField field01 = CtField.make("private int id;",cc);
        CtField field02 = CtField.make("private String name;", cc);
        cc.addField(field01);
        cc.addField(field02);

        //创建方法
        CtMethod method01 = CtMethod.make("public String getName(){return name;}", cc);
        CtMethod method02 = CtMethod.make("public void setName(String name){this.name = name;}", cc);
        cc.addMethod(method01);
        cc.addMethod(method02);

        //添加有参构造器
        CtConstructor constructor = new CtConstructor(new CtClass[]{CtClass.intType,pool.get("java.lang.String")},cc);
        constructor.setBody("{this.id=id;this.name=name;}");
        cc.addConstructor(constructor);
        //无参构造器
        CtConstructor cons = new CtConstructor(null,cc);
        cons.setBody("{}");
        cc.addConstructor(cons);

        cc.writeFile("./com/example/xsf/gradleplugintest");
    }
}
