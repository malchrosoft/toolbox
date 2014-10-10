/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import java.awt.Component;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class SimpleInformationMessageDialog extends SimpleMessageDialog
{
    public SimpleInformationMessageDialog(String title, String message,
        String comment, Component parent)
    {
        super(title, message, comment, DefaultMessageIcons.getInfoIconPath(),
            parent);
    }
}
