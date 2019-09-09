package com.fpt.huytd.scandocumentapi.controllers;

import com.fpt.huytd.scandocumentapi.params.ColorMode;
import com.fpt.huytd.scandocumentapi.params.RequestTwain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.Date;

public class BaseController {

    protected HttpHeaders initResponsePDFHeader(String fileName, long length) {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.APPLICATION_PDF);
        responseHeader.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        responseHeader.setContentLength(length);
        return responseHeader;
    }

    /**
     * @param request
     * @throws Exception
     * @desc init default values for setting TWAIN, {dpi:200,duplex:true,colorMode:RBG,compression:true,compressionQuality:0.7}
     */
    protected void handleRequestParam(RequestTwain request) throws Exception {
        if (request != null) {
            if (StringUtils.isEmpty(request.getColorMode())) {
                request.setColorMode(ColorMode.RBG.getValue());
            }
            if (StringUtils.isEmpty(request.getFileName())) {
                request.setFileName("file_" + new Date().getTime() + ".pdf");
            }
            if (request.getDuplex() == null) {
                request.setDuplex(Boolean.TRUE);
            }
            if (request.getDpi() == null) {
                request.setDpi(Integer.valueOf(200));
            }
            if (request.getCompression() == null) {
                request.setCompression(Boolean.TRUE);
            }
            if (request.getCompressionQuality() == null) {
                request.setCompressionQuality(Float.valueOf(0.7f));
            }
        }
    }
}
