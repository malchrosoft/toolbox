/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components;

import com.malchrosoft.graphics.ImageFile;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Aymeric Malchrowicz
 */
public abstract class ImageComponent extends JPanel
{
    private ImageFile imageFile;
    private boolean sameSizeImage;
    private boolean betterQualitySize;
    private BufferedImage image;

    protected Dimension imageOriginalSize;
    protected Dimension imageFixedSize;

    /** 
     * Creates new form ImageComponent. By default : <br/>
     * <ul><li>the image takes all the place in its contener</li>
     * <li>the better quality is kept in reducing it, but no increase the size
     * more than the original size.</li></ul>
     * @param imageFilePath
     */
    public ImageComponent(String imageFilePath)
    {
        this(imageFilePath, true);
    }

     /**
     * Creates new image component. By default : <br/>
     * <ul><li>the better quality is kept in reducing it, but no increase the size
     * more than the original size.</li></ul>
     * @param imageFilePath the image file name
     * @param sameSizeImage the boolean for : the image has the size of the
     * contener.
     */
    public ImageComponent(String imageFilePath, boolean sameSizeImage)
    {
        this(imageFilePath, sameSizeImage, false);
    }

    /**
     * Creates new image component.
     * @param imageFilePath the image file path
     * @param sameSizeImage the boolean for : the image has the size of the
     * contener
     * @param betterQualitySize the boolean for : The image can be reduced but
     * not increased, to keep its quality
     */
    public ImageComponent(String imageFilePath, boolean sameSizeImage,
        boolean betterQualitySize)
    {
        this.setOpaque(false);
        this.imageFile = new ImageFile(imageFilePath);
        this.image = (BufferedImage) this.imageFile.getImage();
        this.sameSizeImage = sameSizeImage;
        this.betterQualitySize = betterQualitySize;
        this.refreshOriginalSize();
        this.imageFixedSize = null;

    }

    /**
     * Creates new image component with a dimension fixed.
     * @param imageFilePath the image file path
     * @param fixedImageSize the fixed dimension of this image
     */
    public ImageComponent(String imageFilePath, Dimension imageFixedSize)
    {
        this(imageFilePath, false, false);
        this.imageFixedSize = imageFixedSize;
    }

    public ImageComponent(BufferedImage image)
    {
        this(image, true, false);
    }

    public ImageComponent(BufferedImage image, boolean sameSizeImage,
        boolean betterQualitySize)
    {
        this("", sameSizeImage, betterQualitySize);
        this.setImage(image);
    }

    public ImageComponent(BufferedImage image, Dimension imageFixedSize)
    {
        this("", imageFixedSize);
        this.setImage(image);
    }

    /**
     * Sets the image redimensionable : <br/>
     * the image has the same size of the contener, <br/>
     * ths image has not fixed size.
     */
    public void setRedimensionableImage()
    {
        this.setSameSizeImage(true);
        this.imageFixedSize = null;
    }

    public void setFixedImageSize(Dimension imageFixedSize)
    {
        this.imageFixedSize = imageFixedSize;
        this.setSameSizeImage(false);
    }
    public void setFixedImageSize(int width, int height)
    {
        this.setFixedImageSize(new Dimension(width, height));
    }

    public boolean isSameSizeImage()
    {
        return sameSizeImage;
    }

    public void setSameSizeImage(boolean sameSizeImage)
    {
        this.sameSizeImage = sameSizeImage;
    }

    /**
     * Returns the image file path.
     * @return the image file path
     */
    public String getImageFilePath()
    {
        return this.imageFile.getImageFilePath();
    }

    /**
     * Returns the image.
     * @return the image
     */
    public BufferedImage getImage()
    {
        if (this.image == null)
        {
            this.image = (BufferedImage) this.imageFile.getImage();
        }
        return this.image;
    }
    

    protected BufferedImage getImageFromImageFile()
    {
        return (BufferedImage) this.imageFile.getImage();
    }

    /**
     * Sets the image witout change the original image file
     * @param image the image
     */
    public void setImage(BufferedImage image)
    {
        this.image = image;
        this.refreshOriginalSize();
    }

    protected void refreshOriginalSize()
    {
        this.imageOriginalSize = new Dimension(this.image.getWidth(),
            this.image.getHeight());
    }

    /**
     * Loads a new image in this image component.
     * @param imageFilePath the image file path
     */
    public void loadImage(String imageFilePath)
    {
        this.imageFile.setImageFilePath(imageFilePath);
        this.setImage((BufferedImage) this.imageFile.getImage());
    }

    /**
     * Reloads the image file and refresh the image
     */
    public void reloadImage()
    {
        this.imageFile.reloadImage();
        this.setImage((BufferedImage) this.imageFile.getImage());
    }

    /**
     * Returns true if the better quality size fonction is enable,
     * false otherwise.
     * @return true if the better quality size fonction is enable,
     * false otherwise
     */
    public boolean isBetterQualitySize()
    {
        return betterQualitySize;
    }

    /**
     * Sets if this component have to keep the better quality size for image
     * @param betterQualitySize the boolean value
     */
    public void setBetterQualitySize(boolean betterQualitySize)
    {
        this.betterQualitySize = betterQualitySize;
        if (this.betterQualitySize) this.imageFixedSize = null;
    }

    /**
     * Returns the original size of the image (file).
     * @return the original size of the image (file)
     */
    public Dimension getImageOriginalSize()
    {
        return imageOriginalSize;
    }


    @Override
    public void paint(Graphics g)
    {
//        if (this.isSameSizeImage())
//        {
//            Rectangle b = this.getBounds();
//            this.setBounds((int) b.getX(), (int) b.getY(),
//                this.getParent().getWidth() - ((int) b.getX() * 2),
//                this.getParent().getHeight() - ((int) b.getY() * 2));
//        }
        this.redrawContent((Graphics2D) g);
    }

    /**
     * Redraws the content of this component.
     * @param g the Graphics used in the paint(...) method
     */
    protected abstract void redrawContent(Graphics2D g);

}
