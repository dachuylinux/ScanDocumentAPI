package com.fpt.huytd.scandocumentapi.servies;

import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
import com.fpt.huytd.scandocumentapi.commons.ScannerImageService;
import com.fpt.huytd.scandocumentapi.params.ColorMode;
import com.fpt.huytd.scandocumentapi.params.Path;
import com.fpt.huytd.scandocumentapi.params.RequestTwain;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class TwainScanner {
    private static final String TEMP_FOLDER = "C:\\Windows\\Temp\\ScanAPI\\";

    public String doScanToPdfStraightAway(RequestTwain twain) throws Exception {
        String resultPath = "";
        try {
            TwainSource source = TwainManager.selectSource(null, null);
            setupScannerTwain(source, twain);
            resultPath = saveToPdfFile(source, twain.getCompression().booleanValue(),
                    twain.getCompressionQuality().floatValue(), twain.getFileName());
            TwainManager.close();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                TwainManager.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return resultPath;
    }

    public java.util.List<Path> doScanToTempImages(RequestTwain twain) throws Exception {
        java.util.List<Path> listPath = new ArrayList<>();
        try {
            TwainSource source = TwainManager.selectSource(null, null);
            setupScannerTwain(source, twain);
            listPath.addAll(saveToTempImageFiles(source, twain.getColorMode()));
            TwainManager.close();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                TwainManager.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return listPath;
    }

    protected String saveToPdfFile(TwainSource source, boolean compression, float compressionQuality, String fileName)
            throws IOException, DocumentException, TwainException {
        if (source != null) {
            String pathFile = TEMP_FOLDER + fileName;
            Document document = new Document();
            File file = new File(pathFile);
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            do {
                MorenaImage image = new MorenaImage(source);
                if (image != null) {
                    document.add(ScannerImageService.getMorenaImage(image, compression, compressionQuality));
                    document.newPage();
                }
            }
            while (source.hasMoreImages());
            document.close();
            return pathFile;
        }
        return "";
    }

    public String doExportPdfFromImages(RequestTwain request)
            throws IOException, DocumentException {
        java.util.List<Path> paths = request.getPaths();
        if (paths != null && paths.size() > 0) {
            boolean compression = request.getCompression().booleanValue();
            float compressionQuality = request.getCompressionQuality().floatValue();
            String pathFile = TEMP_FOLDER + request.getFileName();
            Document document = new Document();
            File file = new File(pathFile);
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            for (Path p : paths) {
                document.add(ScannerImageService.getImageByPath(p.getPathName(), compression, compressionQuality));
                document.newPage();
            }
            document.close();
            return pathFile;
        }
        return "";
    }

    protected java.util.List<Path> saveToTempImageFiles(TwainSource source, String colorMode) throws IOException, TwainException {
        java.util.List<Path> listPath = new ArrayList<>();
        do {
            MorenaImage image = new MorenaImage(source);
            if (image != null) {
                Path path = new Path(listPath.size(), ScannerImageService.saveImageToDisk(image, TEMP_FOLDER, colorMode));
                listPath.add(path);
            }
        }
        while (source.hasMoreImages());
        return listPath;
    }

    public void setupScannerTwain(TwainSource source, RequestTwain twain) throws TwainException {
        if (source != null) {
            source.setResolution(twain.getDpi());
            //setting mode
            source.setVisible(false); // hidden scanner machine setting
            source.setPixelType(ColorMode.fromNameOrThrowException(twain.getColorMode()).getKey());
            //multipe scan
            source.setFeederEnabled(true);
            source.setAutoFeed(true);
            source.setTransferCount(-1);
            //set duplex
            source.setDuplexEnabled(twain.getDuplex().booleanValue());
        }
    }
}
