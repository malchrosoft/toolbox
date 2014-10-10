/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malchrosoft.utils.list;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class KeyValue 
{
    private String key;
    private String value;
    
    /**
     * Creates a new key -> value
     * @param key the key
     * @param value the value
     */
    public KeyValue(String key, String value)
    {
        this.key = key;
        this.value = value;
    }
    
    public String getKey()
    {
        return this.key;
    }
    
    public String getValue()
    {
        return this.value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.getValue();
    }
}
