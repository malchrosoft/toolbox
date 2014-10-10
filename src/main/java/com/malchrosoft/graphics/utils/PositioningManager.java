/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.utils;

import java.awt.Component;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class PositioningManager
{
    /**
     * Center the component according to the owner
     * @param c the component
     * @param owner the owner
     */
    public static void center(Component c, Component owner)
    {
        c.setBounds(
            calculateCentered((int) owner.getBounds().getX(), 
            (int) owner.getBounds().getWidth(), (int) c.getBounds().getWidth()),
            calculateCentered((int) owner.getBounds().getY(),
            (int) owner.getBounds().getHeight(), (int) c.getBounds().getHeight()),
            (int) c.getBounds().getWidth(), (int) c.getBounds().getHeight());
    }

    /**
     * Center left the component according to the owner
     * @param c the component
     * @param owner the owner
     */
    public static void centerLeft(Component c, Component owner)
    {
        c.setBounds(owner.getX(),
            calculateCentered(owner.getY(), owner.getHeight(), c.getHeight()),
            c.getWidth(), c.getHeight());
    }

    /**
     * Center right the component according to the owner
     * @param c the component
     * @param owner the owner
     */
    public static void centerRight(Component c, Component owner)
    {
        c.setBounds(owner.getWidth() + owner.getX() - c.getWidth(),
            calculateCentered(owner.getY(), owner.getHeight(), c.getHeight()),
            c.getWidth(), c.getHeight());
    }

    /**
     * Put the component to corner top left of the owner
     * @param c the component
     * @param owner the owner
     */
    public static void toCornerTopLeft(Component c, Component owner)
    {
        c.setBounds(owner.getX(), owner.getY(), c.getWidth(), c.getHeight());
    }

    /**
     * Put the component to corner top right of the owner
     * @param c the component
     * @param owner the owner
     */
    public static void toCornerTopRight(Component c, Component owner)
    {
        c.setBounds((int) (owner.getBounds().getWidth() + owner.getBounds().getX() -
            c.getBounds().getWidth()),
            (int) owner.getBounds().getY(),
            c.getWidth(), c.getHeight());
    }

    /**
     * Put the component to the corner down left of the owner
     * @param c the component
     * @param owner the owner
     */
    public static void toCornerDownLeft(Component c, Component owner)
    {
        c.setBounds(owner.getX(), owner.getHeight() + owner.getY() - c.getHeight(),
            c.getWidth(), c.getHeight());
    }

    /**
     * Put the component to the corner down right of the owner
     * @param c the component
     * @param owner the owner
     */
    public static void toCornerDownRight(Component c, Component owner)
    {
        c.setBounds(owner.getWidth() + owner.getX() - c.getWidth(),
            owner.getHeight() + owner.getY() - c.getHeight(), c.getWidth(), c.getHeight());
    }

    public static void underOwner(Component c, Component owner)
    {
        c.setBounds(calculateCentered((int) owner.getBounds().getX(),
            (int) owner.getBounds().getWidth(), (int) c.getBounds().getWidth()),
            owner.getHeight() + owner.getY() + 4, c.getWidth(), c.getHeight());
    }


    /**
     * Calculate the centered value for the position (x or y)
     * @param ownerSide the owner side position
     * @param ownerSize the owner size (width or height select case)
     * @param cSize the component size (width or height select case)
     * @return the position for
     */
    public static int calculateCentered(int ownerSide, int ownerSize,
        int cSize)
    {
        return (int) (ownerSide + ((ownerSize / 2) - (cSize / 2)));
    }
}
