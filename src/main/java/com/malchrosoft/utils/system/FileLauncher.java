/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.utils.system;

import com.malchrosoft.utils.io.MalchroSoftDesktop;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class FileLauncher
{
	private static MalchroSoftDesktop desktop = new MalchroSoftDesktop();
	
	
    /**
     * Launches the default system application according to the file path.
     * @param filePath the file path
     */
    public static Launcher launchFile(String filePath)
    {
        Launcher l = new Launcher(filePath, false);
        new Thread(l).start();
        return l;
    }

    /**
     * Launches the web browser for loarding the web page according to the link.
     * @param link the web link
     */
    public static Launcher launchWebLink(String link)
    {
        Launcher l = new Launcher(link, true);
        new Thread(l).start();
        return l;
    }

    /**
     * Launches the system default browser and load the URL
     * @param url the URL
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void browseInSystemDefaultApplication(String url)
        throws URISyntaxException, IOException
    {
        browseURL(url);
    }

    /**
     * Launches the system default browser and load the URL
     * @param uri the URL
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void browseURL(URI uri) throws IOException
    {
        desktop.browse(uri);
    }

    /**
     * Launches the system default browser and load the URL
     * @param url the URL
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void browseURL(String url) throws URISyntaxException, IOException
    {
        browseURL(new URI(url));
    }

    /**
     * Opens the file in the system default application
     * @param filePath the file path
     * @throws java.io.IOException
     */
    public static void launchInSystemDefaultApplication(String filePath)
        throws IOException
    {
        openFile(filePath);
    }

    /**
     * Opens the file in the system default application
     * @param filePath the file path
     * @throws java.io.IOException
     */
    public static void openFile(String filePath) throws IOException
    {
        openFile(new File(filePath));
    }

    /**
     * Opens the file in the system default application
     * @param file the file
     * @throws java.io.IOException
     */
    public static void openFile(File file) throws IOException
    {
        desktop.open(file);
    }
}
