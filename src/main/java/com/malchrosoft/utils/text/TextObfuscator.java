/**
 * 
 */
package com.malchrosoft.utils.text;

import com.malchrosoft.debug.Log;

/**
 * @author Aymeric Malchrowicz
 */
public class TextObfuscator
{
	public static String obfuscate(String text, String key)
	{
		String obf = "";
		if (key == null || key.isEmpty()) return "0000";
		for (int i = 0; i < text.length(); i++)
			obf += get2Digits((int) text.charAt(i) - 30) + get2Digits((int) key.charAt(i % key.length()) - 30);

		// add space each 8
		return addSpaces(obf, 8);
	}

	private static String addSpaces(String text, int every)
	{
		String newTxt = "";
		for (int i = 0, j = 1; i < (text.length() + (text.length() % every)); i += every, j++)
		{
			if (i + every >= (text.length() + (text.length() % every)))
				newTxt += text.substring(i);
			else
				newTxt += text.substring(i, i + every) + " ";
		}
		return newTxt;
	}

	public static String clarify(String obfuscated, String key)
	{
		String clarified = "";
		String checkKey = "";

		obfuscated = obfuscated.replaceAll(" ", "");

		for (int i = 0; i < obfuscated.length(); i += 4)
		{
			if (i + 1 > obfuscated.length())
				break;
			clarified += (char) (Integer.parseInt("" + obfuscated.charAt(i) + obfuscated.charAt(i + 1)) + 30);
			if (i + 4 > obfuscated.length())
				break;
			checkKey += (char) (Integer.parseInt("" + obfuscated.charAt(i + 2) + obfuscated.charAt(i + 3)) + 30);
		}
		if (!(key != null && !key.isEmpty() && checkKey.contains(key) || key.contains(checkKey)))
			return "keyError:" + obfuscated;
		return clarified;
	}

	private static String get2Digits(int digits)
	{
		// if < 10 add 0 before
		if (digits < 10)
			return "0" + digits;
		return digits + "";
	}

	public static void main(String[] args)
	{
		Log.get().setLevel(Log.Level.DEBUG);

		String text = "aymeric_ malchrowicz";
		String key = "ptstqv";
		String obf = obfuscate(text, key);

		Log.info(text + " /" + key);
		Log.info(obf);
		Log.info(clarify(obf, key));
		Log.info(clarify(obf, "badkey"));

		text = "aymeric#malchrowicz$*\"'(org)";
		key = "orga92";
		Log.info("\n\n" + text + " /" + key);
		obf = obfuscate(text, key);
		Log.info(obf);
		Log.info(clarify(obf, key));

		text = "aymeric#malchrowicz$*\"'(org)";
		key = "";
		Log.info("\n\n" + text + " /" + key);
		obf = obfuscate(text, key);
		Log.info(obf);
		Log.info(clarify(obf, key));

		System.exit(0);
	}
}
