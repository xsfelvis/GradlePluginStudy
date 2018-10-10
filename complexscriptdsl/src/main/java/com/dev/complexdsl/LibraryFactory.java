package com.dev.complexdsl;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.internal.reflect.Instantiator;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class LibraryFactory implements NamedDomainObjectFactory<Library> {
    private Instantiator instantiator;

    public LibraryFactory(Instantiator instantiator) {
        this.instantiator = instantiator;
    }

    @Override
    public Library create(String name) {
        return instantiator.newInstance(Library.class, new Object[]{name});
    }
}
