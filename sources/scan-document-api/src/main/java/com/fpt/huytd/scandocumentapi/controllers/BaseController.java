package com.fpt.huytd.scandocumentapi.controllers;

import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainConstants;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainSource;
import com.fpt.huytd.scandocumentapi.commons.ScannerImageService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    protected void settingMode(TwainSource source, String colorMode) throws TwainException {
        if (source != null) {
            source.setVisible(false); // hidden scanner machine setting
            int printColorMode = TwainConstants.TWPT_RGB;
            if ("BLW".equals(colorMode)) {
                printColorMode = TwainConstants.TWPT_BW;
            } else if ("GRAY".equals(colorMode)) {
                printColorMode = TwainConstants.TWPT_GRAY;
            }
            source.setPixelType(printColorMode);
        }
    }

    protected void settingPages(TwainSource source, String duplex) throws TwainException {
        if (source != null) {
            source.setFeederEnabled(true);
            source.setAutoFeed(true);
            source.setTransferCount(-1);
            if ("0".equals(duplex)) {
                source.setDuplexEnabled(false);
            } else if ("1".equals(duplex)) {
                source.setDuplexEnabled(true);
            }
        }
    }

    protected void saveFilePDF(TwainSource source, String filePath) throws IOException, DocumentException, TwainException {
        Document document = new Document();
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        do {
            MorenaImage image = new MorenaImage(source);
            if(image != null) {
                document.add(ScannerImageService.getMorenaImage(image));
                document.newPage();
            }
        }
        while (source.hasMoreImages());
        document.close();
    }

    protected void handleFileNameDefault(String fileName) {
        if (fileName.equals("file_scan")) {
            SimpleDateFormat sfd = new SimpleDateFormat("yyMMddHHmm");
            String dateStr = sfd.format(new Date());
            fileName = fileName.concat("_").concat(dateStr);
            System.out.println(fileName);
        }
    }
}
