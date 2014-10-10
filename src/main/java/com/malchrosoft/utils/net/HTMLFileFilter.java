/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.net;

import com.malchrosoft.utils.io.FileUtils;
import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class HTMLFileFilter extends FileFilter
{
    private String fileString;
    public static String DEFAULT_EXTENTION = ".html";
    public static String EXTENTION_EXISTANCE_TEST = "htm";

    public HTMLFileFilter()
    {
        super();
        this.fileString = "";
    }


    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory() || (f.isFile() &&
            FileUtils.getFileExtentionWithoutDot(f.getName()).contains(
            EXTENTION_EXISTANCE_TEST)))
            return true;
        return false;
    }

    @Override
    public String getDescription()
    {
        String desc = "*.html; *.htm";
        if (this.fileString != null && this.fileString.length() > 0)
            desc = this.fileString + " (" + desc + ")";
        return desc;
    }

    /**
     * Sets 'File' string according to a language.
     * @param s the string for 'File'
     */
    public void setFileString(String s)
    {
        this.fileString = s;
    }

}
