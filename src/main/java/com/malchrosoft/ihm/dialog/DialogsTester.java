/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import com.malchrosoft.debug.Log;
import com.malchrosoft.graphics.components.TranslucentPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class DialogsTester
{
     /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Log.get().setLevel(Log.Level.DEBUG);
                JFrame f = new JFrame("SimpleSMessageFrame");
                f.setSize(new Dimension(600, 400));
                f.setVisible(true);
                f.setContentPane(new TranslucentPanel(50));
                f.getContentPane().setBackground(Color.BLUE);

                SimpleMessageDialog dialog = new SimpleMessageDialog(
                    "Message - TEST 1",
                    "Message with some details... \nIt's better !", "www.malchrosoft.com", f);
                SimpleCriticalMessageDialog dialog2 = new SimpleCriticalMessageDialog(
                    "Critical - TEST 2",
                    "Message with some details... \nIt's better !", "www.malchrosoft.com", f);
                SimpleInformationMessageDialog dialog3 = new SimpleInformationMessageDialog(
                    "Information - TEST 3",
                    "Message with some details... \nIt's better !", "www.malchrosoft.com", f);
                SimpleWarningMessageDialog dialog4 = new SimpleWarningMessageDialog(
                    "Warning - TEST 4",
                    "Message with some details... \nIt's better !", "www.malchrosoft.com", f);

                f.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                dialog2.setVisible(true);
                dialog3.setVisible(true);
                dialog4.setVisible(true);
                System.exit(0);
            }
        });
    }
}
