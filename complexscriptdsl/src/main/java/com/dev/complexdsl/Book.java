package com.dev.complexdsl;

import org.gradle.internal.reflect.Instantiator;

import java.io.Serializable;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 4933666750282521704L;
    final String mName;
    private Library bookLocation;
    private String description;

    private Book(String name) {
        this.mName = name;
    }

    /**
     *
     */
    public Book(String name, Instantiator instantiator){
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public Library getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(Library bookLocation) {
        this.bookLocation = bookLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
