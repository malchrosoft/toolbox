/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.utils.io;

import com.malchrosoft.debug.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class PropertiesReader
{
    private Locale locale;
    protected Properties properties;
    private String propertiesFileName;
    private String extention;
    public static String PROPERTIES_EXTENTION = ".properties";

    /**
     * Construit un lecteur de propriétés selon la langue désignée par son sigle
     * et le sigle du pays.
     * <p>Exemple : </u><br> 
     *  - FR -> France; fr -> français, <br>
     *  - EN -> England; en -> english, <br>
     *  - US -> United States -> en -> english...</p>
     * @param laguage
     * @param country
     * @param propertiesStartFileName
     */
    public PropertiesReader(String propertiesStartFileName, String country,
        String langue)
    {
        this.locale = null;
        Locale[] localeList = Locale.getAvailableLocales();
        for (Locale l : localeList)
        {
            if (l.getCountry().equals(country.toUpperCase()) && l.getLanguage()
                .equals(langue.toLowerCase()))
            {
                this.locale = new Locale(langue.toLowerCase(),
                    country.toUpperCase());
            }
        }

        if (this.locale == null) this.locale = Locale.getDefault();
        Locale.setDefault(this.locale);


        String composedPropertiesFileName = propertiesStartFileName + 
            this.locale + PropertiesReader.PROPERTIES_EXTENTION;
        this.loadPropertiesFile(composedPropertiesFileName);
    }

//    public PropertiesReader(String propertiesStartFileName)
//    {
//        this(propertiesStartFileName, Locale.getDefault().getCountry(),
//            Locale.getDefault().getLanguage());
//    }
    
    /**
     * Construit un lecteur de fichier
     * @param propertiesStartFileName
     * @param extention
     */
    protected PropertiesReader(String propertiesStartFileName, String extention)
    {
        this.extention = extention;
        this.loadPropertiesFile(propertiesStartFileName + "." + extention);
    }

    /**
     * Builds a properties reader from the file path
     * @param filePath the file path
     */
    public PropertiesReader(String filePath)
    {
        this.extention = FileUtils.getFileExtentionWithoutDot(filePath);
        this.loadPropertiesFile(filePath);
    }
    
    protected void loadPropertiesFile(String fileName)
    {
        this.properties = new Properties();
        this.setFileName(fileName);
        Log.debug(this.getClass().getName() + "@ " +
            "Properties file name : " + this.getFileName());
        try
        {
            this.properties.load(new FileInputStream(this.getFileName()));
        } 
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        } 
    }

    /**
     * Retourne la chaine de caractère correspondant à la clé donnée en 
     * paramètre.
     * @param stringKey la clé
     * @return la chaine de caractère correspondante
     */
    public String getProperty(String key)
    {
        if (this.properties.getProperty(key) == null)
            return "Error : " + key + " not found";
        return this.properties.getProperty(key);
    }

    public Locale getLocale()
    {
        return this.locale;
    }

    public String getFileName()
    {
        return this.propertiesFileName;
    }

    protected void setFileName(String propertiesFileName)
    {
        this.propertiesFileName = propertiesFileName;
    }

    public String getExtention()
    {
        if (this.extention == null || this.extention.equals(""))
        {
            this.extention = this.propertiesFileName.substring(
                this.propertiesFileName.indexOf("."));
        }
        return this.extention;
    }
}
