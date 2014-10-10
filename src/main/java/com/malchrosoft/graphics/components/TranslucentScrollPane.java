/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JScrollPane;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TranslucentScrollPane extends JScrollPane
{
    public TranslucentScrollPane(Component view)
    {
        super(view);
        this.setOpaque(false);
        this.getViewport().setOpaque(false);
    }

    @Override
    public void paint(Graphics g)
    {
        this.paintChildren(g);
    }
}
