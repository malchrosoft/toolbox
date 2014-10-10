/**
 * 
 */
package com.malchrosoft.utils.system;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * @author amalchrowicz
 * 
 */
public class MachineIdGenerator
{
	public static String getMachineID()
	{
		Properties props = System.getProperties();
		String macId = "#p:" + props.getProperty("os.arch") + props.getProperty("os.name").replace(" ", "") + props.
			getProperty("os.version") + props.getProperty("sun.desktop")
			+ props.getProperty("user.country") + props.getProperty("user.language");

		macId += "#proc:" + Runtime.getRuntime().availableProcessors();
		try
		{
			// Uses all the valide network cards mac adresses to compose the ID
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			HexBinaryAdapter hex = new HexBinaryAdapter();
			for (NetworkInterface netint : Collections.list(nets))
			{
				if (("" + hex.marshal(netint.getHardwareAddress())).length() == 12)
				{
					macId += "#mac:" + netint.getName() + ":" + hex.marshal(netint.getHardwareAddress());
				}
			}
		} catch (Exception e)
		{
		}
		return macId;
	}

	public static String getMachineLightID()
	{
		Properties props = System.getProperties();
		String macId = "#p:" + props.getProperty("os.arch") + props.getProperty("os.name").replace(" ", "") + props.
			getProperty("os.version") + props.getProperty("sun.desktop")
			+ props.getProperty("user.country") + props.getProperty("user.language");

		macId += "#proc:" + Runtime.getRuntime().availableProcessors();
		return macId;
	}

	private static void displayInterfaceInformation(NetworkInterface netint)
		throws SocketException
	{
		System.out.println("Display name: "
			+ netint.getName());
		System.out.println("Hardware address: "
			+ new HexBinaryAdapter().marshal(netint.getHardwareAddress()));
	}

	public static void main(String[] args)
	{
//		Properties props = System.getProperties();
//		for (Object key : props.keySet())
//		{
//			if (key instanceof String)
//			{
//				System.out.println(key + " : " + props.getProperty((String) key));
//			}
//		}

//		System.out.println(props.getProperty("os.arch"));
//		System.out.println(props.getProperty("sun.arch.data.model"));
//		System.out.println(props.getProperty("os.name"));
//		System.out.println(props.getProperty("os.version"));
//		System.out.println(props.getProperty("sun.desktop"));
//		System.out.println(props.getProperty("sun.cpu.endian"));
//		System.out.println(props.getProperty("sun.cpu.isalist"));
//		System.out.println(props.getProperty("user.country"));
//		System.out.println(props.getProperty("user.language"));
//		
//		
//		UUID uuid = UUID.randomUUID();
//		String randomUUIDString = uuid.toString();
//		
//		System.out.println("Random UUID String = 64c3b77f-17c5-4fff-926c-bcf2423640e5");
//		System.out.println("Random UUID String = " + randomUUIDString);
//		System.out.println("UUID version       = " + uuid.version());
//		System.out.println("UUID variant       = " + uuid.variant());
//		
//		try
//		{
//			System.out.println(InetAddress.getLocalHost().getHostAddress());
//			System.out.println(InetAddress.getLocalHost().getHostName());
//		} catch (UnknownHostException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		try
		{
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets))
				displayInterfaceInformation(netint);
		} catch (SocketException ex)
		{
			Logger.getLogger(MachineIdGenerator.class.getName()).log(Level.SEVERE, null, ex);
		}



		System.out.println("getMachineID() : " + getMachineID());


//		System.out.println(Runtime.getRuntime().availableProcessors());
//		
//		try
//		{
//			NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
//			System.out.println(ni.getName() + " " + new HexBinaryAdapter().marshal(ni.getHardwareAddress()));
//		} catch (SocketException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}



	}
}
