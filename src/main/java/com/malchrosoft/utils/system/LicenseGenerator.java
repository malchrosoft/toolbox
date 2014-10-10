package com.malchrosoft.utils.system;

/**
 * 
 */

/**
 * @author amalchrowicz
 * 
 */
public class LicenseGenerator
{

	private LicenseGenerator()
	{

	}

	public static String generate(String code3, int majorVersion)
	{
		String num = "";
		num += randInt() + "" + randInt() + randChar() + randInt() + "-";
		num += randInt() + "" + randChar() + randChar() + randInt() + "-";
		num += majorVersion + code3.substring(0, 3) + "-";
		num += rand() + rand() + rand() + rand();

		return num;
	}

	private static String rand()
	{
		boolean isChar = ((int) (Math.random() * 2)) == 1;
		if (isChar)
			return randChar() + "";
		return randInt() + "";
	}

	private static int randInt()
	{
		return (int) (Math.random() * 9 + 1);
	}

	private static char randChar()
	{
		return (char) ((int) (Math.random() * (90 - 65) + 65));
	}

	public static void main(String[] args)
	{
		for (int i = 0; i < 100; i++)
			System.out.println(generate("H2M", 2));
		System.out.println(generate("MrM", 1));
	}

}
