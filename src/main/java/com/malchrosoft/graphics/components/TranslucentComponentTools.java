/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.graphics.components;

import java.awt.Component;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TranslucentComponentTools
{
    public synchronized static BufferedImage createComponentImageIfNecessary(
        Component component, BufferedImage image)
    {
        if (image == null || image.getWidth() != component.getWidth() ||
            image.getHeight() != component.getHeight())
        {
            image = component.getGraphicsConfiguration().
                createCompatibleImage(component.getWidth(),
                component.getHeight());
        }
        return image;
    }

    /**
     * Returns the alpha value from the opacity percentage value.
     * @param opacityPercentValue the opacity percentage value
     * @return the alpha value from the opacity percentage value
     */
    public static float getAlphaValue(float opacityPercentageValue)
    {
        return opacityPercentageValue / 100.0f;
    }

    /**
     * Returns the alpha value from the opacity percentage value.
     * @param opacityPercentValue the opacity percentage value
     * @return the alpha value from the opacity percentage value
     */
    public static float getAlphaValue(int opacityPercentageValue)
    {
        return getAlphaValue((float) opacityPercentageValue);
    }
    
}
