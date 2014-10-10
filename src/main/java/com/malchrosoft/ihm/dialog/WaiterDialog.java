/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.ihm.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class WaiterDialog extends JWindow implements ActionListener
{
    private int x,  tWinX,  tWinY,  tailleFenetreX,  tailleFenetreY;
    private int xa,  ya,  delaiAnimation;
    private FontMetrics fm;
    private String texte;
    protected Timer timer;

    /** Creates new Waiter */
    public WaiterDialog()
    {
        // recup taille ecran
        tailleFenetreX = (int) Toolkit.getDefaultToolkit().getScreenSize().width;
        tailleFenetreY = (int) Toolkit.getDefaultToolkit().getScreenSize().height;
        start();
        tWinX = 300;
        tWinY = 120;
        xa = (tailleFenetreX / 2) - tWinX / 2;
        ya = (tailleFenetreY / 2) - tWinY / 2;
        setBounds(xa, ya, 300, 120);
        setBackground(Color.darkGray);
        setVisible(true);
    } // fin de constructeur
    // methodes

    @Override
    public void paint(Graphics g)
    {
        fm = g.getFontMetrics();
        if (texte == null || texte.equals(""))
        {
            texte = new String("Veuillez patienter...");
        }
        int longueurTexte = fm.stringWidth(texte);
        // si texte trop long
        if (longueurTexte >= 280)
        {
            tWinX = longueurTexte + 20;
            setBounds(xa, ya, tWinX, tWinY);
        }
        int hauteurTexte = fm.getHeight();
        int contourX = tWinX - 10;
        int contourY = tWinY - 10;
        int xb = (contourX / 2) - (longueurTexte / 2);
        // dessiner le contour
        g.setColor(Color.darkGray);
        g.fill3DRect(5, 5, contourX, contourY, true);
        // texte
        g.setColor(Color.lightGray);
        g.fill3DRect(xb - 2, 22, longueurTexte + 4, 2 + hauteurTexte, false);
        g.setColor(Color.red);
        g.drawString(texte, xb, 20 + hauteurTexte);
        // barre
        g.setColor(Color.lightGray);
        g.fill3DRect(12, 60, contourX - 14, 30, false);
        // defilement
        g.setColor(Color.yellow);
        if (x == (contourX - 18))
        {
            g.setColor(Color.blue);
            g.fill3DRect(12, 60, contourX - 14, 30, true);
            x = 0;
        }
        g.setColor(Color.yellow);
        g.fill3DRect(14, 62, x, 26, true);
        x++;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    public void setTexte(String string)
    {
        texte = new String(string);
        repaint();
    }

    public void start()
    {
        if (timer == null)
        {
            timer = new Timer(50, this);
            timer.start();
        }
        else if (!timer.isRunning())
        {
            timer.restart();
        }
    }

    public void close()
    {
        timer.stop();
        setVisible(false);
    }

    public static void main(String arg[])
    {
        WaiterDialog i = new WaiterDialog();
    }
}
