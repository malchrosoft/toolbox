/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import com.malchrosoft.graphics.components.ImageReflectionPanel;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ImageFileChooser extends JFileChooser
{
    private JPanel previewPanel;

    public ImageFileChooser(String title, String imageFileFilterLabel)
    {
        super();
        this.setDialogTitle(title);
        this.setAcceptAllFileFilterUsed(false);
        this.setFileFilter(new ImageFileFilter(imageFileFilterLabel));
        this.previewPanel = new ImagePreviewPanel(this);
        this.setAccessory(this.previewPanel);
        this.setMultiSelectionEnabled(false);
    }

    private class ImagePreviewPanel extends JPanel implements PropertyChangeListener
    {
        private ImageReflectionPanel imagePanel;
        private JProgressBar pb;

        public ImagePreviewPanel(JFileChooser fc)
        {
            this.setPreferredSize(new Dimension(200, 180));
            fc.addPropertyChangeListener(this);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.removeAll();
            this.setBorder(new EmptyBorder(0, 3, 0, 0));
            this.pb = new JProgressBar();
            this.add(this.pb);
            this.pb.setVisible(false);
        }

        @Override
        public void propertyChange(final PropertyChangeEvent evt)
        {
            if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(evt.getPropertyName()))
            {
                if (this.imagePanel != null) this.imagePanel.setVisible(false);
            }
            else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName()))
            {
                if (evt.getNewValue() != null && !((File) evt.getNewValue()).isDirectory())
                    new Thread(new Runnable() 
                    { @Override public void run() { loadImage((File) evt.getNewValue()); } }).start();
            }
            this.paintAll(this.getGraphics());
        }

        private synchronized void loadImage(File imageFile)
        {
            this.pb.setVisible(true);
            this.pb.setIndeterminate(true);
            if (this.imagePanel == null)
            {
                this.imagePanel = new ImageReflectionPanel(imageFile.getPath(), 1);
                this.imagePanel.setBlurEnabled(true);
                this.imagePanel.setBetterQualitySize(true);
                this.imagePanel.setPreferredSize(new Dimension(200, 178));
                this.imagePanel.setVisible(false);
            }
            else this.imagePanel.loadImage(imageFile.getPath());
            if (this.getComponents().length < 2)
                this.add(this.imagePanel);
            this.imagePanel.repaint();
            this.pb.setIndeterminate(false);
            this.pb.setVisible(false);
            this.imagePanel.setVisible(true);
        }
    }

    public static class ImageFileFilter extends FileFilter
    {
        private String descriptionLabel;
        public enum ImageFileExtention
        {
            png, jpg, gif, jpeg
        }

        public ImageFileFilter(String descriptionLabel)
        {
            super();
            this.descriptionLabel = descriptionLabel;
        }

        @Override
        public boolean accept(File f)
        {
            if (f.isDirectory()) return true;
            String fileName = f.getName();
            for (ImageFileExtention ife : ImageFileExtention.values())
                if (fileName.toLowerCase().endsWith(ife.name().toLowerCase()))
                    return true;
            return false;
        }

        @Override
        public String getDescription()
        {
            String exts = "";
            for (ImageFileExtention ife : ImageFileExtention.values())
                exts += "*." + ife.name() + ", ";
            return this.descriptionLabel + " (" + exts.substring(0, exts.length()-2) + ")";
        }

    }

    public static void main(String args[])
    {
        new ImageFileChooser("Toto", "Images").showOpenDialog(null);

    }

}
