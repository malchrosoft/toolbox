/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malchrosoft.utils.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Vector;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class URLTranformer 
{
    /**
     * Reserved characters
     *  !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈ
     * ÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ
     */
    private static int[] reservedCharacters =
    {
        32, 47,
        58, 64,
        91, 96,
        123, 126,
        161, 254
    };

    /**
     * Returns the reserved characters list.
     * @return the reserved characters list
     */
    public static Vector<String> getReservedCharactersList()
    {
        Vector<String> v = new Vector<String>();
        for (int p = 0; p < reservedCharacters.length; p++)
        {
            // For all treatment % in first !!!
            if (p%2 == 0)
            {
                for (int i=reservedCharacters[p];
                    i < reservedCharacters[p+1]+1; i++)
                {
                    if (i == 37) v.add(0, "" + (char)i);
                    else v.add("" + (char)i);
                }
            }        
        }
        return v;
    }

    /**
     * Returns the ASCII decimal value according to the char.<br/>
     * Sample : URLTranformer.getDecimalASCIIValue('%') returns "37"
     * @param c the character
     * @return the ASCII decimal value according to the char
     */
    private static String getDecimalASCIIValue(char c)
    {
        return "" + (int)c;
    }

    /**
     * Returns the Hexadecimal value according to the char.<br/>
     * Sample : URLTranformer.getHexValue('%') returns "%25"
     * @param c the char.
     * @return the Hexadecimal value according to the char
     */
    private static String getHexValue(char c)
    {
        return "%" + Integer.toHexString((int) c);
    }

    /**
     * Encodes the reserved characters in the url
     * @param url the url
     * @return the url encoded
     */
    public static String encodeReservedCharacters(String url)
    {
        String encodeURL = url != null ? url : "";
        for (String s: getReservedCharactersList())
            encodeURL = encodeURL.replace(s, getHexValue(s.charAt(0)));
        return encodeURL;
    }

    /**
     * Decodes the reserved characters in the url
     * @param url the url
     * @return the url decoded
     */
    public static String decodeReservedCharacters(String url)
    {
		String decodeURL = url != null ? url : "";
        for (String s:getReservedCharactersList())
            decodeURL = decodeURL.replace(getHexValue(s.charAt(0)), s);
        return decodeURL;

    }

    public static String encodeURL(String url)
        throws UnsupportedEncodingException
    {
        String encodeURL = URLDecoder.decode(url, "UTF-8");
        encodeURL = URLEncoder.encode(encodeURL, "UTF-8");
        encodeURL = encodeURL.replace("+", "%20");
        encodeURL = encodeURL.replace("°", "%b0");
        return encodeURL;
    }
    
    public static String decodeURL(String url) 
        throws UnsupportedEncodingException
    {
        String decodeURL = URLDecoder.decode(url, "UTF-8");
        decodeURL = decodeURL.replace("%b0", "°");
        return decodeURL;
    }

    
    // TESTS
    public static void main(String[] args)
    {
        System.out.println("| DEC | HEX | Char |");
        for (int i=0; i < 255; i++)
        {
            System.out.println("| " + i + " | %" + Integer.toHexString(i) + " | " + (char)i + " |");
        }
        for (String s: getReservedCharactersList()) System.out.print(s);
        System.out.println();
        for (String s: getReservedCharactersList()) System.out.println(s + " " + 
            getDecimalASCIIValue(s.charAt(0)) + " " + getHexValue(s.charAt(0)));

        System.out.println("String : % !\"#$&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ");
        System.out.println("Encode : " + encodeReservedCharacters("% !\"#$&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ"));
        System.out.println("Decode : " + decodeReservedCharacters("%25%20%21%22%23%24%26%27%28%29%2a%2b%2c%2d%2e%2f%3a%3b%3c%3d%3e%3f%40%5b%5c%5d%5e%5f%60%7b%7c%7d%7e%a1%a2%a3%a4%a5%a6%a7%a8%a9%aa%ab%ac%ad%ae%af%b0%b1%b2%b3%b4%b5%b6%b7%b8%b9%ba%bb%bc%bd%be%bf%c0%c1%c2%c3%c4%c5%c6%c7%c8%c9%ca%cb%cc%cd%ce%cf%d0%d1%d2%d3%d4%d5%d6%d7%d8%d9%da%db%dc%dd%de%df%e0%e1%e2%e3%e4%e5%e6%e7%e8%e9%ea%eb%ec%ed%ee%ef%f0%f1%f2%f3%f4%f5%f6%f7%f8%f9%fa%fb%fc%fd%fe"));
    }
}
