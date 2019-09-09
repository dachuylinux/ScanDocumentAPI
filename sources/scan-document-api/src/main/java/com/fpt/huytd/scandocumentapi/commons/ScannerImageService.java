package com.fpt.huytd.scandocumentapi.commons;

import SK.gnome.morena.MorenaImage;
import com.fpt.huytd.scandocumentapi.params.ColorMode;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.PageSize;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScannerImageService {

    public static com.itextpdf.text.Image getMorenaImage(MorenaImage image, boolean isCompression, float compressionQuality) throws IOException, BadElementException {
        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(image);
        int scaledWidth = awtImage.getWidth(null);
        int scaledHeight = awtImage.getHeight(null);
        BufferedImage bimg = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(awtImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        com.itextpdf.text.Image imageSave;
        if (isCompression) {
            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(compressionQuality);
            jpgWriter.setOutput(outputStream);
            jpgWriter.write(null, new IIOImage(bimg, null, null), jpgWriteParam);
            jpgWriter.dispose();
            byte[] jpegData = compressed.toByteArray();
            imageSave = com.itextpdf.text.Image.getInstance(jpegData);
        } else {
            imageSave = com.itextpdf.text.Image.getInstance(bimg, null);
        }
        imageSave.scaleToFit(PageSize.A4);
        float y = PageSize.A4.getHeight() - imageSave.getScaledHeight();
        imageSave.setAbsolutePosition(0, y);
        return imageSave;
    }

    public static com.itextpdf.text.Image getImageByPath(String pathFile, boolean isCompression, float compressionQuality) throws IOException, BadElementException {
        BufferedImage bimg = ImageIO.read(new File(pathFile));
        com.itextpdf.text.Image imageSave;
        if (isCompression) {
            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(compressionQuality);
            jpgWriter.setOutput(outputStream);
            jpgWriter.write(null, new IIOImage(bimg, null, null), jpgWriteParam);
            jpgWriter.dispose();
            byte[] jpegData = compressed.toByteArray();
            imageSave = com.itextpdf.text.Image.getInstance(jpegData);
        } else {
            imageSave = com.itextpdf.text.Image.getInstance(bimg, null);
        }
        imageSave.scaleToFit(PageSize.A4);
        float y = PageSize.A4.getHeight() - imageSave.getScaledHeight();
        imageSave.setAbsolutePosition(0, y);
        return imageSave;
    }

    public static String saveImageToDisk(MorenaImage morenaImage, String pathFolder, String colorMode) throws IOException {
        int color = BufferedImage.TYPE_INT_RGB;
        if (colorMode.equals(ColorMode.BW.getValue())) {
            color = BufferedImage.TYPE_BYTE_BINARY;
        } else if (colorMode.equals(ColorMode.GRAY.getValue())) {
            color = BufferedImage.TYPE_BYTE_GRAY;
        }
        String pathFile = String.format("%s_img_%d.JPG", pathFolder, new Date().getTime());
        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(morenaImage);
        int scaledWidth = awtImage.getWidth(null);
        int scaledHeight = awtImage.getHeight(null);
        BufferedImage bimg = new BufferedImage(scaledWidth, scaledHeight, color);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(awtImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIO.write(bimg, "JPG", new File(pathFile));
        return pathFile;
    }
}
