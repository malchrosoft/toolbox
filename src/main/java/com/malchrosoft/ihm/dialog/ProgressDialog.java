/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * ProgressDialog.java
 *
 * Created on 9 août 2009, 20:09:06
 */
package com.malchrosoft.ihm.dialog;

import com.malchrosoft.debug.Log;
import com.malchrosoft.graphics.utils.PositioningManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ProgressDialog extends JFrame
{
    private Component owner;
    private int defaultWidth = 350;
    private int defaultHeight = 30;

    private Thread repaintContinualyThread;

    /** Creates new form ProgressDialog */
    public ProgressDialog(Component owner)
    {
        this.getContentPane().setLayout(new BorderLayout());
        initComponents();
        this.owner = owner;
        this.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        this.setSize(new Dimension(defaultWidth, defaultHeight));
        this.setLocationRelativeTo(null);
        this.setLocationByPlatform(true);
        this.setAlwaysOnTop(true);
        if (this.owner != null)
        {
            PositioningManager.center(this, this.owner);
        }
    }

    /**
     * Returns the progress bar.
     * @return the progress bar
     */
    protected JProgressBar getBar()
    {
        return bar;
    }

    public String getLabel()
    {
        return this.barLabel.getText();
    }

    /**
     * Sets the bar indeterminate value.
     * @param isIndeterminate the boolean value
     */
    public void setIndeterminate(boolean isIndeterminate)
    {
        this.getBar().setIndeterminate(isIndeterminate);
//        if (isIndeterminate) this.repaintContinualy();
//        else this.stopRepaintContinualy();
    }

    /**
     * Sets the bar string painted value.
     * @param b the boolean value
     */
    public void setBarStringPainted(boolean b)
    {
        this.getBar().setStringPainted(b);
        this.repaint();
    }

    /**
     * Sets the bar text
     * @param txt the text
     */
    public void setBarText(String txt)
    {
        this.getBar().setString(txt);
        this.paintComponents(this.getGraphics());
    }

    /**
     * Sets the bar value.
     * @param value the value
     */
    public void setValue(int value)
    {
        if (this.getBar().isIndeterminate()) this.setIndeterminate(false);
        this.getBar().setValue(value);
        this.getBar().paintComponents(this.getBar().getGraphics());
    }

    /**
     * Sets the bar label.
     * @param label the label
     */
    public void setLabel(String label)
    {
        this.getBarLabel().setText(label);
//        this.barLabelPanel.paintComponents(this.barLabelPanel.getGraphics());
        this.getBarLabel().paintComponents(this.getBarLabel().getGraphics());
    }

    @Override
    public void setVisible(boolean b)
    {
        if (this.owner != null )this.owner.setEnabled(!b);
        super.setVisible(b);
        if (!b) return;
//        this.paint(this.getGraphics());
//        this.repaint();
        this.getRootPane().updateUI();
    }

//    @Override
//    public void paint(Graphics g)
//    {
//        Graphics2D g2d = (Graphics2D) g;
////        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
////            RenderingHints.VALUE_ANTIALIAS_ON);
////        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
////            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        super.paint(g2d);
////        this.bar.paint(this.bar.getGraphics());
////        this.barLabelPanel.paint(this.barLabelPanel.getGraphics());
////        this.barLabel.paint(this.barLabel.getGraphics());
////        Log.info("#####################    -->     paint !!!");
//    }



    @Override
    public void dispose()
    {
        this.setVisible(false);
        super.dispose();
    }

    /**
     * Returns the bar label.
     * @return the bar label
     */
    protected JLabel getBarLabel()
    {
        return barLabel;
    }

    /**
     * Sets the background color
     * @param color the color
     */
    public void setBackgroundColor(Color color)
    {
        this.barLabelPanel.setBackground(color);
    }

    private void repaintContinualy()
    {
        if (this.repaintContinualyThread == null)
        {
            this.repaintContinualyThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (getBar().isIndeterminate())
                        getBar().paint(getBar().getGraphics());
                }
            });
        }
        this.repaintContinualyThread.start();
    }
    private void stopRepaintContinualy()
    {
        if (this.repaintContinualyThread == null) return;
        this.repaintContinualyThread.interrupt();
        this.repaintContinualyThread = null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bar = new javax.swing.JProgressBar();
        barLabelPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        barLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setFocusableWindowState(false);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(200, 28));
        setResizable(false);
        setUndecorated(true);

        bar.setValue(10);
        bar.setFocusCycleRoot(true);
        bar.setFocusTraversalPolicyProvider(true);
        bar.setInheritsPopupMenu(true);
        bar.setOpaque(true);
        getContentPane().add(bar, java.awt.BorderLayout.CENTER);

        barLabelPanel.setDoubleBuffered(false);
        barLabelPanel.setLayout(new javax.swing.BoxLayout(barLabelPanel, javax.swing.BoxLayout.X_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(4, 200));
        jPanel2.setMinimumSize(new java.awt.Dimension(4, 10));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(4, 14));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        barLabelPanel.add(jPanel2);

        barLabel.setText("jLabel1");
        barLabel.setDoubleBuffered(true);
        barLabel.setFocusCycleRoot(true);
        barLabel.setMaximumSize(new java.awt.Dimension(3400, 14));
        barLabelPanel.add(barLabel);

        getContentPane().add(barLabelPanel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        JFrame f = new JFrame("ProgressDialog - TEST");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400);
        f.setBounds(200, 200, 400, 400);
        //f.getContentPane().setBackground(Color.GREEN);
//        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        TEST(f);
        try { Thread.sleep(500); } catch (InterruptedException ie) {}
        f.dispose();
//        System.exit(0);
    }

    public static void TEST(Component f)
    {
        ProgressDialog pf = new ProgressDialog(f);
        pf.setLabel("DEBUT...");
        pf.setValue(0);
        pf.setIndeterminate(true);
        try { Thread.sleep(500); } catch (InterruptedException ie) {}
        pf.setVisible(true);
        try { Thread.sleep(3000); } catch (InterruptedException ie) {}
        pf.setIndeterminate(false);
        for (int c=0; c < 100; c+=1)
        {
            try { Thread.sleep(35); }
            catch (InterruptedException ie)
            { Log.debug(ie.getMessage()); }
            if (c >= 100) c = 100;
            pf.setValue(c);
        }
        pf.setValue(0);
        pf.setIndeterminate(true);
        pf.setBarStringPainted(true);
        pf.setLabel("Autres tests");
        try { Thread.sleep(3000); } catch (InterruptedException ie) {}
        for (int c=0; c < 100; c+=1)
        {
            try { Thread.sleep(45); }
            catch (InterruptedException ie)
            { Log.debug(ie.getMessage()); }
            if (c >= 100) c = 100;
            pf.setValue(c);
        }
        pf.setValue(100);
        pf.setLabel("Essai de progression... FIN");
        try { Thread.sleep(1000); } catch (InterruptedException ie) {}
        pf.dispose();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar bar;
    private javax.swing.JLabel barLabel;
    private javax.swing.JPanel barLabelPanel;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
