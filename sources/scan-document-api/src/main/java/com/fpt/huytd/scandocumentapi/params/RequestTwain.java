package com.fpt.huytd.scandocumentapi.params;

import java.io.Serializable;
import java.util.List;

public class RequestTwain implements Serializable {
    private String colorMode;
    private Boolean duplex;
    private Integer dpi;
    private String fileName;
    private Boolean compression;
    private Float compressionQuality;
    private List<Path> paths;

    public RequestTwain() {
    }

    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public Boolean getDuplex() {
        return duplex;
    }

    public void setDuplex(Boolean duplex) {
        this.duplex = duplex;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getCompression() {
        return compression;
    }

    public void setCompression(Boolean compression) {
        this.compression = compression;
    }

    public Float getCompressionQuality() {
        return compressionQuality;
    }

    public void setCompressionQuality(Float compressionQuality) {
        this.compressionQuality = compressionQuality;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
