package com.dev.complexdsl;

/**
 * Created by xsf on 2018/10/10.
 * Description:
 */
public class LibraryManager {
    private String managerName;
    private boolean emptyLibraryUsable;

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public boolean isEmptyLibraryUsable() {
        return emptyLibraryUsable;
    }

    public void setEmptyLibraryUsable(boolean emptyLibraryUsable) {
        this.emptyLibraryUsable = emptyLibraryUsable;
    }
}
