package com.dev.complexdsl;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.internal.reflect.Instantiator;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class MyExtension {
    private NamedDomainObjectContainer<Book> books;
    private NamedDomainObjectContainer<Library> libraries;
    private LibraryManager mLibraryManager;
    private Project project;

    public MyExtension(Project project, Instantiator instantiator,NamedDomainObjectContainer<Book> bookContainer,NamedDomainObjectContainer<Library> libraryContainer) {
        this.project = project;
        this.books = bookContainer;
        this.libraries = libraryContainer;
        this.mLibraryManager = instantiator.newInstance(LibraryManager.class,new Object[0]);
    }

    public NamedDomainObjectContainer<Book> getBooks() {
        return books;
    }

    public NamedDomainObjectContainer<Library> getLibraries() {
        return libraries;
    }

    public Project getProject() {
        return project;
    }

    public void books(Action<? super NamedDomainObjectContainer<Book>> action) {
        action.execute(this.books);
    }

    public void libraries(Action<? super NamedDomainObjectContainer<Library>> action) {
        action.execute(this.libraries);
    }
}
