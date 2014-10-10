/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.graphics;

import java.awt.Component;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ImageUtils
{
    public static Image buildZoomImageFromImage(ImageFile imgF, double coefZoom)
    {
        if (imgF == null) return null;
        if (coefZoom < 0.2) coefZoom = 0.2;
        BufferedImage bi = (BufferedImage) imgF.getImage();
        
        AffineTransform tx = new AffineTransform();
        tx.scale(coefZoom, coefZoom);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage( 
            (int) (bi.getWidth() * coefZoom),
            (int) (bi.getHeight() * coefZoom), bi.getType());
        return op.filter(bi, biNew);
    }
    
    public static Image buildOptimalZoomImageFromImage(ImageFile imgF, 
        Component parent)
    {
        if (imgF == null || parent == null) return null;
        int hauteurParent = parent.getHeight();
        int largeurParent = parent.getWidth();
        
        double coefZoomCalcule = calculateProportionalZoomRatio(
            largeurParent, hauteurParent, (BufferedImage) imgF.getImage());
        
        return ImageUtils.buildZoomImageFromImage(imgF, coefZoomCalcule);
    }

    public static double calculateProportionalZoomRatio(int width, int height,
        BufferedImage image)
    {
        double coefZoomCalcule = ((double) height) / image.getHeight();
        if (coefZoomCalcule > (((double) width) / image.getWidth()))
        {
            coefZoomCalcule = ((double) width) / image.getWidth();
        }
        return coefZoomCalcule;
    }
}
