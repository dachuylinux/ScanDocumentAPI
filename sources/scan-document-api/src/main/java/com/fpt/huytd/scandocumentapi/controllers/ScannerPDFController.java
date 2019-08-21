package com.fpt.huytd.scandocumentapi.controllers;

import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController()
public class ScannerPDFController extends BaseController {

    /**
     * @param colorMode printer color mode: RBG, BLW, GRAY. Default RBG
     * @param duplex    default: true
     * @param fileName  default: file_scan
     * @return
     */
    @RequestMapping(value = "/scanPDF/multiplePages", method = RequestMethod.GET)
    public ResponseEntity<Resource> multipe(@RequestParam(value = "colorMode", defaultValue = "RBG") String colorMode,
                                            @RequestParam(value = "duplex", defaultValue = "1") String duplex,
                                            @RequestParam(value = "fileName", defaultValue = "file_scan") String fileName) {
        String pathFile = String.format("C:\\Windows\\Temp\\ScanAPI\\%s.pdf", fileName);
        try {
            super.handleFileNameDefault(fileName);
            TwainSource source = TwainManager.selectSource(null, null);
            if (source != null) {
                super.settingMode(source, colorMode);
                super.settingPages(source, duplex);
                super.saveFilePDF(source, pathFile);
            }
            TwainManager.close();
            //return file
            String fileNameReturn = fileName.concat(".pdf");
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileNameReturn);
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
            File file = new File(pathFile);

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}