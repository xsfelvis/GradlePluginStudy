package com.dslplugin;

/**
 * Created by xsf on 2018/10/9.
 * Description:
 */
public class SmallExtension {
    final String mName;
    private String mExtensionName;

    public SmallExtension(String name) {
        this.mName = name;
    }

    public String getExtensionName() {
        return mExtensionName;
    }

    public void setExtensionName(String extensionName) {
        mExtensionName = extensionName;
    }

    public String getName() {
        return mName;
    }
}
