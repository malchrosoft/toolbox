/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * TranslucentPanel.java
 *
 * Created on 20 janv. 2009, 16:24:21
 */
package com.malchrosoft.graphics.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TranslucentPanel extends javax.swing.JPanel
{
    private int opacity;
    private BufferedImage panelImage;

    private boolean roundCorners;
    private int roundSize;
    private boolean contentTranslucentToo;

    /**
     * Creates a new Translucent Panel.
     * By default : <ul><li>the opacity value = <b>80%</b>,</li>>
     * <li>the corners are rounded,</li>
     * <li>the round corner value is 10px<,/li>
     * <li>children are opaque.</li><li></li></ul>
     */
    public TranslucentPanel()
    {
        this(80);
    }

    /**
     * Creates a new translucent panel. <br/>
     * By default : <ul><li>the corners are rounded</li>
     * <li>the round corner value is 10px</li>
     * <li>children are opaque</li><li></li></ul>
     * @param opacity the opacity percentage
     */
    public TranslucentPanel(int opacity)
    {
        this(opacity, true);
    }

    /**
     * Creates a new translucent panel. <br/>
     * By default : <ul><li>the round corner value is 10px</li>
     * <li>children are opaque</li><li></li></ul>
     * @param opacity the opacity percentage.
     * @param roundCorners boolean value : the corners are rounded.
     */
    public TranslucentPanel(int opacity, boolean roundCorners)
    {
        this(opacity, roundCorners, false);
    }

    /**
     * Creates a new translucent panel. <br/>
     * By default the round corner value is 10px.
     * @param opacity the opacity (in percentage)
     * @param roundCorners boolean value for : the corners are rounded
     * @param childrenTranslucent boolean value for : the children are
     * transparent too, like the panrent panel.
     */
    public TranslucentPanel(int opacity, boolean roundCorners,
        boolean childrenTranslucent)
    {
        this(opacity, roundCorners, childrenTranslucent, 10);
    }

    /**
     * Creates a new translent panel. <br/>
     * By default the round corner value is 10px
     * @param opacity
     * @param roundCorners
     * @param childrenTranslucent
     * @param roundSize
     */
    public TranslucentPanel(int opacity, boolean roundCorners,
        boolean childrenTranslucent, int roundSize)
    {
        //initComponents();
        this.setRoundCorners(roundCorners);
        this.setOpacity(opacity);
        this.setContentTranslucentToo(childrenTranslucent);
        this.setRoundSize(roundSize);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        this.drawPanel((Graphics2D) g);
    }

    /**
     * Draws the panel with the opacity value
     * @param g2d The graphics context
     */
    private void drawPanel(Graphics2D g2d)
    {
        Composite initialComposite = g2d.getComposite();
        
        /* This is for translucent children. */
        if (this.contentTranslucentToo)
        {
            this.panelImage = TranslucentComponentTools.
                createComponentImageIfNecessary(this, this.panelImage);
            Graphics2D gPanel = this.panelImage.createGraphics();
            gPanel.setClip(g2d.getClip());
            gPanel.setColor(this.getBackground());
            super.paintComponent(gPanel);
        }
        else super.paintComponent(g2d);

        g2d.setComposite(AlphaComposite.SrcOver.derive(
            TranslucentComponentTools.getAlphaValue(this.opacity)));
        
        // Enable anti-aliasing to get smooth outlines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(this.getBackground());
        if (this.roundCorners)
        {
            g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),
                this.getRoundSize(), this.getRoundSize());
        }
        else
        {
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        
        /* This is for the translucent panel only */
        if (! this.contentTranslucentToo)
        {
            g2d.setComposite(initialComposite);
        }
    }

    public boolean isContentTranslucentToo()
    {
        return contentTranslucentToo;
    }

    public void setContentTranslucentToo(boolean contentTranslucentToo)
    {
        this.contentTranslucentToo = contentTranslucentToo;
    }

    public int getOpacity()
    {
        return opacity;
    }

    /**
     * Sets the opacity percentage value: 0% = totaly translucent, 100% = opaque.
     * @param opacity the opacity value (from 0 to 100).
     */
    public void setOpacity(int percent)
    {
        if (percent > 100) this.opacity = 100;
        else if (percent < 0) this.opacity = 0;
        else this.opacity = percent;

        if (this.opacity == 100) super.setOpaque(true);
        else super.setOpaque(false);

        this.repaint();
    }

    public boolean isRoundCorners()
    {
        return roundCorners;
    }

    public void setRoundCorners(boolean roundCorners)
    {
        this.roundCorners = roundCorners;
    }

    public int getRoundSize()
    {
        return roundSize;
    }

    public void setRoundSize(int roundSize)
    {
        this.roundSize = roundSize;
    }

    @Override
    public void setOpaque(boolean b)
    {
        if (b) this.setOpacity(100);
        else this.setOpacity(0);
        super.setOpaque(b);
    }
   


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSlider1 = new javax.swing.JSlider();

        setDoubleBuffered(false);

        jButton1.setText("jButton1");

        jLabel1.setText("jLabel1");

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(350, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    // TESTS

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
                TranslucentPanel.testPanel();
            }
        };
        SwingUtilities.invokeLater(frameCreationAndShowingRunnable);
    }

    private static void testPanel()
    {
        JFrame f = new JFrame("TranslucentPanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 450);
        JPanel checkerboard = new GradientStagePanel(new Color(170, 170, 255),
            new Color(20, 20, 80));
        TranslucentPanel tPanel = new TranslucentPanel(40);
        tPanel.setBackground(Color.RED.brighter());
        tPanel.setPreferredSize(new Dimension(300, 400));
        checkerboard.add(tPanel);

        TranslucentPanel tPanel2 = new TranslucentPanel(30);
        tPanel2.setContentTranslucentToo(true);
        tPanel2.setBackground(Color.BLUE);
        tPanel2.setPreferredSize(new Dimension(220, 400));
        checkerboard.add(tPanel2);
        f.add(checkerboard);
        f.setVisible(true);
    }
}
