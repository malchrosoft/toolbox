/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components;

import com.malchrosoft.debug.Log;
import com.malchrosoft.graphics.ImageUtils;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.graphics.ReflectionRenderer;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ImageReflectionPanel extends ImageComponent
{
    private BufferedImage reflect;
    private ReflectionRenderer renderer;
    private int reflectDistance;

    /**
     * Creates new ImageReflectionPanel
     * @param image the image to refect
     */
    public ImageReflectionPanel(BufferedImage image)
    {
        this(image, 0);
    }

    /**
     * Creates new ImageReflectionPanel
     * @param image the image to reflect
     * @param reflectDistance the reflect distance
     */
    public ImageReflectionPanel(BufferedImage image, int reflectDistance)
    {
        super(image, true, false);
        this.reflect = null;
        this.renderer = new ReflectionRenderer();
        if (reflectDistance < 0) reflectDistance = 0;
        this.reflectDistance = reflectDistance;
        this.refreshReflect();
        this.repaint();
    }

    /**
     * Creates a new ImageReflectionPanel from a image file path
     * @param imageFilePath the imageFilePath
     */
    public ImageReflectionPanel(String imageFilePath)
    {
        this(imageFilePath, true);
    }

    /**
     * Creates a new ImageReflectionPanel from an image file path
     * @param imageFilePath the image file path
     * @param sameSizeImage true if the image must be resizable
     */
    public ImageReflectionPanel(String imageFilePath, boolean sameSizeImage)
    {
        super(imageFilePath, sameSizeImage);
        this.renderer = new ReflectionRenderer();
        this.refreshReflect();
    }

    /**
     * Creates a new ImageReflectionPanel from an image file path
     * @param imageFilePath the image file path
     * @param reflectDistance the distance of the reflect
     */
    public ImageReflectionPanel(String imageFilePath, int reflectDistance)
    {
        this(imageFilePath, true);
        this.setReflectDistance(reflectDistance);
    }

    /**
     * Sets the opacity <br/>A value between 0.0f and 1.0f
     * @param opacity the opacity
     */
    public void setOpacity(float opacity)
    {
        this.renderer.setOpacity(opacity);
//        this.refreshReflect();
    }

    /**
     * Returns the opacity value of the reflect.
     * @return the opacity value of the reflect
     */
    public float getOpacity()
    {
        return this.renderer.getOpacity();
    }

    /**
     * Sets the lenght of the reflect
     * @param length
     */
    public void setLength(float length)
    {
        this.renderer.setLength(length);
//        this.refreshReflect();
    }

    /**
     * Returns the length of the reflect.
     * @return the length of the reflect
     */
    public float getLength()
    {
        return this.renderer.getLength();
    }

    /**
     * Sets the blur activation value.
     * @param enabled true for enabled, false otherwise
     */
    public void setBlurEnabled(boolean enabled)
    {
        this.renderer.setBlurEnabled(enabled);
        this.refreshReflect();
    }

    /**
     * Returns the BurEnabled boolean value
     * @return true if the Blur is enabled, false otherwise.
     */
    public boolean isBlurEnabled()
    {
        return this.renderer.isBlurEnabled();
    }

    /**
     * Sets the blur radius
     * @param radius the blur raduis
     */
    public void setBlurRadius(int radius)
    {
        this.renderer.setBlurRadius(radius);
//        this.refreshReflect();
    }

    @Override
    public void setImage(BufferedImage image)
    {
        super.setImage(image);
        this.refreshReflect();
        this.repaint();
    }
    
    @Override
    public void loadImage(String filePath)
    {
		if (!new File(filePath).exists())
			return;
        super.loadImage(filePath);
        this.refreshReflect();
        this.repaint();
    }

    /**
     * Returns the reflect distance
     * @return
     */
    public int getReflectDistance()
    {
        return this.reflectDistance;
    }

    public void setReflectDistance(int reflectDistance)
    {
        this.reflectDistance = reflectDistance;
        this.repaint();
    }

    private synchronized void refreshReflect()
    {
        try
        {
            this.reflect = this.renderer.createReflection(this.getImage());
        } catch (OutOfMemoryError ooe)
        {
            this.reflect = null;
            System.gc();
            try
            {
                Thread.sleep(1000);
                this.reflect = this.renderer.createReflection(this.getImage());
            } catch (OutOfMemoryError ooe2)
            {
                Log.debug("\n*** ERROR TO CORRECT !!! \n" + ooe.getMessage() +
                    ooe.getClass().getName() + "\n");
                this.reflect = new BufferedImage(10, 10,
                    BufferedImage.TYPE_INT_ARGB);
            } catch (InterruptedException ie) {}
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        this.refreshReflect();
        this.redrawContent(g2d);
    }

    @Override
    protected void redrawContent(Graphics2D g)
    {
        int width = (int) this.imageOriginalSize.getWidth();
        int height = (int) (this.imageOriginalSize.getHeight() +
            this.imageOriginalSize.getHeight()*this.getLength() +
            this.getReflectDistance());
        double ratio = 1.0;
        int sup = 0;
        
        if (this.renderer.isBlurEnabled())
        {
            sup += this.renderer.getEffectiveBlurRadius();
        }
        BufferedImage tmpIm = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_ARGB);
        if (this.isSameSizeImage())
        {
            ratio = ImageUtils.calculateProportionalZoomRatio(this.getWidth(),
                this.getHeight(), tmpIm);
            if (this.isBetterQualitySize() && ratio > 1.0)
            {
                ratio = 1.0;
            }
        }
        else if (this.imageFixedSize != null)
        {
            width = (int) this.imageFixedSize.getWidth();
            height = (int) this.imageFixedSize.getHeight();
            ratio = ImageUtils.calculateProportionalZoomRatio(width, height,
                tmpIm);
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        /**
         * CACULATION :
         * [h = image height, d = reflect distance, rh = reflect height
         *      H = ImageReflectionPanel height, ht = total height]
         * For ht = H :
         *      ratio = H / ht                 [ht = h + d + rh]
         *      h = ht - rh - d
         *      (h + rh + d)*ratio = H
         *      h*ratio + rh*ratio + d*ratio = H
         *      h*ratio = H - rh*ratio - d*ratio
         *      h = (H - rh*ratio - d*ratio) / ratio
         *
         *  or
         *      newHeight = newWidth / originalWidth * originalHeight
         */
        // Center this image and reflect
        int xR, yR, xI, yI;
        int newWidth = (int) (this.imageOriginalSize.getWidth() * ratio);
        int newHeight = (int) (newWidth / this.imageOriginalSize.getWidth() *
            this.imageOriginalSize.getHeight());
//        int newHeight = (int) (ratio * this.imageOriginalSize.getHeight());
        xR = xI = (this.getWidth() - newWidth) / 2;
        yR = yI = (int) (this.getHeight() - (newHeight +
             newHeight * this.getLength() + this.reflectDistance)) /2;
        yR += newHeight + this.reflectDistance;
        // Add sup only if the blur is enabled
        xI += sup*ratio;

        g.drawImage(this.getImage(), xI, yI, newWidth, newHeight, null);
        newWidth += (sup*ratio*2);
        g.drawImage(this.reflect, xR, yR, newWidth, (int) (newHeight *
            this.getLength()), null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Runnable frameCreationAndShowingRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                ImageReflectionPanel.testPanel();
            }
        };
        SwingUtilities.invokeLater(frameCreationAndShowingRunnable);
    }

    private static void testPanel()
    {
        JFrame f = new JFrame("ImageReflectionPanel - TEST");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageReflectionPanel ip = new ImageReflectionPanel("", 2);
        ip.setBlurEnabled(true);
        ip.setBlurRadius(1);
        f.setSize(new Dimension(400, 650));
        f.add(ip);
        f.setVisible(true);
    }
}
