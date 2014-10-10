/*
 * GraphicThemeUtilities.java
 *
 * Created on 16 avril 2007, 15:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.malchrosoft.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.painter.AbstractLayoutPainter.HorizontalAlignment;
import org.jdesktop.swingx.painter.AbstractLayoutPainter.VerticalAlignment;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.ImagePainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.TextPainter;
import org.jdesktop.swingx.painter.effects.GlowPathEffect;

/**
 *
 * @author Aymeric
 */
public class GraphicThemeUtilities
{
    /**
     * Construit un painter de text avec un effet de neon.
     * @param text le text
     * @param x la position en x
     * @param y la position en y
     * @param color la couleur du text
     * @param font la police � appliquer
     * @return le TextPainter  contruit
     */
    public static TextPainter buildGlowTextPainter(String text, int x, int y,
        Color color, Font font)
    {
        return buildGlowTextPainter(text, x, y, color, font, true);
    }

    /**
     * Builds a text painter with a glow effect.
     * @param text the text
     * @param x the x position
     * @param y the y position
     * @param color the text [and glow] color
     * @param font the text font
     * @param glowIsWhite true if the glow must be white, false if it has the
     * same color of the text.
     * @return the TextPainter built
     */
    public static TextPainter buildGlowTextPainter(String text, int x, int y,
        Color color, Font font, boolean glowIsWhite)
    {
        if (glowIsWhite)
            return buildGlowTextPainter(text, x, y, color, Color.WHITE, font);
        else
            return buildGlowTextPainter(text, x, y, color, color, font);
    }

    /**
     * Builds a text painter with a glow effect.
     * @param text the text
     * @param x the x position
     * @param y the y position
     * @param textColor the text color
     * @param glowColor the glow color
     * @param font the text font
     * @return the TextPainter built
     */
    public static TextPainter buildGlowTextPainter(String text, int x, int y,
        Color textColor, Color glowColor, Font font)
    {
        TextPainter textPainter = new TextPainter(text, font, textColor);
        GlowPathEffect gPE = new GlowPathEffect();
        gPE.setBrushColor(glowColor);
        textPainter.setAreaEffects(gPE);
        textPainter.setVerticalAlignment(TextPainter.VerticalAlignment.TOP);
        textPainter.setHorizontalAlignment(TextPainter.HorizontalAlignment.LEFT);
        textPainter.setInsets(new Insets(y, x, 0, 0));
        textPainter.setAntialiasing(true);

        return textPainter;
    }

    public static JXPanel buildGlowedTextPanel(String text, int x, int y,
        int width, int height, Color textColor, Color glowColor, Font font)
    {
        return buildGlowedTextPanel(text, new Rectangle(x, y, width, height),
            textColor, glowColor, font);
    }

    public static JXPanel buildGlowedTextPanel(String text, Rectangle r,
        Color textColor, Color glowColor, Font font)
    {
        JXPanel panel = new JXPanel();
        panel.setBounds(r);
        panel.setOpaque(false);
        panel.setBackgroundPainter(buildGlowTextPainter(" " + text, 0,
            0, textColor, glowColor, font));
        return panel;
    }

    public static Painter buildGlossTextPainter(String text, Color textColor,
        Font textFont, Color backgroundColor)
    {
        return buildGlossTextPainter(text, textColor, textFont, backgroundColor,
            VerticalAlignment.CENTER, HorizontalAlignment.CENTER);
    }

    public static Painter buildGlossTextPainter(String text, Color textColor,
        Font textFont, Color backgroundColor, VerticalAlignment verticalAlignment,
        HorizontalAlignment horizontalAlignment)
    {
        TextPainter tp = new TextPainter(text, textFont, textColor);
        tp.setVerticalAlignment(verticalAlignment);
        tp.setHorizontalAlignment(horizontalAlignment);
        tp.setAntialiasing(true);
        Painter bp = GraphicThemeUtilities.getColoredGlossPainter(
            backgroundColor);
        return new CompoundPainter(bp, tp);
    }

    /**
     * Renvoie un border avec un effet de neon en arri�re plan
     * @return le DropShadowBorder contruit
     */
    public static DropShadowBorder getNeonBorder()
    {
        return new DropShadowBorder(Color.WHITE, 6, 0.9f, 5,
            true, true, true, true);
    }

    /**
     * Renvoie un Painter avec un effet de reflet et des raillures en d�grad�
     * @param stripeStarPosition la position de d�part des raillures
     * @param stripeEndPosition la position de fin des raillures
     * @return le painter contruit
     */
    public static Painter getBlackGlossStripeBackgroundPainter(
        int stripeStarPosition, int stripeEndPosition)
    {
        Painter background = new MattePainter(Color.BLACK);
        if (stripeEndPosition == stripeStarPosition && stripeEndPosition == 0)
        {
            return GraphicThemeUtilities.getColoredGlossPainter(Color.BLACK);
        }

        PinstripePainter pinstripes = new PinstripePainter(45);
        pinstripes.setPaint(new GradientPaint(
            new Point(stripeStarPosition, stripeStarPosition), Color.LIGHT_GRAY,
            new Point(stripeEndPosition, stripeEndPosition), Color.BLACK));
        pinstripes.setSpacing(10);
        return new CompoundPainter(background, pinstripes, new GlossPainter());
    }

    /**
     * Retourne un painter de couleur avec un effet de gloss
     * @param couleurBase la couleur de base
     * @return le Painter
     */
    public static Painter getColoredGlossPainter(Color couleurBase)
    {
        Painter bg = new MattePainter(couleurBase);
        GlossPainter gp = new GlossPainter();
        return new CompoundPainter(bg, gp);

    }

    /**
     * Renvoie un Painter avec un effet de dégradé vertical
     * @param x1 
     * @param y1
     * @param color1 la couleur de départ (haut)
     * @param x2
     * @param y2
     * @param color2 la couleur d'arrivée (bas)
     * @return le Painter
     */
    public static Painter getDegradeVertical(int x1, int y1, Color color1,
        int x2, int y2, Color color2)
    {
        MattePainter p = new MattePainter(new GradientPaint(new Point((x2 - x1) / 2, y1), color1,
            new Point((x2 - x1) / 2, y2), color2));
        p.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return p;
    }

    /**
     * Renvoie un Painter avec un effet de dégradé vertical
     * @param c Le composant récepteur
     * @param color1 la couleur de départ (Haut)
     * @param color2 la couluer d'arrivée (Bas)
     * @return le Painter de dégradé défini.
     */
    public static Painter getDegradeVertical(Component c,
        Color color1, Color color2)
    {
        return GraphicThemeUtilities.getDegradeVertical(c.getX(), c.getY(), color1,
            c.getWidth(), c.getHeight(), color2);
    }

    /**
     * Contruit un ImagePainter avec un effet de neon en arri�re plan
     * @param imageClassPath le chemin de l'image dans le projet
     * @param x la position en x
     * @param y la position en y
     * @return le ImagePainter contruit
     */
    public static ImagePainter buildGlowImagePainter(String imageClassPath,
        int x, int y)
    {
        Image image;
        ImagePainter imagePainter = null;
        try
        {
            image = ImageFile.buildImageFromPath(imageClassPath);
            BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            imagePainter = new ImagePainter();
            imagePainter.setImage(bufferedImage);
            imagePainter.setAntialiasing(true);

            imagePainter.setAreaEffects(new GlowPathEffect());
            imagePainter.setVerticalAlignment(TextPainter.VerticalAlignment.CENTER);
            imagePainter.setHorizontalAlignment(TextPainter.HorizontalAlignment.CENTER);
            imagePainter.setInsets(new Insets(y, x, 0, 0));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return imagePainter;
    }
}
