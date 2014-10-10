/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

/*
 * SimpleMessageDialog.java
 *
 * Created on 22 avr. 2009, 19:58:33
 */
package com.malchrosoft.ihm.dialog;

import com.malchrosoft.debug.Log;
import com.malchrosoft.graphics.components.ImageReflectionPanel;
import com.malchrosoft.graphics.components.TranslucentPanel;
import com.malchrosoft.graphics.components.TranslucentScrollPane;
import com.malchrosoft.graphics.components.textcomponents.TranslucentEditorPane;
import com.malchrosoft.graphics.components.textcomponents.TranslucentTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.pushingpixels.lafwidget.contrib.blogofbug.swing.components.GradientPanel;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class SimpleMessageDialog extends javax.swing.JDialog
{
    private TranslucentScrollPane messagePane;
    private TranslucentTextArea messageTextArea;
    private TranslucentEditorPane messageEditorPane;
    private TranslucentPanel messagePanel;
    private ImageReflectionPanel iconPanel;
    
    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 150;
    public static final Dimension DEFAULT_DIMENSION =
        new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private boolean useHTMLMode;

    /**
     * Creates new SimpleMessageDialog.
     * @param title the title
     * @param message the message
     * @param comment the sort comment
     * @param parent the parent
     * @param isModal is modal of not, if yes => true, false otherwise
     */
    public SimpleMessageDialog(String title, String message,
        String comment, Component parent, boolean isModal)
    {
        this(title, message, comment, parent, isModal, false);
    }

    /**
     * Creates new SimpleMessageDialog.
     * @param title the title
     * @param message the message
     * @param comment the sort comment
     * @param parent the parent
     * @param isModal is modal of not, if yes => true, false otherwise
     * @param useHTMLMode
     */
    public SimpleMessageDialog(String title, String message,
        String comment, Component parent, boolean isModal, boolean useHTMLMode)
    {
        super((JFrame) parent, isModal);
        this.useHTMLMode = useHTMLMode;
		GradientPanel gp = new GradientPanel();
        gp.setBackground(new Color(200, 200, 255), new Color(150, 150, 200));
        this.setContentPane(gp);
        this.getContentPane().setLayout(new BorderLayout());

        initComponents();
        for (Component c : this.getComponents())
            if (c instanceof JButton) ((JButton) c).setBorderPainted(false);
        if (parent != null)
        {
            this.setLocationRelativeTo(parent);
        }
        else this.setLocationByPlatform(true);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.messagePanel = new TranslucentPanel(0);
        this.messageTextArea = new TranslucentTextArea(true);
        this.messageTextArea.setEditable(false);
        this.messageTextArea.setAlignmentY(TranslucentTextArea.CENTER_ALIGNMENT);
        this.messageTextArea.setFont(this.commentLabel.getFont().deriveFont(
            Font.PLAIN, 12.0f));
        this.messageTextArea.setLineWrap(true);
        this.messageTextArea.setWrapStyleWord(true);

        this.messageEditorPane = new TranslucentEditorPane(true);
        this.messageEditorPane.setContentType("text/html");
        this.messageEditorPane.setEditable(false);
        this.messageEditorPane.setAlignmentY(TranslucentEditorPane.CENTER_ALIGNMENT);
        this.messageEditorPane.setFont(this.messageTextArea.getFont());

        if (this.useHTMLMode)
            this.messagePane = new TranslucentScrollPane(this.messageEditorPane);
        else
            this.messagePane = new TranslucentScrollPane(this.messageTextArea);

        this.messagePanel.setLayout(new BoxLayout(this.messagePanel,
            BoxLayout.X_AXIS));
        this.messagePanel.add(this.messagePane);
        this.iconPanel = new ImageReflectionPanel(
            DefaultMessageIcons.getMessageIconPath());
        this.iconPanel.setBlurEnabled(true);
        this.getContentPane().add(this.iconPanel, BorderLayout.WEST);
        this.getContentPane().add(this.messagePanel, BorderLayout.CENTER);
        this.iconPanel.setPreferredSize(new Dimension(110, 120));
        this.iconPanel.setMaximumSize(new Dimension(110, 120));
        this.iconPanel.setMinimumSize(new Dimension(110, 120));

        

        this.setTitle(title);
        this.setMessage(message);
        this.setComment(comment);

        this.initListeners();
        this.okButton.requestFocus();
    }

    /**
     * Creates new SimpleMessageDialog.<br/>
     * By default, this dialog is modal.
     * @param title the title
     * @param message the message
     * @param parent the parent
     */
    public SimpleMessageDialog(String title, String message,
        Component parent)
    {
        this(title, message, "", parent);
    }

    /**
     * Creates new SimpleMessageDialog.<br/>
     * By default, this dialog is modal.
     * @param title
     * @param message
     * @param comment
     * @param parent
     */
    public SimpleMessageDialog(String title, String message,
        String comment, Component parent)
    {
        this(title, message, comment, parent, true);
    }

    /**
     * Creates new SimpleMessageDialog.<br/>
     * By default, this dialog is modal.
     * @param title the title
     * @param message the message
     * @param comment the short comment
     * @param iconFilePath the icon file path
     * @param parent the parent
     */
    public SimpleMessageDialog(String title, String message,
        String comment, String iconFilePath, Component parent)
    {
        this(title, message, comment, parent);
        this.setIconPath(iconFilePath);
    }

    private void initListeners()
    {
        this.okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SimpleMessageDialog.this.dispose();
            }
        });
        this.okButton.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                formKeyPressed(e);
            }
        });
    }

    public ImageReflectionPanel getIconPanel()
    {
        return iconPanel;
    }

    /**
     * Sets the icon file path
     * @param filePath the file path
     */
    public void setIconPath(String filePath)
    {
        // TODO : Aymeric
        this.iconPanel.loadImage(filePath);
    }

    /**
     * Sets the reflexion length (Beetween 0 and 1)
     * @param length the length
     */
    public void setIconReflexionLength(float length)
    {
        this.iconPanel.setLength(length);
    }

    /**
     * Sets the comment witch must be short
     */
    public void setComment(String comment)
    {
        this.commentLabel.setText(comment);
    }

    /**
     * Sets the OK/Close button text.<br/>By default : "OK"
     * @param text the thext for this button
     */
    public void setButtonText(String text)
    {
        this.okButton.setText(text);
    }

    /**
     * Sets the message
     * @param message the message to display
     */
    public void setMessage(String message)
    {
        String[] v;
		if (isUseHTMLMode()) v = message.split("[<br].[>]");
		else v = message.split("\n");
        String newMessage = "";
        String newHTMLMessage = "<div style=\"font-size:" +
            this.messageTextArea.getFont().getSize() + "pt; font-family: " +
            this.messageTextArea.getFont().getFamily() + "; \" color=\"#" +
            Integer.toHexString(this.messageTextArea.getForeground().getRed()) +
            Integer.toHexString(this.messageTextArea.getForeground().getGreen()) +
            Integer.toHexString(this.messageTextArea.getForeground().getBlue()) +
            "\">";
        if (v.length < 2)
        {
            newMessage += "\n\n\n";
            newHTMLMessage += "<br/><br/><br/>\n";
        }
        else if (v.length < 3)
        {
            newMessage += "\n\n";
            newHTMLMessage += "<br/><br/>\n";
        }
        else if (v.length < 4)
        {
            newMessage += "\n";
            newHTMLMessage += "<br/>\n";
        }

        newMessage += message;
        newHTMLMessage += message + "</div>";
        
        this.messageTextArea.setText(newMessage);
        this.messageTextArea.setCaretPosition(0);
        this.messageEditorPane.setText(newHTMLMessage);
        this.messageEditorPane.setCaretPosition(0);
    }

    public JLabel getCommentLabel()
    {
        return commentLabel;
    }

    /**
     * Returns the message component if it's used, <i>null</i> otherwise.
     * @return the message component if it's used, <i>null</i> otherwise
     */
    public TranslucentTextArea getMessageTextArea()
    {
        if (!this.useHTMLMode) return messageTextArea;
        return null;
    }
    /**
     * Returns the message component if it's used, <i>null</i> otherwise.
     * @return the message component if it's used, <i>null</i> otherwise
     */
    public TranslucentEditorPane getMessageEditorPane()
    {
        if (this.useHTMLMode) return messageEditorPane;
        return null;
    }

    @Override
    public void setVisible(boolean b)
    {
        this.refreshMessageView();
        super.setVisible(b);
    }

    private void refreshMessageView()
    {
        if (this.useHTMLMode)
            this.messagePane.setViewportView(this.messageEditorPane);
        else
            this.messagePane.setViewportView(this.messageTextArea);
    }

    public boolean isUseHTMLMode()
    {
        return useHTMLMode;
    }

    public void setUseHTMLMode(boolean useHTMLMode)
    {
        this.useHTMLMode = useHTMLMode;
    }
    

    public JPanel getSouthPanel()
    {
        return southPanel;
    }

    /**
     * Sets the gradient background
     * @param start top color
     * @param end bottom color
     */
    public void setBackground(Color start, Color end)
    {
        if (this.getContentPane() instanceof GradientPanel)
        ((GradientPanel) this.getContentPane()).setBackground(start, end);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        southPanel = new javax.swing.JPanel();
        commentLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 150));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setModal(true);
        setName("SimpleErrorMessageFrame"); // NOI18N
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        southPanel.setOpaque(false);
        southPanel.setLayout(new java.awt.BorderLayout());

        commentLabel.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        commentLabel.setText("Toto");
        commentLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        commentLabel.setAlignmentX(0.5F);
        commentLabel.setFocusable(false);
        commentLabel.setPreferredSize(new java.awt.Dimension(400, 14));
        southPanel.add(commentLabel, java.awt.BorderLayout.CENTER);

        okButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        okButton.setMnemonic('O');
        okButton.setText("OK");
        okButton.setFocusCycleRoot(true);
        southPanel.add(okButton, java.awt.BorderLayout.LINE_END);

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
            .addGap(0, 23, Short.MAX_VALUE)
        );

        southPanel.add(jPanel2, java.awt.BorderLayout.LINE_START);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_formKeyPressed
    {//GEN-HEADEREND:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER ||
            evt.getKeyCode() == KeyEvent.VK_ESCAPE)
            this.dispose();
    }//GEN-LAST:event_formKeyPressed

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
                
                f.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
//                dialog.setBackground(Color.LIGHT_GRAY, Color.GRAY);
                dialog.setVisible(true);
                System.exit(0);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel commentLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel southPanel;
    // End of variables declaration//GEN-END:variables
}
