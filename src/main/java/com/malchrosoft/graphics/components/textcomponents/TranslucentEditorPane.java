/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components.textcomponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JEditorPane;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TranslucentEditorPane extends JEditorPane
{
    private boolean totalyTranslucent;

    public TranslucentEditorPane()
    {
        this(false);
    }

    public TranslucentEditorPane(boolean totalyTranslucent)
    {
        this.init(totalyTranslucent);
    }

    public TranslucentEditorPane(String type, String text)
    {
        super(type, text);
        this.init(false);
    }

    private void init(boolean totalyTranslucent)
    {
        this.setOpaque(false);
        this.totalyTranslucent = totalyTranslucent;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paint(g2d);
    }
}
