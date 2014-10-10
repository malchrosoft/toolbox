/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.io;

import com.malchrosoft.debug.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class FileUtils
{
    public FileUtils()
    {
    }

    public boolean copyFile(File source, File destination, JProgressBar pb)
    {
        if (!source.exists()) return false;
        FileOutputStream os = null;
        FileInputStream is = null;
        this.initProgressBar(pb);
        if (destination.exists()) return false;
        try
        {
            long sl = source.length();
            destination.createNewFile();
            is = new FileInputStream(source);
            os = new FileOutputStream(destination);
            byte[] buffer = new byte[128 * 1024];
            int i;
            int cumulation = 0;
            while ((i = is.read(buffer)) != -1)
            {
                cumulation += buffer.length;
                if (pb != null) pb.setValue((int) (cumulation / sl * 100));
                os.write(buffer, 0, i);
            }
            if (pb != null) pb.setValue(100);
            return true;
        } catch (FileNotFoundException fnfe)
        {
            Log.debug(fnfe.getMessage());
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, fnfe);
        } catch (IOException ex)
        {
            Log.debug(ex.getMessage());
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try { os.close(); } catch (IOException ex) {}
            try { is.close(); } catch (IOException ex) {}
        }
        return false;
    }
    public boolean copyFile(String sourcePath, String destinationPath,
        JProgressBar pb)
    {
        return this.copyFile(new File(sourcePath), new File(destinationPath), pb);
    }

    public boolean copyFile(String sourcePath, String destinationPath)
    {
        return this.copyFile(sourcePath, destinationPath, null);
    }

    public boolean copyFile(File source, File destination)
    {
        return this.copyFile(source, destination, null);
    }


    private void initProgressBar(JProgressBar pb)
    {
        if (pb != null)
        {
            pb.setValue(0);
            pb.setIndeterminate(false);
            pb.setMaximum(100);
            pb.setMinimum(0);
        }
    }


    // STATIC
    public static boolean copyFile(File sourceFile, String destinationPath)
    {
        return new FileUtils().copyFile(sourceFile, new File(destinationPath));
    }
    
    public static boolean copyFile(File sourceFile, String destinationPath,
        JProgressBar progressBar)
    {
        return new FileUtils().copyFile(sourceFile, new File(destinationPath),
            progressBar);
    }

    public static String getDirectoryPath(String filePath)
    {
        return getDirectoryPath(new File(filePath));
    }

    public static String getDirectoryPath(File file)
    {
        return file.getParent();
    }

    public static String getFileNameOnly(String filePath)
    {
        String[] splitted = filePath.split(buildSeparatorForSplitting(File.separator));
        return splitted[splitted.length-1];
    }

    private static String buildSeparatorForSplitting(String separator)
    {
        if (separator.equals("\\")) return "\\\\";
        return separator;
    }

    /**
     * Separates the extention of the file path and returns a vector of string :
     * <ul><li>First : the file path without the file extention</li>
     * <li>Last : the extention without the dot</li></ul>
     * @param completeFilePath the complete file path
     * @return the extention of the file path and returns a vector of string
     */
    public static Vector<String> separateFileNameAndExtention(String completeFilePath)
    {
        Vector<String> vs = new Vector<String>();
        if (completeFilePath.lastIndexOf(".") < 0)
        {
            vs.add(completeFilePath);
            vs.add("");
        }
        else
        {
            vs.add(completeFilePath.substring(0, completeFilePath.lastIndexOf(".")));
            vs.add(completeFilePath.substring(completeFilePath.lastIndexOf(".")+1,
                completeFilePath.length()));
        }
        return vs;
    }
    public static String getFilePathWithoutExtention(String completeFilePath)
    {
        return separateFileNameAndExtention(completeFilePath).get(0);
    }
    public static String getFileExtentionWithoutDot(String completeFilePath)
    {
        return separateFileNameAndExtention(completeFilePath).get(1);
    }

    /**
     * Returns the name of the file without extention nor directory
     * @param filePath the file path
     * @return the file name
     */
    public static String getFileNameName(String filePath)
    {
        return getFilePathWithoutExtention(FileUtils.getFileNameOnly(filePath));
    }

    /**
     * Returns the name of the file without extention nor directory
     * @param filePath the file
     * @return the file name only
     */
    public static String getFileNameName(File file)
    {
        return getFileNameName(file.getPath());
    }

    /**
     * Returns the canonical path or the absolute path on error.
     * @param file the file
     * @return the canonical/absolute path
     */
    public static String getProtectedCanonicalPath(File file)
    {
        String path = file.getAbsolutePath();
        try
        {
            path = file.getCanonicalPath();
        } catch (IOException ex) {   }
        return path;
    }

    /**
     * Returns the canonical path or the absolute path on error.
     * @param filePath the file path (relative / absolute)
     * @return the canonical path or the absolute path on error
     */
    public static String getProtectedCanonicalPath(String filePath)
    {
        return getProtectedCanonicalPath(new File(filePath));
    }
}
