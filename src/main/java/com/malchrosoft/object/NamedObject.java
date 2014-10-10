/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.object;

/**
 * This class encapsulates object with a name
 * @param <T> the encpasulated object type
 */
public class NamedObject<T>
{
	private String name;
	private T object;

	/**
	 * Creates a new NamedObject
	 * @param name the text to display
	 * @param object
	 */
	public NamedObject(String name, T object)
	{
		this.name = name;
		this.object = object;
	}
	
	public T get()
	{
		return object;
	}

	@Override
	public final String toString()
	{
		return name;
	}

}
