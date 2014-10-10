/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.utils.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ParametersFile extends PropertiesReader
{
	/**
	 * Construit un acesseur de fichier de type propriété avec une extention spécifique.
	 * @param propertiesStartFileName le nom de fichier
	 * @param specificExtention l'extention
	 */
	public ParametersFile(String propertiesStartFileName,
		String specificExtention)
	{
		super(propertiesStartFileName, specificExtention);
	}

	/**
	 * Sauvegarde le fichier de paramètres
	 * @throws java.io.IOException
	 */
	public void save() throws IOException
	{
		try
		{
			this.properties.store(new FileOutputStream(
				this.getFileName()), "PARAMETERS_FILE");
		} catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Rechargement des paramètres
	 */
	public void reload()
	{
		this.loadPropertiesFile(this.getFileName());
	}

	/**
	 * @param key
	 * @return the parameter
	 */
	public String getParameter(String key)
	{
		return this.properties.getProperty(key);
	}

	/**
	 * Ecrase la valeur du paramètre correspondant à la clé.
	 * @param key La clé du paramètre
	 * @param value la valeur
	 */
	public void setParameter(String key, String value)
	{
		this.properties.setProperty(key, value);
	}

}
