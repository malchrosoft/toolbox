/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class NamedFile extends File
{
    private String namedPart;

    public NamedFile(String directory, String namedPart, String nameEnd,
        String ext)
    {
        super(directory + namedPart + nameEnd + "." + ext);
        this.namedPart = namedPart;
    }

    /**
     * Returns the content
     * @return the text content
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public String getContent() throws FileNotFoundException, IOException
    {
        return TextFileManager.getTextFileContent(this.getPath(), null);
    }

    public String getNamedPart()
    {
        return namedPart;
    }
}
