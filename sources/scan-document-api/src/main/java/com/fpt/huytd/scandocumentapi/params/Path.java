package com.fpt.huytd.scandocumentapi.params;

import java.io.Serializable;

public class Path implements Serializable {
    private int index;
    private String pathName;

    public Path(int index, String pathName) {
        this.index = index;
        this.pathName = pathName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
