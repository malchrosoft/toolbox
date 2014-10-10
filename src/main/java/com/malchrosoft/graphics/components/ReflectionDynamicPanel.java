/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * ReflectionDynamicPanel.java
 *
 * Created on 23 janv. 2009, 14:50:58
 */
package com.malchrosoft.graphics.components;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ReflectionDynamicPanel extends javax.swing.JPanel
{
    private JPanel contentPane;
    private boolean initialized;
    private BufferedImage contentBuffer;
    private BufferedImage reflectionBuffer;
    private Graphics2D contentGraphics;
    private Graphics2D reflectionGraphics;
    private GradientPaint alphaMask;
    private float length;
    private float opacity;
    private int splitThickness;
    private static final float defaultLength = 0.65f;
    private static final float defaultOpacity = 0.75f;
    private static final int defaultSplitThickness = 4;

    /** 
     * Creates new form ReflectionDynamicPanel. <br/>
     * By default : <ul>
     * <li>the default length ratio = 0.65f</li>
     * <li>the default opacity ratio = 0.75f</li>
     * <li>the default split thickness = 4</li></ul>
     */
    public ReflectionDynamicPanel()
    {
        this(defaultSplitThickness);
    }

    /**
     * Creates a reflection panel with a specified separator thickness, this is
     * to say the distance between the original content and the reflect.  <br/>
     * By default : <ul>
     * <li>the default length ratio = 0.65f</li>
     * <li>the default opacity ratio = 0.75f</li></ul>
     * @param splitThickness the separator thickness
     */
    public ReflectionDynamicPanel(int splitThickness)
    {
        this(defaultLength, defaultOpacity, splitThickness);
    }

    /**
     * Creates a reflection panel with a specified length of the reflect.  <br/>
     * By default : <ul>
     * <li>the default opacity ratio = 0.75f</li>
     * <li>the default split thickness = 4</li></ul>
     * @param reflectLenghtRatio the length of the reflect in percentage
     */
    public ReflectionDynamicPanel(float reflectLengthRatio)
    {
        this(reflectLengthRatio, defaultOpacity, defaultSplitThickness);
    }

    /**
     * Creates a reflection panel with specified length ratio (the distance
     * between the content and its reflect), opacity ratio (opacity in
     * percentage) and the separator thickness.
     * @param reflectLengthRatio the reflect length ratio
     * @param opacityRatio the opacity ratio
     * @param splitThickness the separator thickness
     */
    public ReflectionDynamicPanel(float reflectLengthRatio, float opacityRatio,
        int splitThickness)
    {
        super(new GridBagLayout());
        this.initValues();
        this.buildContentPane();
        this.buildFiller();
        this.setSplitThickness(splitThickness);
        this.setOpacity(opacityRatio);
        this.setLength(reflectLengthRatio, true);
        this.initialized = true;
    }

    private void initValues()
    {
        this.initialized = false;
        this.contentBuffer = null;
        this.contentGraphics = null;
        this.setOpaque(false);
        this.installRepaintManager();
    }

    /**
     * Builds the content pane [Content]
     */
    private void buildContentPane()
    {
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setOpaque(false);

        this.add(this.contentPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Builds the filler [Reflexion part]
     */
    private void buildFiller()
    {
        this.add(Box.createVerticalBox(),
            new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 0, 0), 0, 0));
    }

    @Override
    public Dimension getPreferredSize()
    {
        Dimension size = this.contentPane.getPreferredSize();
        size.height *= 1.0f + this.length;
        return size;
    }

    public Dimension getContentPaneSize()
    {
        return this.contentPane.getSize();
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index)
    {
        if (this.initialized && comp != this.contentPane)
        {
            this.contentPane.add(comp, constraints, index);
//System.out.println("ReflectionDynamicPanel : addImpl(" + comp + ", " + constraints +
//    ", " + index + "); ");
        }
        else
        {
            super.addImpl(comp, constraints, index);
        }

    }

    @Override
    public void remove(int index)
    {
        this.contentPane.remove(index);
    }

    @Override
    public void removeAll()
    {
        this.contentPane.removeAll();
    }

    @Override
    public void setLayout(LayoutManager mrg)
    {
        if (this.initialized)
        {
            this.contentPane.setLayout(mrg);
        }
        else
        {
            super.setLayout(mrg);
        }
    }

    @Override
    public void paint(Graphics g)
    {
        this.paintContent(g);
        this.paintReflexion(g);
    }

    private void paintContent(Graphics g)
    {
        if (this.contentBuffer == null ||
            this.contentBuffer.getWidth() != this.contentPane.getWidth() ||
            this.contentBuffer.getHeight() != this.contentPane.getHeight())
        {
            if (this.contentBuffer != null)
            {
                this.contentBuffer.flush();
                this.contentGraphics.dispose();
            }
            this.contentBuffer =
                GraphicsUtilities.createCompatibleTranslucentImage(
                this.contentPane.getWidth(), this.contentPane.getHeight());
            this.contentGraphics = this.contentBuffer.createGraphics();
        }

        Graphics2D g2 = this.contentGraphics;
        g2.clipRect(this.contentPane.getX(), this.contentPane.getY(),
            this.contentPane.getWidth(), this.contentPane.getHeight());

        // because the content buffer is reused, the image must be cleared
        g2.setComposite(AlphaComposite.Clear);
        Rectangle clip = g.getClipBounds();
        g2.fillRect(clip.x, clip.y, clip.width, clip.height);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(g.getColor());
        g2.setFont(g.getFont());

        super.paint(g2);

        g.drawImage(this.contentBuffer, 0, 0, null);
    }

    private void paintReflexion(Graphics g)
    {
        int width = this.contentPane.getWidth();
        int height = (int) (this.contentPane.getHeight() * this.length);
        this.createReflection(g, width, height);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(1.0, -1.0);
        g2.drawImage(this.reflectionBuffer, 0, -this.contentPane.getHeight() -
            height, null);
        g2.dispose();
    }

    private void createReflection(Graphics g, int width, int height)
    {
        if (this.reflectionBuffer == null ||
            this.reflectionBuffer.getWidth() != width ||
            this.reflectionBuffer.getHeight() != height)
        {
            if (this.reflectionBuffer != null)
            {
                this.reflectionBuffer.flush();
                this.reflectionGraphics.dispose();
            }

            this.reflectionBuffer =
                GraphicsUtilities.createCompatibleImage(this.contentBuffer,
                width, height);
            this.reflectionGraphics = this.reflectionBuffer.createGraphics();

            this.alphaMask = new GradientPaint(0.0f, 0.0f,
                new Color(0.0f, 0.0f, 0.0f, 0.0f),
                0.0f, height, new Color(0.0f, 0.0f, 0.0f, this.opacity), true);
        }

        int yOffset = this.contentPane.getHeight() - height;
        Rectangle clip = g.getClipBounds();

        Graphics2D g2 = this.reflectionGraphics;
        g2.setClip(clip.x, clip.y - yOffset, clip.width, clip.height);

        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(clip.x, clip.y - yOffset, clip.width, clip.height);
        g2.setComposite(AlphaComposite.SrcOver);

        g2.translate(0, -yOffset);
        g2.drawImage(this.contentBuffer, 0, -this.getSplitThickness(), null);
        g2.translate(0, yOffset);

        g2.setComposite(AlphaComposite.DstIn);
        g2.setPaint(this.alphaMask);
        g2.fillRect(clip.x, clip.y - yOffset, clip.width, clip.height);
    }

    /**
     * Install reflection repaint manager
     */
    private void installRepaintManager()
    {
        ReflectionRepaintManager manager = new ReflectionRepaintManager();
        RepaintManager.setCurrentManager(manager);
    }

    /**
     * Returns the reflect length ratio (percentage).
     * @return the reflect length ratio
     */
    public float getLenght()
    {
        return length;
    }

    /**
     * Sets the reflect lenth.
     * @param length the reflect length
     * @param isPercentage the boolean for : this value is a percentage. If
     * it is not a percentage, the value is convert in a percentage value.
     */
    public void setLength(float length, boolean isPercentage)
    {
        if (!isPercentage)
        {
            this.length = length * 100 / this.getHeight();
        }
        else
        {
            this.length = length;
        }
    }

    /**
     * Returns the opacity ratio value.
     * @return the opacity ratio value
     */
    public float getOpacity()
    {
        return opacity;
    }

    /**
     * Sets the opacity ratio value (percentage).
     * @param opacity the opacity
     */
    public void setOpacity(float opacity)
    {
        this.setOpacity(opacity, true);
    }

    /**
     * Sets the opacity value.
     * @param opacityValue the opacity value
     * @param isPercentage the boolean for : the specified value is a percentage
     */
    public void setOpacity(float opacityValue, boolean isPercentage)
    {
        if (isPercentage)
        {
            this.opacity = opacityValue;
        }
        else
        {
            this.opacity = opacityValue / 100;
        }
    }

    /**
     * Returns the separator thickness.
     * @return the separator thickness
     */
    public int getSplitThickness()
    {
        return splitThickness;
    }

    /**
     * Sets the separator thickness.
     * @param splitThickness the separator thickness
     */
    public void setSplitThickness(int splitThickness)
    {
        this.splitThickness = splitThickness;
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
            .addGap(0, 438, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private class ReflectionRepaintManager extends RepaintManager
    {
        @Override
        public void addDirtyRegion(JComponent c, int x, int y, int width,
            int height)
        {
//            Rectangle dirtyRegion = this.getDirtyRegion(c);

            int lastDeltaX = c.getX();
            int lastDeltaY = c.getY();

            Container parent = c.getParent();
            // As long as we can find a parent
            while (parent instanceof JComponent)
            {
                // if the parent is not visible
                // neither is the compenent
                if (!parent.isVisible())
                {
                    return;
                }

                if (parent instanceof ReflectionDynamicPanel)
                {
                    x += lastDeltaX;
                    y += lastDeltaY;

                    // extends the dirty region to cover the corresponding area in
                    // the reflection
                    int gap = contentPane.getHeight() - height - y;
                    height += 2 * gap + height;

                    lastDeltaX = lastDeltaY = 0;

                    // the component that needs to be repainted is now the
                    // ReflectionDynamicPanel
                    c = (JComponent) parent;
                }

                // calculates the location delta between the parent and the
                //dirty compenent
                lastDeltaX += parent.getX();
                lastDeltaY += parent.getY();

                parent = parent.getParent();
            }


            // posts the repaint request in the EDT
            super.addDirtyRegion(c, x, y, width, height);
        }
    }

    // TESTS
    public static void main(String[] args)
    {
        Runnable frameCreationAndShowingRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                ReflectionDynamicPanel.testReflectionPanel();
            }
        };
        SwingUtilities.invokeLater(frameCreationAndShowingRunnable);
    }

    public static void testReflectionPanel()
    {
        JFrame f = new JFrame("ReflectionPanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 250);
        ReflectionDynamicPanel p = new ReflectionDynamicPanel();
        GradientStagePanel gsp = new GradientStagePanel(60, true);
        TranslucentPanel tPanel = new TranslucentPanel(40);
        tPanel.setBackground(Color.RED.brighter());
        tPanel.setPreferredSize(new Dimension(300, 90));
        p.add(tPanel);
        gsp.add(p);
        f.add(gsp);
        f.setVisible(true);
    }
}
