/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.utils.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class TextFileManager
{
	private long fileLength;
	private String filePath;
	private JProgressBar progressBar;
	private BufferedReader buff;

	private TextFileManager(long fileLength, String filePath,
		JProgressBar progressBar, BufferedReader buff)
	{
		this.fileLength = fileLength;
		this.filePath = filePath;
		this.progressBar = progressBar;
		this.buff = buff;
	}
	
	/**
	 * Saves text content in file in UTF-8 by default
	 * @param fileName the file path
	 * @param content the content
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static void saveTextToFile(String fileName, String content)
		throws FileNotFoundException, IOException
	{
		saveTextToFile(fileName, content, "UTF-8");
	}
	

	/**
	 * Saves text content in file.
	 * @param fileName the file path
	 * @param content the content
	 * @param charsetEncodeValue the charset encode value
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static void saveTextToFile(String fileName, String content, String charsetEncodeValue)
		throws FileNotFoundException, IOException
	{
		File file = new File(fileName);
		if (!file.exists())
		{
			file.createNewFile();
		}
		FileOutputStream outputFile = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(outputFile, charsetEncodeValue);
		writer.write(content);
		writer.close();
	}

	/**
	 * Returns the text file content from the file name.
	 * @param fileName the file path
	 * @param progressBar the progressBar (It can be null)
	 * @return the text file content from the file name
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static String getTextFileContent(String fileName,
		JProgressBar progressBar) throws FileNotFoundException, IOException
	{
		return TextFileManager.getTextFromBufferedReader(
			TextFileManager.getBufferedReaderFromFilePath(fileName,
			progressBar).buff);
	}

	/**
	 * Returns the text content from a buffered file reader.
	 * @param buff the text file buffered reader
	 * @return the text content from a buffered file reader
	 * @throws java.io.IOException
	 */
	public static String getTextFromBufferedReader(BufferedReader buff)
		throws IOException
	{
		String line = buff.readLine();
		String textContent = line; // Copie de la première ligne
		line = buff.readLine();
		while (line != null)
		{
			textContent += "\n" + line;
			line = buff.readLine();
		}
		buff.close();
		return textContent;
	}

	private static TextFileManager getBufferedReaderFromFilePath(
		String filePath, JProgressBar pBar) throws FileNotFoundException
	{
		File file = new File(filePath);
		if (!file.exists() || !file.canRead()) return null;
		FileReader reader = new FileReader(file);
		return new TextFileManager(file.length(), filePath, pBar,
			new BufferedReader(reader));
	}

	/**
	 * Returns the content of the text file in a vector : 1 element / line.
	 * @param fileName the file full path
	 * @param progressBar the progressBar (It can be null)
	 * @return the content of the text file in a vector : 1 element / line
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static List<String> getTextFileContentList(String fileName,
		JProgressBar progressBar) throws FileNotFoundException, IOException
	{
		return TextFileManager.getListFromBufferedReader(
			TextFileManager.getBufferedReaderFromFilePath(fileName,
			progressBar));
	}

	/**
	 * Returns the content of the buffered reader in a vector : 1 element 
	 * / line.
	 * @param managet a TextFileManager
	 * @return the content of the buffered reader in a vector : 1 element 
	 * / line
	 * @throws java.io.IOException
	 */
	private static List<String> getListFromBufferedReader(
		TextFileManager manager) throws IOException
	{
		String line = null;
		List<String> list = new ArrayList<String>();
		if (manager.progressBar != null) manager.progressBar.setValue(0);
		int currentLength = 0;
		do
		{
			line = manager.buff.readLine();
			if (line != null && !line.isEmpty()) list.add(line);
			if (manager.progressBar != null)
			{
				currentLength += line.length();
				manager.progressBar.setValue(
					Math.round((currentLength * 100) / (float) manager.fileLength));
			}
		} while (line != null);
		manager.buff.close();
		return list;
	}
}
