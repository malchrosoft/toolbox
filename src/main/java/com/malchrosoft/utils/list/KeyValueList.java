/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malchrosoft.utils.list;

import java.util.Vector;

/**
 * @author Aymeric Malchrowicz
 * @deprecated use Map<T, E> instead
 */
public class KeyValueList extends Vector<KeyValue> 
{
    public KeyValueList()
    {
        super();
    }

    /**
     * Returns the value according to the key
     * @param key the key
     * @return the value according to the key
     */
    public String getValueByKey(String key)
    {
        for (KeyValue kv: this)
        {
            if (kv.getKey().equals(key)) return kv.getValue();
        }
        return null;
    }
}
