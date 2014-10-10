/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.list;

import com.malchrosoft.utils.common.HasMultipleCodes;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Aymeric Malchrowicz
 */
public class LookupMultipleCodedManager<CODE, TYPE extends HasMultipleCodes<CODE>>
{
	private Map<CODE, TYPE> map;
	
	/**
	 * Creates a new LookupMultipleCodedManager that manages TYPEs according different unique CODEs
	 * @param types the types to manage
	 */
	public LookupMultipleCodedManager(TYPE... types)
	{
		this.map = new HashMap<CODE, TYPE>();
		for (TYPE t : types)
		{
			for (CODE c : t.getCodes())
			{
				this.map.put(c, t);
			}
		}
	}
	
	/**
	 * Return TYPE by a code that must be unique
	 * @param code
	 * @return 
	 */
	public TYPE get(CODE code)
	{
		return map.get(code);
	}
}
