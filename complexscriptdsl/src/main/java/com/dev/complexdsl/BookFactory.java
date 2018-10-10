package com.dev.complexdsl;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.internal.reflect.Instantiator;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class BookFactory implements NamedDomainObjectFactory<Book> {
    private final Instantiator instantiator;

    public BookFactory(Instantiator instantiator) {
        this.instantiator = instantiator;
    }

    @Override
    public Book create(String name) {
        return instantiator.newInstance(Book.class, new Object[]{name, instantiator});
    }
}
