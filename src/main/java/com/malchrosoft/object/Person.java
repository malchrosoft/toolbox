/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.object;

import com.malchrosoft.utils.list.KeyValue;
import com.malchrosoft.utils.list.KeyValueList;
import java.util.*;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class Person
{
	private String name;
	private String forename;
	private Date birthDate;
	private Sex sexKey;
	private List<Person> parents;
	private List<Person> children;

	public static enum Sex
	{
		MALE, FEMALE, UNKNOWN
	}

	/**
	 * Creates a new person
	 *
	 * @param name the name (family name)
	 * @param forename the forename
	 */
	public Person(String name, String forename)
	{
		this(name, forename, null, Sex.UNKNOWN);
	}

	/**
	 * Creates a new person
	 *
	 * @param name the name (Family name)
	 * @param forename the farename
	 * @param birthDate the birth day date
	 * @param sex the sex
	 */
	public Person(String name, String forename, Date birthDate, Sex sex)
	{
		this.name = name;
		this.forename = forename;
		this.birthDate = birthDate;
		this.sexKey = sex;
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	/**
	 * Returns the birth date. Can be null
	 *
	 * @return the birth date
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getForename()
	{
		return forename;
	}

	public void setForename(String forename)
	{
		this.forename = forename;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Sex getSexKey()
	{
		return sexKey;
	}

	public String getSex()
	{
		return this.sexKey.name();
	}

	public void setSex(Sex sex)
	{
		this.sexKey = sex;
	}

	public List<Person> getChildren()
	{
		return children;
	}

	public List<Person> getParents()
	{
		return parents;
	}

	/**
	 * Adds a new child
	 *
	 * @param child the child
	 */
	public void addChild(Person child)
	{
		this.children.add(child);
	}

	/**
	 * Adds a new parent
	 *
	 * @param parent
	 */
	public void addParent(Person parent)
	{
		this.parents.add(parent);
	}

	@Override
	public String toString()
	{
		return (this.getForename() + " " + this.getName()).trim();
	}

	public static class PersonListGenerator
	{
		private PersonListGenerator()
		{
			// TODO to externalize
		}
		
		public static enum IdentityElement
		{
			NAME, FORENAME, SEX
		}

		public static enum ElementDifferentiation
		{
			IN_UPPER_CASE, IN_LOWER_CASE, FIRST_UPPER_CASE_ONLY
		}

		/**
		 * Retruns the identityElement key name.
		 *
		 * @param idE the identity element
		 * @return the identityElement key name in a string
		 */
		public static String getIdentityElementKeyName(IdentityElement idE)
		{
			return idE.name();
		}

		/**
		 * Returns the person list from a vector of string.
		 *
		 * @param personIdentities the vector of person identities
		 * @param elementDiff a KeyValueList listing each person identity
		 * element asthe KEY and the corresponding elementDifferentiation name
		 * as the VALUE.
		 * <p>Example : new KeyValueList().add(new KeyValue(
		 * PersonListGenerator.IdentityElement.NAME.name(),
		 * PersonListGenerator.ElementDifferentiation.IN_UPPER_CASE.name()));
		 * </p><p>KEY = PersonListGenerator.IdentityElement.[...].name()<br/>
		 * VALUE = PersonListGenerator.ElementDifferentiation.[...].name()</p>
		 * @param separator the separator between each identity element
		 * @return the person list from a vector of string
		 */
		public static List<Person> toPersonList(
			List<String> personIdentities,
			KeyValueList elementDiff, String separator)
		{
			List<Person> personList = new ArrayList<Person>();
			Person p;
			for (String s : personIdentities)
			{
				p = personFromIdentitiesAndElementDiff(s,
					elementDiff, separator);
				personList.add(p);
//                if (s.contains(".")) Log.debug("toPersonList : (s : " + s + ")" +
//                    ", (elementDiff : " + elementDiff + "), (separator : " +
//                    separator + ") = Name : " +
//                    p.getName() + " Forename : " + p.getForename());
			}
			return personList;
		}

		/**
		 * Returns the string list of the person id according to the id order.
		 *
		 * @param persons the persons
		 * @param idOrder the identity element order
		 * @return the string list of the person
		 */
		public static List<String> toStringList(List<Person> persons,
			List<IdentityElement> idOrder)
		{
			List<String> pl = new ArrayList<String>();
			String cs = "";
			for (Person p : persons)
			{
				for (IdentityElement ie : idOrder)
				{
					cs += " " + getIdElement(p, ie);
				}
				pl.add(cs);
				cs = "";
			}
			return pl;
		}

		private static String getIdElement(Person p, IdentityElement ie)
		{
			if (ie == IdentityElement.FORENAME) return p.getForename();
			else if (ie == IdentityElement.NAME) return p.getName();
			else if (ie == IdentityElement.SEX) return p.getSex();
			return "";
		}

		private static Person personFromIdentitiesAndElementDiff(
			String identities, KeyValueList elementDiff, String sep)
		{
			String[] splitedId = identities.split(sep);
			String name = "";
			String forename = "";
			boolean treated;
			for (String cs : splitedId)
			{
				treated = false;
				for (KeyValue kv : elementDiff)
				{
					if (isInGoodCase(cs, getElementDifferentiationByName(
						kv.getValue())))
					{
						IdentityElement cie = getIdentityElementByName(
							kv.getKey());
						switch (cie)
						{
							case NAME:
								if (name.length() > 1 && sep.equals(" "))
									name += " ";
								name += cs;
								treated = true;
								break;
							case FORENAME:
								if (forename.length() > 1 && sep.equals(" "))
									forename += " ";
								forename += cs;
								treated = true;
								break;
						}
						if (treated) break;
					}
				}
			}
			if (name.length() < 1 && forename.length() < 1) name = identities;
			return new Person(name, forename);
		}

		public static KeyValue getDefaultNameElementDiff()
		{
			return new KeyValue(IdentityElement.NAME.name(),
				ElementDifferentiation.IN_UPPER_CASE.name());
		}

		public static KeyValue getDefaultForenameElementDiff()
		{
			return new KeyValue(IdentityElement.FORENAME.name(),
				ElementDifferentiation.FIRST_UPPER_CASE_ONLY.name());
		}

		private static boolean isInGoodCase(String s, ElementDifferentiation e)
		{
			boolean test = true;
			if (e == ElementDifferentiation.FIRST_UPPER_CASE_ONLY)
			{
				char cc;
				for (int i = 0; i < s.length(); i++)
				{
					cc = s.charAt(i);
					if (i == 0 && !Character.isUpperCase(cc) &&
						Character.isLetter(cc)) test = false;
					else if (i > 0 && !Character.isLowerCase(cc) &&
						Character.isLetter(cc)) test = false;
				}
			}
			else if (e == ElementDifferentiation.IN_UPPER_CASE)
			{
				for (char cc : s.toCharArray())
					if (!Character.isUpperCase(cc) &&
						Character.isLetter(cc)) test = false;
			}
			else if (e == ElementDifferentiation.IN_LOWER_CASE)
			{
				for (char cc : s.toCharArray())
					if (!Character.isLowerCase(cc) &&
						Character.isLetter(cc)) test = false;
			}
			else test = false;
			return test;
		}

		private static IdentityElement getIdentityElementByName(String name)
		{
			for (IdentityElement ie : IdentityElement.values())
				if (ie.name().equals(name)) return ie;
			return IdentityElement.NAME;
		}

		private static ElementDifferentiation getElementDifferentiationByName(
			String name)
		{
			for (ElementDifferentiation e : ElementDifferentiation.values())
				if (e.name().equals(name)) return e;
			return ElementDifferentiation.FIRST_UPPER_CASE_ONLY;
		}
	}

	public static class PersonListManager
	{
		private PersonListManager()
		{
			// TODO to externalize
		}
		
		public static List<Person> sortList(List<Person> vp)
		{
			List<String> vs = new ArrayList<String>();
			for (Person p : vp)
				vs.add((p.getName() + ":" + p.getForename()).trim());
			Collections.sort(vs);
			KeyValueList elementDiff = new KeyValueList();
			elementDiff.add(PersonListGenerator.getDefaultNameElementDiff());
			elementDiff.add(PersonListGenerator.getDefaultForenameElementDiff());
			return PersonListGenerator.toPersonList(vs, elementDiff, ":");
		}
	}
}
