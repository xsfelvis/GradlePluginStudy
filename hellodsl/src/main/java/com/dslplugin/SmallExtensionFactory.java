package com.dslplugin;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.internal.reflect.Instantiator;

/**
 * Created by xsf on 2018/10/9.
 * Description: 用于在buildscript中创建新的指定类型的对象
 */
public class SmallExtensionFactory implements NamedDomainObjectFactory<SmallExtension> {
    private Instantiator mInstantiator;

    public SmallExtensionFactory(Instantiator mInstantiator) {
        this.mInstantiator = mInstantiator;
    }

    @Override
    public SmallExtension create(String name) {
        return mInstantiator.newInstance(SmallExtension.class, name);
    }
}
