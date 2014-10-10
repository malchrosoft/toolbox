/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.system;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class OSDetector
{
	public static enum OS
	{
		WINDOWS, LINUX, UNIX, MAC, SOLARIS, UNKNOWN
	}
	
	
	/**
	 * @return the os where the application is executed
	 */
	public static OS getMyOS()
	{
		if (isWindows()) return OS.WINDOWS;
		if (isMac()) return OS.MAC;
		if (isUnix()) return OS.UNIX;
		if (isLinux()) return OS.LINUX;
		if (isSolaris()) return OS.SOLARIS;
		return OS.UNKNOWN;
	}
	
	public static boolean isWindows()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
	}

	public static boolean isMac()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
	}

	public static boolean isUnixKernel()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
	
	public static boolean isUnix()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// unix only
		return (os.indexOf("nix") >= 0);
	}
	
	public static boolean isLinux()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// linux only
		return (os.indexOf("nux") >= 0);
	}

	public static boolean isSolaris()
	{
		String os = System.getProperty("os.name").toLowerCase();
		// Solaris
		return (os.indexOf("sunos") >= 0);
	}
}
