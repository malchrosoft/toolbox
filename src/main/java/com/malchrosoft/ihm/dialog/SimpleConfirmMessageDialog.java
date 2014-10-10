/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import com.malchrosoft.debug.Log;
import com.malchrosoft.graphics.components.TranslucentPanel;
import com.malchrosoft.sound.WaveSoundPlayer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class SimpleConfirmMessageDialog extends SimpleMessageDialog
{
    private JButton cancelButton;
    private JButton noButton;
    private JButton yesButton;

    private JButton yesForAllButton;
    private JButton noForAllButton;

    private WaveSoundPlayer sound;

    public enum OptionType
    {
        yesNoCancel, yesNo, okCancel, yesNoCancelAndYesNoForAll
    }

    public enum AnswerType
    {
        yes, no, cancel, yesForAll, NoForAll
    }

    private AnswerType answer;
    private OptionType option;

    public SimpleConfirmMessageDialog(String title, String message,
        Component parent)
    {
        this(title, message, parent, null);
    }

    public SimpleConfirmMessageDialog(String title, String message, Component parent,
        WaveSoundPlayer sound)
    {
        super(title, message, parent);
        this.cancelButton = new JButton("Cancel");
        this.noButton = new JButton("No");
        this.yesButton = new JButton("Yes");
        this.noForAllButton = new JButton("No for all");
        this.yesForAllButton = new JButton("Yes for all");
        this.getSouthPanel().removeAll();
        this.getSouthPanel().setLayout(new BoxLayout(this.getSouthPanel(), BoxLayout.X_AXIS));
        this.getSouthPanel().add(Box.createHorizontalGlue());
        this.initListeners();
        for (Component c : this.getComponents())
            if (c instanceof JButton) ((JButton) c).setBorderPainted(false);
        this.setIconPath(DefaultMessageIcons.getQuestionIconPath());
        this.sound = sound;
    }

    private void initListeners()
    {
        this.yesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                answer = AnswerType.yes;
                setVisible(false);
            }
        });
        this.noButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                answer = AnswerType.no;
                setVisible(false);
            }
        });
        this.cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancel();
            }
        });
        this.noForAllButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                answer = AnswerType.NoForAll;
                setVisible(false);
            }
        });
        this.yesForAllButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                answer = AnswerType.yesForAll;
                setVisible(false);
            }
        });
        KeyListener cancelKL = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    cancel();
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    ((JButton) e.getSource()).getActionListeners()[0]
                        .actionPerformed(null);
            }
        };

        this.getSouthPanel().addKeyListener(cancelKL);
        this.getYesButton().addKeyListener(cancelKL);
        this.getNoButton().addKeyListener(cancelKL);
        this.getCancelButton().addKeyListener(cancelKL);
    }

    public AnswerType showConfirmDialog(OptionType option)
    {
        this.answer = AnswerType.cancel;
        this.getSouthPanel().add(this.yesButton);
        this.getSouthPanel().add(Box.createRigidArea(new Dimension(2, 5)));
        switch (option)
        {
            case okCancel :
                this.getSouthPanel().add(this.cancelButton);
                this.getCancelButton().requestFocus();
                break;
            case yesNo :
                this.getSouthPanel().add(this.noButton);
                this.getNoButton().requestFocus();
                break;
            case yesNoCancel :
                this.getSouthPanel().add(this.noButton);
                this.getSouthPanel().add(Box.createRigidArea(new Dimension(5, 5)));
                this.getSouthPanel().add(this.cancelButton);
                this.getCancelButton().requestFocus();
                break;
            case yesNoCancelAndYesNoForAll :
                this.getSouthPanel().add(this.yesForAllButton);
                this.getSouthPanel().add(Box.createRigidArea(new Dimension(5, 5)));
                this.getSouthPanel().add(this.noButton);
                this.getSouthPanel().add(Box.createRigidArea(new Dimension(2, 5)));
                this.getSouthPanel().add(this.noForAllButton);
                this.getSouthPanel().add(Box.createRigidArea(new Dimension(5, 5)));
                this.getSouthPanel().add(this.cancelButton);
                this.getCancelButton().requestFocus();
                break;

        }
        if (this.sound != null) this.sound.play();
        this.setVisible(true);
        return this.answer;
    }

    public AnswerType getAnswer()
    {
        return answer;
    }

    public JButton getCancelButton()
    {
        return cancelButton;
    }

    public JButton getNoButton()
    {
        return noButton;
    }

    public OptionType getOption()
    {
        return option;
    }

    public JButton getYesButton()
    {
        return yesButton;
    }

    public JButton getNoForAllButton()
    {
        return noForAllButton;
    }

    public JButton getYesForAllButton()
    {
        return yesForAllButton;
    }
    
    private void cancel()
    {
        answer = AnswerType.cancel;
        this.setVisible(false);
    }



    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(SimpleConfirmMessageDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex)
                {
                    Logger.getLogger(SimpleConfirmMessageDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex)
                {
                    Logger.getLogger(SimpleConfirmMessageDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex)
                {
                    Logger.getLogger(SimpleConfirmMessageDialog.class.getName()).log(Level.SEVERE, null, ex);
                }

                JFrame f = new JFrame("SimpleMessageFrame");
                f.setSize(new Dimension(600, 400));
                f.setVisible(true);
                f.setContentPane(new TranslucentPanel(50));
                f.getContentPane().setBackground(Color.BLUE);

                SimpleConfirmMessageDialog dialog = new SimpleConfirmMessageDialog(
                    "ConfirmMessage - TEST 1",
                    "Confirm Message with a question... ?", f);

                f.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.getContentPane().addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent e)
                    {
                        Log.info(e.getKeyChar()+"");
                    }
                });
//                Log.info(dialog.showConfirmDialog(OptionType.yesNoCancel).name());
                Log.info(dialog.showConfirmDialog(OptionType.yesNoCancelAndYesNoForAll).name());
                f.setTitle(f.getTitle() + "           Answer : " +
                    dialog.getAnswer().name());
                //System.exit(0);
            }
        });
    }

}
