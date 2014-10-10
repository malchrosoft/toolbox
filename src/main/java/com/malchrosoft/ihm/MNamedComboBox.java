/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.ihm;

import com.malchrosoft.ihm.model.MComboBoxModel;
import com.malchrosoft.object.NamedObject;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * MalchroSoft JComboxBox wrapper / helper
 * @param <T> the managed type automatically encapsulated in NamedObjects<T>
 *
 */
public class MNamedComboBox<T> extends JComboBox<NamedObject<T>>
{
	/**
	 * Creates a
	 * <code>MNamedComboBox</code> with a default data model. The default data model is an empty list of objects. Use
	 * <code>addItem</code> to add items. By default the first item in the data model becomes selected.
	 *
	 * @see DefaultComboBoxModel
	 */
	public MNamedComboBox()
	{
		super(new MComboBoxModel<NamedObject<T>>());
	}

	/**
	 * Creates a
	 * <code>MNamedComboBox</code> that contains the elements in the specified List. By default the first item in the vector
	 * (and therefore the data model) becomes selected.
	 *
	 * @param items an item list to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public MNamedComboBox(List<NamedObject<T>> items)
	{
		super(new MComboBoxModel<NamedObject<T>>(items));
	}

	/**
	 * Creates a
	 * <code>MNamedComboBox</code> that contains the elements in the specified array. By default the first item in the array
	 * (and therefore the data model) becomes selected.
	 *
	 * @param items an array of objects to insert into the combo box
	 * @see MComboBoxModel
	 */
	public MNamedComboBox(NamedObject<T>[] items)
	{
		super(new MComboBoxModel<NamedObject<T>>(items));
	}

	public T getSelected()
	{
		return this.getSelectedItem().get();
	}

	/**
	 * @return the selected named object
	 */
	@Override
	public NamedObject<T> getSelectedItem()
	{
		return (NamedObject<T>) super.getSelectedItem();
	}

	/**
	 *
	 * Adds an item to the item list. This method works only if the
	 * <code>MNamedComboBox</code> uses a mutable data model. <p> <strong>Warning:</strong> Focus and keyboard navigation
	 * problems may arise if you add duplicate String objects. A workaround is to add new objects instead of String
	 * objects and make sure that the toString() method is defined. For example:
	 * <pre>
	 *   comboBox.addItem(makeObj("Item 1"));
	 *   comboBox.addItem(makeObj("Item 1"));
	 *   ...
	 *   private Object makeObj(final String item)  {
	 *     return new Object() { public String toString() { return item; } };
	 *   }
	 * </pre>
	 * @param displayText the text to display
	 * @param item the item to add to the list
	 * @see MutableComboBoxModel
	 */
	public void addItem(String displayText, T item)
	{
		super.addItem(new NamedObject<T>(displayText, item));
	}

}
