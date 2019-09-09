package com.fpt.huytd.scandocumentapi.params;

import java.io.Serializable;
import java.util.List;

public class PageRequest implements Serializable {
    private int pageNum;
    private List<Path> paths;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
