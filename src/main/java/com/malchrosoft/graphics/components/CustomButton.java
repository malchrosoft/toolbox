/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.graphics.components;

import java.awt.Graphics;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class CustomButton extends JButton
{
    public CustomButton()
    {
        super();
    }
    
    public CustomButton(Icon icon)
    {
        super(icon);
    }
    
    public CustomButton(String text)
    {
        super(text);
    }
    
    public CustomButton(Action a)
    {
        super(a);
    }
    
    @Override
    public void paintComponents(Graphics g)
    {
        super.paintComponents(g);
    }
    
}
