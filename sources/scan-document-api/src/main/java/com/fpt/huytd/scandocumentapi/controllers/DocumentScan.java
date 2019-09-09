package com.fpt.huytd.scandocumentapi.controllers;

import com.fpt.huytd.scandocumentapi.commons.FileCodecBase64;
import com.fpt.huytd.scandocumentapi.params.PageRequest;
import com.fpt.huytd.scandocumentapi.params.Path;
import com.fpt.huytd.scandocumentapi.params.RequestTwain;
import com.fpt.huytd.scandocumentapi.servies.TwainScanner;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scan")
@CrossOrigin(origins = "*")
public class DocumentScan extends BaseController {

    @Autowired
    TwainScanner twainScanner;

    private java.util.List<Path> fakeData() throws IOException {
        List<String> strs = new ArrayList<>();
        Files.walk(Paths.get("C:\\Windows\\Temp\\ScanAPI\\"))
                .filter(s -> s.toString().endsWith(".JPG")).forEach(f -> {
            strs.add("C:\\Windows\\Temp\\ScanAPI\\" + f.getFileName());
        });
        java.util.List<Path> listPath = new ArrayList<>();
        int id = 0;
        for (String s : strs) {
            listPath.add(id, new Path(id, s));
            id++;
        }
        return listPath;
    }

    @RequestMapping(value = "/base64/page/images", method = RequestMethod.POST)
    public String getFirstImagePageNavigator(@RequestBody RequestTwain request) {
        Map<String, Object> maps = new HashMap<>();
        try {
            super.handleRequestParam(request);
            java.util.List<Path> listPath = twainScanner.doScanToTempImages(request);
//            java.util.List<Path> listPath = fakeData();
            if (listPath.size() > 0) {
                maps.put("total", listPath.size());
                maps.put("pageNumber", 1);
                maps.put("listPath", listPath);
                File file = new File(listPath.get(0).getPathName());
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

    @RequestMapping(value = "/base64/page/images/generate", method = RequestMethod.POST)
    public String generateBase64Images(@RequestBody RequestTwain request) {
        Map<String, Object> maps = new HashMap<>();
        try {
            super.handleRequestParam(request);
            String pathFile = twainScanner.doExportPdfFromImages(request);
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

    @RequestMapping(value = "/stream/page/images/exportPDF", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> exportImagesToPDF(@RequestBody RequestTwain request) {
        InputStreamResource inputStreamResource = null;
        HttpHeaders responseHeader = null;
        try {
            super.handleRequestParam(request);
            String pathFile = twainScanner.doExportPdfFromImages(request);
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

    /**
     * @param pageRequest
     * @return
     * @desc method get string base64 of one image from Pages<Image> page number
     */
    @RequestMapping(value = "/base64/page/image/findByPageNumber", method = RequestMethod.POST)
    public String getImageBase64ByIndexOfPage(@RequestBody PageRequest pageRequest) {
        Map<String, Object> maps = new HashMap<>();
        try {
            int pageNum = pageRequest.getPageNum();
            List<Path> paths = pageRequest.getPaths();
            if (pageNum < 0 || pageNum > paths.size()) {
                maps.put("status", HttpStatus.BAD_REQUEST.value());
                maps.put("message", "Index invalid or out of range!");
            } else {
                maps.put("status", HttpStatus.OK.value());
                if (paths.size() > 0) {
                    maps.put("total", paths.size());
                    Path path = paths.stream().filter(p -> p.getIndex() == pageNum - 1).findAny().orElse(null);
                    if (path != null) {
                        File file = new File(pageRequest.getPaths().get(pageRequest.getPageNum() - 1).getPathName());
                        if (file.exists() && file.length() > 0) {
                            String encodedString = FileCodecBase64.encodeFileToBase64Binary(file);
                            maps.put("data", encodedString);
                            maps.put("listPath", paths);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            maps.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            maps.put("message", "e.getMessage()");
        }
        return new Gson().toJson(maps);
    }

    /**
     * @param pageRequest
     * @return
     * @desc method remove image of Pages<Image> by page number
     */
    @RequestMapping(value = "/base64/page/image/removeByPageNumber", method = RequestMethod.POST)
    public String removeImageByIndex(@RequestBody PageRequest pageRequest) {
        Map<String, Object> maps = new HashMap<>();
        try {
            int pageNum = pageRequest.getPageNum();
            List<Path> paths = pageRequest.getPaths();
            if (pageNum < 0 || pageNum > paths.size()) {
                maps.put("status", HttpStatus.BAD_REQUEST.value());
                maps.put("message", "Index invalid or out of range!");
            } else {
                maps.put("status", HttpStatus.OK.value());
                if (paths.size() > 0) {
                    for (int i = 0; i < paths.size(); i++) {
                        if(paths.get(i).getIndex() == (pageNum -1)){
                            paths.remove(paths.get(i));
                            break;
                        }
                    }
                    maps.put("total", paths.size());
                    maps.put("listPath", paths);
                } else {
                    maps.put("total", paths.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            maps.put("message", "e.getMessage()");
        }
        return new Gson().toJson(maps);
    }


}
