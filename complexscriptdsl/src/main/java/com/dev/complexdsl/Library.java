package com.dev.complexdsl;

import java.io.Serializable;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 79987269263359336L;
    private final String mName;
    private String libraryDetail;
    private Integer bookCount;

    public Library(String name) {
        mName = name;
    }

    public Library(String name, String libraryDetail, int bookCount) {
        this.mName = name;
        this.libraryDetail = libraryDetail;
        this.bookCount = bookCount;
    }

    public String getName() {
        return mName;
    }

    public String getLibraryDetail() {
        return libraryDetail;
    }

    public void setLibraryDetail(String libraryDetail) {
        this.libraryDetail = libraryDetail;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

}
