/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.graphics.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class GradientStagePanel extends JPanel
{
    /**
     * The stage altitude
     */
    private float stageAltitude;
    private boolean stageAltitudeIsPercentage;

    private Color topColor;
    private Color bottomColor;
    private Color backColor;
    private Color frontColor;

    /**
     * Creates a gradient stage panel. By default the altitude is 1/4 from
     * the bottom of the panel.
     */
    public GradientStagePanel()
    {
        this(25, true);
    }

    /**
     * Creates a gradient stage panel.<br/>
     * This panel draws a stage by a gradient for the stage and a gradient for
     * the wall in background with gray gradient.
     * @param altitude the stage altitude.
     * @param isPercentage the altitude value is a percentage.
     */
    public GradientStagePanel(int altitude, boolean isPercentage)
    {
       this(altitude, isPercentage, Color.LIGHT_GRAY, Color.DARK_GRAY);
    }

    /**
     * Creates a gradient stage panel. By default the altitude is 25% from the
     * bottom.
     * @param lightColor the light color (top and front)
     * @param darkColor the dark color (bottom and back)
     */
    public GradientStagePanel(Color lightColor, Color darkColor)
    {
        this(25, true, lightColor, darkColor);
    }

    /**
     * Creates a gradient stage panel with 4 colors. By default the altitude is
     * 25% from the bottom.
     * @param topColor the top color
     * @param bottomColor the bottom color
     * @param backColor the back color
     * @param frontColor the front color
     */
    public GradientStagePanel(Color topColor, Color bottomColor,
        Color backColor, Color frontColor)
    {
        this(25, true, topColor, bottomColor, backColor, frontColor);
    }

    /**
     * Creates a gradient stage panel with altitude and 2 colors.
     * @param altitude the altitude
     * @param isPercentage the boolean witch tell if the altitude value is a
     * percentage of the height or not
     * @param lightColor the light color (top and front)
     * @param darkColor the dark color (bottom and back)
     */
    public GradientStagePanel(int altitude, boolean  isPercentage,
        Color lightColor, Color darkColor)
    {
        this(altitude, isPercentage, lightColor, darkColor,
            darkColor.darker(), lightColor.brighter());
    }

    /**
     * Creates a gradient stage panel with an altitude and 4 colors.
     * @param altitude the altitude from the bottom
     * @param isPercentage tha boolean value : the altitude value is a
     * percentage
     * @param topColor the top color
     * @param bottomColor the bottom color
     * @param backColor the back color
     * @param frontColor the front color
     */
    public GradientStagePanel(int altitude, boolean isPercentage,
        Color topColor, Color bottomColor, Color backColor, Color frontColor)
    {
        this.setSize(100, 100);
        this.setStageAltitude(altitude, isPercentage);
        this.setTopColor(topColor);
        this.setBottomColor(bottomColor);
        this.setBackColor(backColor);
        this.setFrontColor(frontColor);
    }

    private float getStageAltitude()
    {
        return stageAltitude;
    }

    /**
     * Sets the stage altitude.
     * @param stageAltitude the stage altitude value
     * @param isPercentage the boolean witch tell if the altitude value is a
     * percentage.
     */
    public void setStageAltitude(int stageAltitude, boolean isPercentage)
    {
        if (stageAltitude < 10 && !isPercentage) stageAltitude = 10;
        else if (stageAltitude > 90 && isPercentage) stageAltitude = 90;
        else if (stageAltitude < 10 && isPercentage) stageAltitude = 10;
        this.stageAltitude = stageAltitude;
        this.stageAltitudeIsPercentage = isPercentage;
    }

    /**
     * Returns stage altitude boolean value : <b>true</b> if this value is a
     * percentage, <b>false</b> otherwise.
     * @return <b>true</b> if this value is a percentage, <b>false</b> otherwise
     */
    public boolean stageAltitudeIsPercentage()
    {
        return stageAltitudeIsPercentage;
    }

    /**
     * Returns the stage altitude percentage : the percentage of the altitude
     * from the bottom of this panel.
     * @return the stage altitude percentage
     */
    public float getCalculatedStageAltitudePercentage()
    {
        if (this.stageAltitudeIsPercentage)
        {
            return this.getStageAltitude();
        }
        else return stageAltitude/this.getHeight()*100;
    }

    /**
     * Returns the stage altitude value calculated.
     * @return the stage altitude value calculated
     */
    public float getCalculatedStageAltitudeValue()
    {
        if (this.stageAltitudeIsPercentage)
        {
            return this.getHeight()*this.getStageAltitude()/100;
        }
        else return this.getStageAltitude();
    }

    /**
     * Returns the stage altitude position.
     * @return the stage altitude position
     */
    public float getCalculatedStageAltitudePosition()
    {
        if (this.stageAltitudeIsPercentage)
        {
            return this.getHeight()-this.getCalculatedStageAltitudeValue();
        }
        else return this.getHeight() - stageAltitude;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        GradientPaint bg = new GradientPaint(0f, 0f,
            this.getTopColor(), 0f,
            this.getCalculatedStageAltitudePosition()-10,
            this.getBottomColor());
        g2d.setPaint(bg);
        g2d.fillRect(0,0, this.getWidth(),
            (int) this.getCalculatedStageAltitudePosition()-10);

        bg = new GradientPaint(0f,
            (int) this.getCalculatedStageAltitudePosition()-10,
            this.getBottomColor(), 0f,
            this.getCalculatedStageAltitudePosition(),
            this.getBackColor());
        g2d.setPaint(bg);
        g2d.fillRect(0,(int) this.getCalculatedStageAltitudePosition()-10,
            this.getWidth(), (int) this.getCalculatedStageAltitudePosition());


        bg = new GradientPaint(0f, this.getCalculatedStageAltitudePosition(),
            this.getBackColor(), 0f, this.getHeight(), this.getFrontColor());
        g2d.setPaint(bg);
        g2d.fillRect(0,(int) this.getCalculatedStageAltitudePosition(),
            this.getWidth(),(int) this.getCalculatedStageAltitudeValue());
    }

    public Color getBackColor()
    {
        return backColor;
    }

    public void setBackColor(Color backColor)
    {
        this.backColor = backColor;
    }

    public Color getBottomColor()
    {
        return bottomColor;
    }

    public void setBottomColor(Color bottomColor)
    {
        this.bottomColor = bottomColor;
    }

    public Color getFrontColor()
    {
        return frontColor;
    }

    public void setFrontColor(Color frontColor)
    {
        this.frontColor = frontColor;
    }

    public Color getTopColor()
    {
        return topColor;
    }

    public void setTopColor(Color topColor)
    {
        this.topColor = topColor;
    }




    // test
    public static void main(String[] argv)
    {
        Runnable frameCreationAndShowingRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                GradientStagePanel.testPanel();
            }
        };
        SwingUtilities.invokeLater(frameCreationAndShowingRunnable);
    }

    private static void testPanel()
    {
        JFrame f = new JFrame("TEST GradientStagePanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        GradientStagePanel p = new GradientStagePanel(55, true);
        f.add(p);
        f.setVisible(true);
    }
}
