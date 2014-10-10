/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.io;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class CopyrightFile extends NamedFile
{
    /**
     * Creates the copyright file (copyright_'languageCode'.txt)
     * @param directory the directory
     * @param languageCode the language code
     */
    public CopyrightFile(String directory, String languageCode)
    {
        this(directory, languageCode, "html");
    }

    /**
     * Creates the copyright file (copyright_'languageCode'.'ext')
     * @param directory the directory
     * @param languageCode the language code
     * @param ext the extention
     */
    public CopyrightFile(String directory, String languageCode, String ext)
    {
        super(directory, "copyright_", languageCode, ext);
    }

}
