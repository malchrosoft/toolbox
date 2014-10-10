/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.ihm;

import com.malchrosoft.ihm.model.MComboBoxModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * MalchroSoft JComboxBox wrapper / helper
 * @param <T> the managed type
 *
 */
public class MComboBox<T> extends JComboBox<T>
{
	/**
	 * Creates a
	 * <code>MNamedComboBox</code> with a default data model. The default data model is an empty list of objects. Use
	 * <code>addItem</code> to add items. By default the first item in the data model becomes selected.
	 *
	 * @see DefaultComboBoxModel
	 */
	public MComboBox()
	{
		super(new MComboBoxModel<T>());
	}

	/**
	 * Creates a
	 * <code>MNamedComboBox</code> that contains the elements in the specified List. By default the first item in the vector
	 * (and therefore the data model) becomes selected.
	 *
	 * @param items an item list to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public MComboBox(List<T> items)
	{
		super(new MComboBoxModel<T>(items));
	}

	/**
	 * Creates a
	 * <code>MNamedComboBox</code> that contains the elements in the specified array. By default the first item in the array
	 * (and therefore the data model) becomes selected.
	 *
	 * @param items an array of objects to insert into the combo box
	 * @see MComboBoxModel
	 */
	public MComboBox(T[] items)
	{
		super(new MComboBoxModel<T>(items));
	}

	public T getSelected()
	{
		return (T) super.getSelectedItem();
	}

}
