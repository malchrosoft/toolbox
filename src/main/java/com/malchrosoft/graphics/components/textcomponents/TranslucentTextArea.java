/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.graphics.components.textcomponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextArea;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TranslucentTextArea extends JTextArea
{
    private boolean totalyTranslucent;

    public TranslucentTextArea()
    {
        this(false);
    }
    
    public TranslucentTextArea(boolean totalyTranslucent)
    {
        this.setOpaque(false);
        this.totalyTranslucent = totalyTranslucent;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2D = ((Graphics2D) g);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paint(g2D);
    }

//    // TESTS
//    public static void main(String[] args)
//    {
//        JFrame f = new JFrame("TranslucentTextArea - TESTS");
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setSize(240, 180);
//
//        GradientStagePanel gsp = new GradientStagePanel(
//            Color.RED.brighter().brighter().brighter(),
//            Color.RED.darker().darker().darker());
//        TranslucentPanel tp = new TranslucentPanel(25, true, false);
//        tp.setBackground(Color.green);
//        TranslucentTextArea ta = new TranslucentTextArea();
//        TranslucentScrollPane sp = new TranslucentScrollPane(ta);
//        gsp.setLayout(null);
//        tp.setBounds(10, 10, 200, 120);
//        sp.setBounds(4, 4, 192, 112);
//        tp.add(sp);
//        gsp.add(tp);
//        f.add(gsp);
//        f.setVisible(true);
//    }
}
