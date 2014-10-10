/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.listener;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ListDataAdapter implements ListDataListener
{
    @Override
    public void intervalAdded(ListDataEvent e)
    {
        // DO nothing
    }

    @Override
    public void intervalRemoved(ListDataEvent e)
    {
        // DO nithing
    }

    @Override
    public void contentsChanged(ListDataEvent e)
    {
        // DO nothing
    }

}
