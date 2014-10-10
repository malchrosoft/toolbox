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
public class LicenseFile extends NamedFile
{
    /**
     * Creates a new license file (license_'LanguageCode'.html)
     * @param directory the directory which contains the license file
     * @param languageCode the language code
     */
    public LicenseFile(String directory, String languageCode)
    {
        this(directory, languageCode, "txt");
    }
    
    /**
     * Creates a new license file (license_'LanguageCode'.'ext')
     * @param directory directory which contains the license file
     * @param languageCode the language content
     * @param ext the extention
     */
    public LicenseFile(String directory, String languageCode, String ext)
    {
        super(directory, "license_", languageCode, ext);
    }

}
