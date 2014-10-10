/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.text;

import com.malchrosoft.debug.Log;
import java.awt.Color;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ColorUtils
{
    /**
     * Creates a new color from the RGB string values
     * @param colorValues the RGB string values
     * @return the created color
     */
    public static Color createColorFromRGBStringValues(String colorValues)
    {
        int[] comp = rgbColorValue(colorValues);
        return new Color(comp[0], comp[1], comp[2]);
    }

    /**
     * Returns the color int value from a string containing the color values
     * seperates by "," without space.
     * @param colorValue the color values as string separated by ","
     * @return a table of integer with each value
     */
    public static int[] rgbColorValue(String colorValue)
    {
        if (colorValue.isEmpty() || colorValue.length() < 5 ||
            colorValue.length() > 11) return new int[] {0, 0, 0};
        String colorTMP = new String(colorValue);
        int pos = 0;
        int r,g,b; r = g = b = 0;
        try
        {
            pos = colorTMP.indexOf(",");
            r = Integer.parseInt(colorTMP.substring(0, pos));
            colorTMP = colorTMP.substring(pos+1);
            pos = colorTMP.indexOf(",");
            g = Integer.parseInt(colorTMP.substring(0, pos));
            colorTMP = colorTMP.substring(pos+1);
            b = Integer.parseInt(colorTMP);
        }
        catch (NumberFormatException e)
        {
            Log.debug("NumberFormatException : Chaine (000,000,000) = "+colorValue);
            e.printStackTrace();
        }

        if (pos == 0) return new int[]{0,0,0};
        return new int[]{r, g, b};
    }

    /**
     * Returns the RGB color values in a string with each value separated by a ','.
     * @param rgb the color values
     * @return the string built
     */
    public static String colorToRGBString(Color c)
    {
        return String.valueOf(c.getRed()) + "," +
            String.valueOf(c.getGreen()) + "," +
            String.valueOf(c.getBlue());
    }
}
