/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * ImagePanel.java
 *
 * Created on 5 févr. 2009, 12:10:01
 */
package com.malchrosoft.graphics.components;

import com.malchrosoft.graphics.ImageUtils;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ImagePanel extends ImageComponent
{
    public ImagePanel(String imageFilePath)
    {
        super(imageFilePath, true);
    }

    public ImagePanel(String imageFilePath, boolean sameSizeImage)
    {
        super(imageFilePath, sameSizeImage, false);
    }

    public ImagePanel(String imageFilePath, boolean sameSizeImage,
        boolean betterQualitySize)
    {
        super(imageFilePath, sameSizeImage, betterQualitySize);
    }

    public ImagePanel(String imageFilePath, Dimension fixedImageSize)
    {
        super(imageFilePath, fixedImageSize);
    }

    @Override
    protected void redrawContent(Graphics2D g)
    {
        int width = (int) this.imageOriginalSize.getWidth();
        int height = (int) this.imageOriginalSize.getHeight();
        double ratio = 1.0;

        if (this.isSameSizeImage())
        {
            ratio = ImageUtils.calculateProportionalZoomRatio(this.getWidth(),
                this.getHeight(), this.getImage());
        }
        if (this.isBetterQualitySize() && ratio > 1.0)
        {
            ratio = 1.0;
        }
        if (!this.isSameSizeImage() && this.imageFixedSize != null)
        {
            width = (int) this.imageFixedSize.getWidth();
            height = (int) this.imageFixedSize.getHeight();
        }
        
        BufferedImage bi = GraphicsUtilities.createCompatibleTranslucentImage(
            (int) (width * ratio), (int) (height * ratio));
        Graphics2D big = (Graphics2D) bi.getGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        big.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        bi.getGraphics().drawImage(this.getImageFromImageFile(), 0, 0,
            (int) (width * ratio), (int) (height * ratio), null);
        bi.getGraphics().dispose();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(bi, 0, 0, null);
    }

    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

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
                ImagePanel.testPanel();
            }
        };
        SwingUtilities.invokeLater(frameCreationAndShowingRunnable);
    }

    private static void testPanel()
    {
        JFrame f = new JFrame("ImagePanel - TEST");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImagePanel ip = new ImagePanel("serveurs.png", true);
        f.setSize(new Dimension(200, 150));
        f.add(ip);
        f.setVisible(true);
    }

}
