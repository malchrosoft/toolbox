package com.malchrosoft.utils.net;

import com.malchrosoft.debug.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * 
 */
/**
 * @author amalchrowicz
 * 
 */
public class UsageSender
{
	public static void send(String description)
	{
		try
		{
			URL usageURL = new URL("http://www.malchrosoft.com/stats/usages.php/?description=" +
				URLTranformer.encodeURL(description));
			usageURL.openConnection();
			usageURL.getContent();
		} catch (MalformedURLException e)
		{
			Log.error(e.getMessage());
		} catch (IOException e)
		{
			Log.info("No internet conexion available !");
		}

	}

	public static void main(String[] args)
	{
		send("UsageSender-TEST");
	}
}
