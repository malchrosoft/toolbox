/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import com.malchrosoft.graphics.components.TranslucentPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class SimpleWarningMessageDialog extends SimpleMessageDialog
{
    /**
     * Creates a new SimpleWarningMessageDialog
     * @param title the title
     * @param message the message
     * @param comment the commnet (must be short)
     * @param parent the parent component
     */
    public SimpleWarningMessageDialog(String title, String message,
        String comment, Component parent)
    {
        super(title, message, comment, parent);
        this.setIconPath(DefaultMessageIcons.getWarningIconPath());
    }

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
                JFrame f = new JFrame("SimpleErrorMessageFrame");
                f.setSize(new Dimension(600, 400));
                f.setVisible(true);
                f.setContentPane(new TranslucentPanel(50));
                f.getContentPane().setBackground(Color.BLUE);

                SimpleWarningMessageDialog dialog = new SimpleWarningMessageDialog(
                    "Error message - TEST 1",
                    "Error message with some details... \nIt's better !", "www.malchrosoft.com", f);
                SimpleWarningMessageDialog dialog2 = new SimpleWarningMessageDialog(
                    "Error message - TEST 2",
                    "Error message with some details... Blablabla bla bla " +
                    "blabla bla. \nIt's better !\n- No ?",
                    "www.malchrosoft.com - Software Development", f);
                SimpleWarningMessageDialog dialog3 = new SimpleWarningMessageDialog(
                    "Error message - TEST 3",
                    "Error message with some details... OK ?!",
                    "www.malchrosoft.com - Software Development", f);
                SimpleWarningMessageDialog dialog4 = new SimpleWarningMessageDialog(
                    "Error message - TEST 4",
                    "With very long text : \n Error message with some details..." +
                    " OK ?! \nSome details :\n" +
                    "JGame is an open source 2D game engine that runs on any " +
                    "Java 1.2+ JRE platform (with optional OpenGL enhancements " +
                    "through JOGL), as well as the mobile J2ME (CLDC1.1/MIDP2.0) " +
                    "platform. It provides a very high-level framework for " +
                    "developing \"classic\" type arcade games. It is based on " +
                    "sprites with automatic animation and collision detection, " +
                    "a tile-based background with easy sprite-tile interaction " +
                    "facilities, and high-level game state and game sequence " +
                    "facilities. JGame games can be run in a variety of ways " +
                    "without requiring any changes in the code: regular " +
                    "applications, webstart, applets, or MIDlets. Graphics are " +
                    "scaled automatically to fit any screen size, from the " +
                    "smallest mobile device to full-screen desktop PC. JGame " +
                    "uses 2D graphics acceleration where available, but using " +
                    "OpenGL enables better graphical quality. ",
                    "www.malchrosoft.com - Software Development", f);
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
