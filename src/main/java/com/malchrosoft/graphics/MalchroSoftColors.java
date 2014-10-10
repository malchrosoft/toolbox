/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics;

import java.awt.Color;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class MalchroSoftColors extends Color
{

    /**
     * Build a new MalchroSoft color
     * @param r red component
     * @param g green component
     * @param b bleu component
     */
    public MalchroSoftColors(int r, int g, int b)
    {
        super(r, g, b);
    }

    public static final Color DARK_GREEN = new Color(0, 109, 0);
    public static final Color DARK_RED = new Color(170, 0, 0);
    public static final Color DARK_BLUE = new Color(0, 0, 130);
    public static final Color DARK_MAGENTA = new Color(140, 0, 140);
    public static final Color DARK_ORANGE = new Color(255, 140, 0);
    public static final Color DARK_FIRE_BRICK = new Color(178, 34, 34);

    public static final Color LIGHT_BROWN = new Color(240, 220, 180);
    public static final Color LIGHT_BLEU = new Color(200, 215, 255);
    public static final Color LIGHT_GREEN = new Color(150, 250, 150);

    public static final Color LAVENDER = new Color(230, 230, 250);
    public static final Color GHOSTWHITE = new Color(248, 248, 255);
    public static final Color BEIGE = new Color(245, 245, 220);
    
}
