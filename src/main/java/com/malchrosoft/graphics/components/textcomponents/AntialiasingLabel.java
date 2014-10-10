/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components.textcomponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class AntialiasingLabel extends JLabel
{
    public AntialiasingLabel()
    {
        super();
        this.init();
    }

    public AntialiasingLabel(Icon image)
    {
        super(image);
        this.init();
    }

    public AntialiasingLabel(Icon image, int horizontalAlignment)
    {
        super(image, horizontalAlignment);
        this.init();
    }

    public AntialiasingLabel(String text)
    {
        super(text);
        this.init();
    }

    public AntialiasingLabel(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
        this.init();
    }

    public AntialiasingLabel(String text, Icon icon, int horizontalAlignment)
    {
        super(text, icon, horizontalAlignment);
        this.init();
    }

    private void init()
    {
        this.setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2D = ((Graphics2D) g);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paint(g2D);
    }
}
