/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.text;

import java.text.Normalizer;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class StringUtils
{
	private StringUtils()
	{
	}

	/**
	 * Builds a fusioned String Table from two string tables
	 *
	 * @param ss the first string table
	 * @param ss2 the second string table
	 * @return a fusioned string table
	 */
	public static String[] buildFusionedStringTables(String[] ss, String[] ss2)
	{
		String[] st = new String[ss.length + ss2.length];
		int c = 0;
		for (String s : ss)
		{
			st[c++] = s;
		}
		for (String s : ss2)
		{
			st[c++] = s;
		}
		return st;
	}

	/**
	 * Compares the keys to lower case and replacing each '_' by ''.
	 *
	 * @param key1 the first key
	 * @param key2 the second key
	 * @return the result of the comparation true if they are similare, false
	 * otherwise
	 */
	public static boolean compareKeys(String key1, String key2)
	{
		boolean comparation = key1.replace("_", "").toLowerCase().equals(
			key2.replace("_", "").toLowerCase());
		return comparation;
	}

	/**
	 * Compares the contains of the keys to lower case and replacing each '_'
	 * by ''. Look into each key if it contains the other in these conditions.
	 *
	 * @param key1 the first key
	 * @param key2 the second key
	 * @return true if a key is contained by the other, return false otherwise
	 */
	public static boolean compareKeyContents(String key1, String key2)
	{
		boolean comparation = key1.replace("_", "").toLowerCase().contains(
			key2.replace("_", "").toLowerCase());
		if (!comparation) comparation = key2.replace("_", "").toLowerCase().contains(
				key1.replace("_", "").toLowerCase());
		return comparation;
	}

	/**
	 * Returns a parsable value.<br/>Returns "0" if the string value is not
	 * parsable by the Integer class.
	 *
	 * @param s the string
	 * @return a parsable value
	 */
	public static String getIntegerValue(String s)
	{
		try
		{
			Integer.parseInt(s);
		} catch (NumberFormatException nfe)
		{
			return "0";
		}
		return s;
	}

	/**
	 * Removes all accents from the string source
	 *
	 * @param source the string source
	 * @return the string without accent
	 */
	public static String removeAccent(String source)
	{
		return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
	}
}
