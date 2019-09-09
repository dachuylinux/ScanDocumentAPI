package com.fpt.huytd.scandocumentapi.controllers;

import com.fpt.huytd.scandocumentapi.commons.FileCodecBase64;
import com.fpt.huytd.scandocumentapi.params.RequestTwain;
import com.fpt.huytd.scandocumentapi.servies.TwainScanner;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/scanAway")
@CrossOrigin(origins = "*")
public class DocumentScanStraightAway extends BaseController  {

    @Autowired
    TwainScanner twainScanner;

    @RequestMapping(value = "/base64", method = RequestMethod.GET)
    public String scanBase64PDF(@RequestBody RequestTwain request) {
        Map<String, Object> maps = new HashMap<>();
        try {
            super.handleRequestParam(request);
            String pathFile = twainScanner.doScanToPdfStraightAway(request);
            if (!StringUtils.isEmpty(pathFile)) {
                File file = new File(pathFile);
                if (file.exists() && file.length() > 0) {
                    String encodedString = FileCodecBase64.encodeFileToBase64Binary(file);
                    maps.put("data", encodedString);
                }
            }
            maps.put("status", HttpStatus.OK.value());
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            maps.put("message", e.getMessage());
        }
        return new Gson().toJson(maps);
    }

    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> scanStreamPDF(@RequestBody RequestTwain request) {
        InputStreamResource inputStreamResource = null;
        HttpHeaders responseHeader = null;
        try {
            super.handleRequestParam(request);
            String pathFile = twainScanner.doScanToPdfStraightAway(request);
            if (!StringUtils.isEmpty(pathFile)) {
                File file = new File(pathFile);
                if (file.exists() && file.length() > 0) {
                    byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
                    InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
                    inputStreamResource = new InputStreamResource(inputStream);
                    responseHeader = super.initResponsePDFHeader(request.getFileName(), file.length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<InputStreamResource>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
    }
}
