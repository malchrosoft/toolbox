/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.utils.net;

import com.malchrosoft.utils.io.TextFileManager;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class NetFileImport implements Runnable
{
	/**
	 * Returns the text file content from the URL.
	 * @param url the URL of the text file
	 * @return the text file content from the URL
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public synchronized static String getTextFileContentFromURL(String url, String charsetEncorder)
		throws MalformedURLException, IOException
	{
		return NetFileImport.getTextFileContentFromConnection(
			NetFileImport.getOpenedConnectionFromURL(url), charsetEncorder);
	}

	/**
	 * Returns the text file content from the URL.
	 * @param connection the HttpURLConnection
	 * @return the text file content from the URL
	 * @throws java.io.IOException
	 */
	public synchronized static String getTextFileContentFromConnection(
		HttpURLConnection connection, String charsetEncoder) throws IOException
	{
		BufferedReader buff = new BufferedReader(
			new InputStreamReader(connection.getInputStream(), charsetEncoder));
		return TextFileManager.getTextFromBufferedReader(buff);
	}

	/**
	 * Saves a file from URL
	 * @param filePath the file path
	 * @param url the URL
	 * @return true if operation ended correctly, false otherwise.
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public synchronized static boolean saveFileFromURL(String filePath,
		String url) throws MalformedURLException, IOException
	{
		return NetFileImport.saveFileFromWeb(filePath,
			NetFileImport.getOpenedConnectionFromURL(url));
	}

	/**
	 * Saves a file from URL
	 * @param filePath the file path
	 * @param connection the connection
	 * @return true if operation ended correctly, false otherwise.
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public synchronized static boolean saveFileFromWeb(String filePath,
		HttpURLConnection connection) throws MalformedURLException, IOException
	{
		InputStream in = connection.getInputStream();
		FileOutputStream out = new FileOutputStream(filePath);
		int current = in.read();
		while (current != -1)
		{
			out.write(current);
			current = in.read();
		}
		in.close();
		out.close();
		connection.disconnect();
		return true;
	}

	/**
	 * Return the connection from the URL with GET request method.
	 * @param url the URL
	 * @return the connection from the URL opened
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public synchronized static HttpURLConnection getOpenedConnectionFromURL(
		String url) throws MalformedURLException, IOException
	{
		HttpURLConnection con = NetFileImport.getConnectionFromURL(url, "GET");
		con.connect();
		return con;
	}

	/**
	 * Return the connection from the URL with specified request method.<br/>
	 * Set the method for the URL request, one of:
	 * <UL>
	 *  <LI>GET
	 *  <LI>POST
	 *  <LI>HEAD
	 *  <LI>OPTIONS
	 *  <LI>PUT
	 *  <LI>DELETE
	 *  <LI>TRACE
	 * </UL> are legal, subject to protocol restrictions.  The default
	 * method is GET.
	 *
	 * @param url the URL
	 * @param requestMethod the request method
	 * @return  the connection from the URL with specified request method.
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public synchronized static HttpURLConnection getConnectionFromURL(String url,
		String requestMethod) throws MalformedURLException, IOException
	{
		URL page = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) page.openConnection();
		connection.setRequestMethod(requestMethod);
		return connection;
	}

	private enum IMPORT_TYPE
	{
		SAVE_FILE_FROM_URL,
		TEXT_FILE_CONTENT_FROM_URL,
		TEXT_FILE_CONTENT,
		DEFAULT
	}
	private IMPORT_TYPE mode;
	private HttpURLConnection connection;
	private String url;
	private String filePath;
	private boolean OkOrFinished;

	public NetFileImport(String url)
	{
		this.url = url;
		this.connection = null;
		this.OkOrFinished = false;
	}

	public NetFileImport(HttpURLConnection connection)
	{
		this.connection = connection;
		this.url = this.connection.getURL().getFile();
		this.OkOrFinished = false;
	}

	private void start()
	{
		new Thread(this).start();
	}

	public void saveWebFileToLocalFile(String filePath)
		throws MalformedURLException, IOException
	{
		this.mode = NetFileImport.IMPORT_TYPE.SAVE_FILE_FROM_URL;
		this.filePath = filePath;
		this.start();
	}

	public HttpURLConnection getConnection()
	{
		return connection;
	}

	public void setConnection(HttpURLConnection connection)
	{
		this.connection = connection;
	}

	public boolean isOkOrFinished()
	{
		return OkOrFinished;
	}

	@Override
	public void run()
	{
		try
		{
			switch (this.mode)
			{
				case SAVE_FILE_FROM_URL:
					if (this.connection == null && this.url != null)
						this.OkOrFinished = NetFileImport.saveFileFromURL(
							this.filePath, this.url);
					else this.OkOrFinished = NetFileImport.saveFileFromWeb(
							this.filePath, this.connection);
					break;
				case TEXT_FILE_CONTENT:
				case TEXT_FILE_CONTENT_FROM_URL:
				case DEFAULT:
					this.OkOrFinished = true;
				// nothing
			}
		} catch (Exception ex)
		{
			Logger.getLogger(NetFileImport.class.getName()).log(
				Level.SEVERE, null, ex);
			this.OkOrFinished = true;
		}
	}
}
