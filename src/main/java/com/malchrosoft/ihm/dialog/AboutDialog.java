/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * AboutDialog.java
 *
 * Created on 21 oct. 2009, 01:07:41
 */
package com.malchrosoft.ihm.dialog;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class AboutDialog extends javax.swing.JDialog
{
    private JDialog licenseDialog;

    /**
     * Creates new form AboutDialog
     * @param parent the parent
     * @param modal boolean to say if this dialog is modal or not
     * @param licenceDialog the licenceDialog (can be null)
     */
    public AboutDialog(java.awt.Frame parent, boolean modal,
        JDialog licenceDialog)
    {
        super(parent, modal);
        initComponents();
        this.licenseDialog = licenceDialog;
        this.setLocationRelativeTo(this.getParent());
        this.closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        this.licenseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                treatLicenceButtonAction();
            }
        });
        if (this.licenseDialog == null) this.licenseButton.setVisible(false);
    }

    /**
     * Creates new AboutDialog
     * @param parent the parent
     * @param modal boolean to say if this dialog is modal or not
     */
    public AboutDialog(Frame parent, boolean modal)
    {
        this(parent, modal, null);
    }

    private void treatLicenceButtonAction()
    {
        if (this.licenseDialog == null) return;
        this.licenseDialog.setModal(true);
        this.licenseDialog.setVisible(true);
    }

    /**
     * Sets the application name
     * @param name the name
     */
    public void setApplicationName(String name)
    {
        this.applicationNameLabel.setText(name);
    }

    /**
     * Sets the company name
     * @param name the name
     */
    public void setCompanyName(String name)
    {
        this.companyNameLabel.setText(name);
    }

    /**
     * Sets the version text
     * @param versionText the version text
     */
    public void setVersionText(String versionText)
    {
        this.versionLabel.setText(versionText);
    }

    /**
     * Sets image
     * @param image the image
     */
    public void setImage(Image image)
    {
        this.image.setImage(image);
    }

    /**
     * Sets the image from the image file path
     * @param path the image file path
     */
    public void setImageFromPath(String path)
    {
        this.setImage(new ImageIcon(path).getImage());
    }

    public void setCloseButtonText(String text)
    {
        this.closeButton.setText(text);
    }

    public void setLicenseButtonText(String text)
    {
        this.licenseButton.setText(text);
    }

    /**
     * Sets the copyright html text
     * @param htmlText the html content
     */
    public void setCopyrightHTMLText(String htmlText)
    {
        this.copyrightHTMLEditorPane.setText("<div style=\"font-size:9pt; " +
            "font-family: Tahoma, arial, sans-serif; " +
            "text-align:justify\"> " +
            htmlText + "</div>");
        this.copyrightHTMLEditorPane.setCaretPosition(0);
    }

    public JLabel getApplicationNameLabel()
    {
        return applicationNameLabel;
    }

    public JLabel getCompanyNameLabel()
    {
        return companyNameLabel;
    }

    public JLabel getVersionLabel()
    {
        return versionLabel;
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        image = new org.jdesktop.swingx.JXImagePanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        labelIndentPanel = new javax.swing.JPanel();
        labelsPanel = new javax.swing.JPanel();
        versionLabel = new javax.swing.JLabel();
        applicationNameLabel = new javax.swing.JLabel();
        companyNameLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        copyrightHTMLEditorPane = new javax.swing.JEditorPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        licenseButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(java.awt.Color.white);
        setModal(true);
        setResizable(false);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        image.setMaximumSize(new java.awt.Dimension(600, 300));
        image.setMinimumSize(new java.awt.Dimension(300, 225));
        image.setOpaque(false);
        image.setPreferredSize(new java.awt.Dimension(300, 225));

        javax.swing.GroupLayout imageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(imageLayout);
        imageLayout.setHorizontalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        imageLayout.setVerticalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        jPanel1.add(image, java.awt.BorderLayout.NORTH);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(403, 52));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        labelIndentPanel.setMaximumSize(new java.awt.Dimension(10, 10));
        labelIndentPanel.setOpaque(false);

        javax.swing.GroupLayout labelIndentPanelLayout = new javax.swing.GroupLayout(labelIndentPanel);
        labelIndentPanel.setLayout(labelIndentPanelLayout);
        labelIndentPanelLayout.setHorizontalGroup(
            labelIndentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        labelIndentPanelLayout.setVerticalGroup(
            labelIndentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel7.add(labelIndentPanel);

        labelsPanel.setBackground(new java.awt.Color(255, 255, 255));
        labelsPanel.setMaximumSize(new java.awt.Dimension(2147483647, 65));
        labelsPanel.setMinimumSize(new java.awt.Dimension(190, 65));
        labelsPanel.setOpaque(false);
        labelsPanel.setPreferredSize(new java.awt.Dimension(190, 65));
        labelsPanel.setLayout(new java.awt.BorderLayout());

        versionLabel.setForeground(new java.awt.Color(102, 102, 102));
        versionLabel.setText("BusinessVersion (Built version)");
        labelsPanel.add(versionLabel, java.awt.BorderLayout.PAGE_END);

        applicationNameLabel.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        applicationNameLabel.setForeground(new java.awt.Color(204, 51, 0));
        applicationNameLabel.setText("ApplicationName");
        applicationNameLabel.setMaximumSize(new java.awt.Dimension(190, 30));
        applicationNameLabel.setMinimumSize(new java.awt.Dimension(190, 30));
        applicationNameLabel.setPreferredSize(new java.awt.Dimension(190, 30));
        labelsPanel.add(applicationNameLabel, java.awt.BorderLayout.CENTER);

        companyNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        companyNameLabel.setText("CompanyName");
        labelsPanel.add(companyNameLabel, java.awt.BorderLayout.PAGE_START);

        jPanel7.add(labelsPanel);

        jPanel2.add(jPanel7);

        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(484, 20));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 70));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(108, 70));

        copyrightHTMLEditorPane.setContentType("text/html");
        copyrightHTMLEditorPane.setEditable(false);
        copyrightHTMLEditorPane.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        copyrightHTMLEditorPane.setForeground(new java.awt.Color(102, 102, 102));
        copyrightHTMLEditorPane.setText("<span color=\"#999999\">Copyright &copy; 2006-2010 MalchroSoft. All right reserved.</span><br/>\n\n<p align=\"justified\">\n    <b>Warning:</b><br/>\n    \n    This computer program is protected by copyright law and international treaties.\nUnauthorised reproduction or distribution of this program, or any portion of it,\nmay result in severe civil and criminal penalties, and will be prosecuted to the\nmaximum extend possible under the law.<br/>\nSoftware and all other creation under the name MalchroSoft are propected by copyright\nlaw.<br/>\nMalchroSoft represents the productions or services, in the software field, made by\nor on behalf of Aymeric Malchrowicz.\n</p>");
        copyrightHTMLEditorPane.setFocusCycleRoot(false);
        copyrightHTMLEditorPane.setFocusable(false);
        jScrollPane1.setViewportView(copyrightHTMLEditorPane);

        jPanel2.add(jScrollPane1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(338, 10));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5);

        licenseButton.setText("licenceButton");
        licenseButton.setBorderPainted(false);
        jPanel3.add(licenseButton);

        closeButton.setText("closeButton");
        closeButton.setBorderPainted(false);
        jPanel3.add(closeButton);

        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                AboutDialog dialog = new AboutDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel applicationNameLabel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel companyNameLabel;
    private javax.swing.JEditorPane copyrightHTMLEditorPane;
    private org.jdesktop.swingx.JXImagePanel image;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel labelIndentPanel;
    private javax.swing.JPanel labelsPanel;
    private javax.swing.JButton licenseButton;
    private javax.swing.JLabel versionLabel;
    // End of variables declaration//GEN-END:variables
}