/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.ihm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

/**
 * @author Aymeric Malchrowicz
 * @param <TYPE> the managed type
 */
public class MComboBoxModel<TYPE extends Object> extends AbstractListModel<TYPE> implements MutableComboBoxModel<TYPE>,
	Serializable
{
	private List<TYPE> objects;
	private TYPE selectedObject;

	/**
	 * Constructs an empty MComboBoxModel object.
	 */
	public MComboBoxModel()
	{
		objects = new ArrayList<TYPE>();
	}

	/**
	 * Constructs a MComboBoxModel object initialized with an array of objects.
	 *
	 * @param items an array of Object objects
	 */
	public MComboBoxModel(final TYPE items[])
	{
		this(Arrays.asList(items));
	}

	/**
	 * Constructs a MComboBoxModel object initialized with a vector.
	 *
	 * @param v a Vector object ...
	 */
	public MComboBoxModel(List<TYPE> v)
	{
		objects = v;
		if (getSize() > 0)
		{
			selectedObject = getElementAt(0);
		}
	}

	// implements javax.swing.ComboBoxModel
	/**
	 * Set the value of the selected item. The selected item may be null. <p>
	 * @param anObject The combo box value or null for no selection.
	 */
	public void setSelected(TYPE anObject)
	{
		if ((selectedObject != null && !selectedObject.equals(anObject)) ||
			selectedObject == null && anObject != null)
		{
			selectedObject = anObject;
			fireContentsChanged(this, -1, -1);
		}
	}

	@Override
	@Deprecated
	/**
	 * @deprecated use setSelected instead
	 */
	public void setSelectedItem(Object anItem)
	{
		this.setSelected(selectedObject);
	}

	// implements javax.swing.ComboBoxModel
	@Override
	public TYPE getSelectedItem()
	{
		return selectedObject;
	}

	// implements javax.swing.ListModel
	@Override
	public int getSize()
	{
		return objects.size();
	}

	// implements javax.swing.ListModel
	@Override
	public TYPE getElementAt(int index)
	{
		if (index >= 0 && index < objects.size())
			return objects.get(index);
		else
			return null;
	}

	/**
	 * Returns the index-position of the specified object in the list.
	 *
	 * @param anObject
	 * @return an int representing the index position, where 0 is the first position
	 */
	public int getIndexOf(Object anObject)
	{
		return objects.indexOf(anObject);
	}

	// implements javax.swing.MutableComboBoxModel
	@Override
	public void addElement(TYPE object)
	{
		objects.add(object);
		fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
		if (objects.size() == 1 && selectedObject == null && object != null)
		{
			setSelected(object);
		}
	}

	// implements javax.swing.MutableComboBoxModel
	@Override
	public void insertElementAt(TYPE anObject, int index)
	{
		objects.set(index, anObject);
		fireIntervalAdded(this, index, index);
	}

	// implements javax.swing.MutableComboBoxModel
	@Override
	public void removeElementAt(int index)
	{
		if (getElementAt(index) == selectedObject)
		{
			if (index == 0)
			{
				setSelected(getSize() == 1 ? null : getElementAt(index + 1));
			}
			else
			{
				setSelected(getElementAt(index - 1));
			}
		}
		objects.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	// implements javax.swing.MutableComboBoxModel
	@Override
	public void removeElement(Object anObject)
	{
		int index = objects.indexOf(anObject);
		if (index != -1)
		{
			removeElementAt(index);
		}
	}

	/**
	 * Empties the list.
	 */
	public void removeAllElements()
	{
		if (objects.size() > 0)
		{
			int firstIndex = 0;
			int lastIndex = objects.size() - 1;
			objects.clear();
			selectedObject = null;
			fireIntervalRemoved(this, firstIndex, lastIndex);
		}
		else
		{
			selectedObject = null;
		}
	}

}
