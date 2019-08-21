package com.fpt.huytd.scandocumentapi.commons;
import SK.gnome.morena.MorenaImage;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScannerImageService {

    public static Image getMorenaImage(MorenaImage image) throws IOException, BadElementException {
        java.awt.Image img = Toolkit.getDefaultToolkit().createImage(image);
        BufferedImage bimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);

        com.itextpdf.text.Image imageSave = Image.getInstance(bimg, null);
        imageSave.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        float y = PageSize.A4.getHeight() - imageSave.getScaledHeight();
        imageSave.setAbsolutePosition(0, y);
        return imageSave;
    }
}
