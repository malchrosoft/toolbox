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
public class Launcher implements Runnable
{
    private String param;
    private IOException ioException;
    private URISyntaxException uriSyntaxException;
    private boolean weblink;
	private MalchroSoftDesktop desktop;

    /**
     * Creates a new launcher.
     * @param param the launching parameter
     * @param weblink the boolean value to know if the param is a weblink or not.
     */
    public Launcher(String param, boolean weblink)
    {
        this.param = param;
        this.weblink = weblink;
		this.desktop = new MalchroSoftDesktop();
        this.ioException = null;
    }

    /**
     * Returns if an exception arisen or not.
     * @return true if an exception arisen, other false
     */
    public boolean exceptionArisen()
    {
        return this.ioException != null || this.uriSyntaxException != null;
    }

    /**
     * Returns the IO Exception only if an exception has arisen.
     * @return the IO Exception if an axeption has arisen, <i>null</i> otherwise
     */
    public IOException getIOException()
    {
        return this.ioException;
    }

    /**
     * Returns the URISyntaxException if an URISyntaxException has arisen.
     * @return the URISyntaxException if an URISyntaxException has arisen,
     * <i>null</i> otherwise
     */
    public URISyntaxException getUriSyntaxException()
    {
        return uriSyntaxException;
    }

    @Override
    public void run()
    {
        try
        {
//            if (OS.isWindows())
//            {
//                if (this.weblink)
//                    Runtime.getRuntime().exec("cmd /c start " + this.param);
//                else
//                    Runtime.getRuntime().exec("cmd /c \"" + this.param + "\"");
//            }
//            else if (OS.isLinux() || OS.isMacOSX())
//            {
//                if (this.weblink)
//                    Runtime.getRuntime().exec("start " + this.param);
//                else
//                    Runtime.getRuntime().exec(this.param);
//            }
//            else
//            {
//                if (this.weblink) Runtime.getRuntime().exec("start " + this.param);
//                else Runtime.getRuntime().exec(this.param);
//            }
            if (this.weblink)
                desktop.browse(new URI(this.param));
            else desktop.open(new File(this.param));
        } catch (URISyntaxException ex)
        {
            this.uriSyntaxException = ex;
        } catch (IOException ex)
        {
            this.ioException = ex;
        }
    }

    public String getParam()
    {
        return param;
    }

    public boolean isWeblink()
    {
        return weblink;
    }

}
