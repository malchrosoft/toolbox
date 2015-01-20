/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.utils.text;

import com.malchrosoft.utils.text.TextManager.Separator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TextManager
{
	public enum Separator
	{
		PIPE('|'),
		SEMICOLON(';'),
		COMMA(','),
		SLASH('/'),
		NEW_LINE('\n'),
		DOT('.'),
		SPACE(' ');
		//
		private char value;
		private String regex;

		private Separator(char sepChar)
		{
			this(sepChar, Pattern.quote(sepChar + ""));

		}

		private Separator(char sepChar, String regex)
		{
			this.value = sepChar;
			this.regex = regex;
		}

		public String getRegex()
		{
			return regex;
		}

		public char getValue()
		{
			return value;
		}

	}

	private String string;

	public TextManager(String string)
	{
		this.string = string;
	}

	/**
	 * Returns the list of string : 1 element / line dilimited by the char '\n'.
	 *
	 * @return the list.
	 */
	public List<String> toLineList()
	{
		return toList(Separator.NEW_LINE);
	}

	/**
	 * Returns the list of string : 1 element / line dilimited by the separator.
	 *
	 * @param separator the separator
	 * @return
	 * @deprecated user toList(Separator sep)
	 */
	public List<String> toList(char separator)
	{
		if (this.string == null) return null;
		return Arrays.asList(this.string.split(Pattern.quote(separator + "")));
	}

	/**
	 * @param seps
	 * @return the list of string : 1 element / line delimited by the separator.
	 */
	public List<String> toListMulti(Separator... seps)
	{
		if (this.string == null) return null;
		List<String> v = new ArrayList<>();
		for (Separator sep : seps)
		{
			v = Arrays.asList(this.string.split(sep.getRegex()));
			if (v.size() > 1) return v;
		}
		return v;
	}

	/**
	 * @param sep
	 * @return the list of string : 1 element / line delimited by the separator.
	 */
	public List<String> toList(Separator sep)
	{
		if (this.string == null) return null;
		return Arrays.asList(this.string.split(sep.getRegex()));
	}

	/**
	 * Try to separate string with different separator and returns the list if it is bigger than 1 <br/> Try with :
	 * COMMA, PIPE, SEMICOLON.<br/> <U>Condition :</U> <UL> <LI>if the local string is null
	 * returns null,</LI> <LI>if the resulted list smaller than 2 returns null.</LI> </UL>
	 *
	 * @return the list of string if bigger than 1 of null otherwise.
	 */
	public List<String> toList()
	{
		if (this.string == null) return null;
		List<String> v;
		for (Separator sep : EnumSet.complementOf(EnumSet.of(Separator.SPACE, Separator.DOT)))
		{
			v = this.toList(sep);
			if (v.size() > 1) return v;
		}
		return this.toList(Separator.COMMA);
	}

}
