package com.dslplugin;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.invocation.DefaultGradle;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class CreateDSLPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Instantiator instantiator = ((DefaultGradle) project.getGradle()).getServices().get(Instantiator.class);
        NamedDomainObjectContainer<SmallExtension> smallExtensionsContainer = project.container(SmallExtension.class,
                new SmallExtensionFactory(instantiator));
        MyExtension myExtension = project.getExtensions().create("myExtension", MyExtension.class,
                new Object[]{instantiator, smallExtensionsContainer});
    }
}
