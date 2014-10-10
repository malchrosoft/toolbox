/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.io;

import com.malchrosoft.utils.system.OSDetector;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Malchrosoft desktop wrapper
 * @author Aymeric Malchrowicz
 */
public class MalchroSoftDesktop
{
	private Desktop desktop;

	/**
	 * Creates a new destop wrapper named MalchrosoftDesktop
	 * @see java.awt.Desktop
	 */
	public MalchroSoftDesktop()
	{
		this.desktop = Desktop.getDesktop();
	}

	public boolean isSupported(Action action)
	{
		return this.desktop.isSupported(action);
	}

	/**
	 * Launches the associated application to open the file.
	 *
	 * <p> If the specified file is a directory, the file manager of
	 * the current platform is launched to open it.
	 *
	 * @param file the file to be opened with the associated application
	 * @throws NullPointerException if {@code file} is {@code null}
	 * @throws IllegalArgumentException if the specified file dosen't
	 * exist
	 * @throws UnsupportedOperationException if the current platform
	 * does not support the {@link Desktop.Action#OPEN} action
	 * @throws IOException if the specified file has no associated
	 * application or the associated application fails to be launched
	 * @throws SecurityException if a security manager exists and its
	 * {@link java.lang.SecurityManager#checkRead(java.lang.String)}
	 * method denies read access to the file, or it denies the
	 * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
	 * permission, or the calling thread is not allowed to create a
	 * subprocess
	 * @see java.awt.AWTPermission
	 */
	public void open(File file) throws IOException
	{
		try
		{
			if (Desktop.isDesktopSupported())
				this.desktop.open(file);
		} catch (IOException ioe)
		{
			boolean works = false;
			if (file.exists())
			{
				// try by another way
				if (OSDetector.isWindows())
				{
					String[] commands =
					{
						"cmd", "/c", "start", "\"DummyTitle\"", file.getAbsolutePath()
					};
					Runtime.getRuntime().exec(commands);
				}
				else if (OSDetector.isLinux() || OSDetector.isMac())
				{
					Runtime.getRuntime().exec(file.getAbsolutePath());
				}
				works = true;
			}
			if (!works) throw ioe;
		}
	}

	public void edit(File file) throws IOException
	{
		this.edit(file);
	}

	public void print(File file) throws IOException
	{
		this.desktop.print(file);
	}

	public void mail(URI mailtoURL) throws IOException
	{
		this.desktop.mail(mailtoURL);
	}

	public void browse(URI url) throws IOException
	{
		this.desktop.browse(url);
	}
}
