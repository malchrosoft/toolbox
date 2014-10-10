/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.game.sprite;

import com.malchrosoft.debug.Log;
import com.malchrosoft.game.GameConstants;
import com.malchrosoft.graphics.utils.ImageManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Element;


/**
 * Animated sprite
 * @author Aymeric Malchrowicz
 */
public class AnimatedSprite
{
	private static final String XML_TITLE = AnimatedSprite.class.getName();
	private Point2D absoluteLocation;
	private Point2D relativeLocation;
	private Point2D previousrelativeLocation;
	protected BufferedImage[] bimg;
	private BufferedImage[] hFlippedBimg;
	private List<AnimatedSpriteAnimation> animations;
	private AnimatedSpriteAnimation currentAnimation;
	private int width;
	private int height;
	private int currentSpeedStep = 0;
	/** elements saved for xml file. */
	private String imagePath;
	private int colCount;
	private int rowCount;
	private Color transparentColor;

	/**
	 * Creates a new Animated sprite
	 * @param imagePath the image path to load it
	 * @param colCount the col count
	 * @param rowCount the row count
	 */
	public AnimatedSprite(String imagePath, int colCount, int rowCount)
	{
		this(imagePath, colCount, rowCount, null);
	}

	/**
	 * Creates a new Animated sprite
	 * @param imagePath the image path to load it
	 * @param colCount the col count
	 * @param rowCount the row count
	 * @param transparentColor the transparent color
	 */
	public AnimatedSprite(String imagePath, int colCount, int rowCount, Color transparentColor)
	{
		BufferedImage global;
		if (transparentColor != null)
			global = ImageManager.loadTransparentImage(imagePath, transparentColor);
		else global = ImageManager.loadImage(imagePath);

		this.bimg = ImageManager.splitImage(global, colCount, rowCount);
		// Keep the hizontal flipped images at the same position.
		if (this.bimg.length > 0)
		{
			this.hFlippedBimg = new BufferedImage[bimg.length];
			for (int i = 0; i < bimg.length; i++)
				this.hFlippedBimg[i] = ImageManager.horizontalflip(bimg[i]);
		}
		this.animations = new ArrayList<AnimatedSpriteAnimation>();
		this.width = bimg[0].getWidth();
		this.height = bimg[0].getHeight();
		this.setAbsoluteLocation(new Point(50, 50));
		this.previousrelativeLocation = this.absoluteLocation;
		this.relativeLocation = (Point2D) absoluteLocation.clone();

		// elements for xml file
		this.imagePath = imagePath;
		this.colCount = colCount;
		this.rowCount = rowCount;
		this.transparentColor = transparentColor;
	}

	/**
	 * Adds an animation
	 * @param animation the animation to add
	 * @return true if the animation was added, false otherwise.
	 */
	public boolean add(AnimatedSpriteAnimation animation)
	{
		for (AnimatedSpriteAnimation an : this.animations)
			if (an.getName().equals(animation.getName()))
			{
				Log.error("Animation with same name already exist ! : " + animation.getName());
				return false;
			}
		return this.animations.add(animation);
	}

	/**
	 * Set the animation to run by its name.
	 * @param animationName the animation name
	 * @return true if the name could find, false otherwise
	 */
	public boolean setAnimation(CharacterAnimationName animationName)
	{
		return this.setAnimation(animationName.name());
	}

	/**
	 * Set the animation to run by its name.
	 * @param animationName the animation name
	 * @return true if the name could find, false otherwise
	 */
	public boolean setAnimation(String animationName)
	{
		if (this.currentAnimation != null && this.currentAnimation.getName().equals(animationName)) return true;
		for (AnimatedSpriteAnimation an : this.animations)
			if (an.getName().equals(animationName))
			{
				this.currentAnimation = an;
				return true;
			}
		return false;
	}

	/**
	 * @return the current animation name
	 */
	public String getCurrentAnimationName()
	{
		if (this.currentAnimation != null)
			return this.currentAnimation.getName();
		return null;
	}

	public void setCurrentAnimationSpeed(int speed)
	{
		this.currentAnimation.setAnimationSpeed(speed);
	}

	protected AnimatedSpriteAnimation getCurrentAnimation()
	{
		return currentAnimation;
	}

	/**
	 * Sets the absolute location (according to the MAP)
	 * @param location the absolute location
	 */
	public void setAbsoluteLocation(Point2D location)
	{
		this.absoluteLocation = location;
	}

	/**
	 * @return the absolute location according to the MAP
	 */
	public Point2D getAbsoluteLocation()
	{
		return (Point2D) absoluteLocation.clone();
	}

	/**
	 * @return the relative location according to the view
	 */
	public Point2D getRelativeLocation()
	{
		return relativeLocation;
	}

	public Rectangle getAbsoluteBounds()
	{
		return new Rectangle((int) this.absoluteLocation.getX(), (int) this.absoluteLocation.getY(),
			this.getWidth(), this.getHeight());
	}

	public Rectangle getRelativeBounds()
	{
		return new Rectangle((int) this.relativeLocation.getX(), (int) this.relativeLocation.getY(),
			this.getWidth(), this.getHeight());
	}

	/**
	 * Sets the relative location according to the drawing in a view
	 * @param relativeLocation the relative location
	 */
	public void setRelativeLocation(Point2D relativeLocation)
	{
		this.relativeLocation = relativeLocation;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	protected int getCurrentSpeedStep()
	{
		return currentSpeedStep;
	}

	public void draw(Graphics2D g)
	{
		if (currentAnimation.getImageFlipping() == AnimatedSpriteAnimation.ImageFlipping.HORIZONTAL_FLIP)
			g.drawImage(hFlippedBimg[currentAnimation.getCurrentPosition(currentSpeedStep < 1)], null, (int) relativeLocation.
				getX() + this.currentAnimation.getHorizontalDecal(), (int) relativeLocation.getY() + this.currentAnimation.
				getVerticalDecal());
		else
			g.drawImage(bimg[currentAnimation.getCurrentPosition(currentSpeedStep < 1)], null, (int) relativeLocation.
				getX()
				+ this.currentAnimation.getHorizontalDecal(), (int) relativeLocation.getY() + this.currentAnimation.
				getVerticalDecal());

		// Refresh current speed step to refresh the animation according to the animation speed setted
		if (currentSpeedStep < 1)
			currentSpeedStep = currentAnimation.getAnimationSpeed() / GameConstants.VITESSE_RAFRAICHISSEMENT;
		else currentSpeedStep--;

		// Set these for next time we need to erase
		this.previousrelativeLocation.setLocation(relativeLocation);
	}

	public void draw(Graphics2D g, int x, int y)
	{
		g.drawImage(bimg[currentAnimation.getCurrentPosition(currentSpeedStep < 1)], null, x, y);
	}

	public Element toXml()
	{
		Element element = new Element(XML_TITLE);

		element.setAttribute(new Attribute("image", this.imagePath));
		element.setAttribute(new Attribute("col", this.colCount + ""));
		element.setAttribute(new Attribute("row", this.rowCount + ""));
		element.setAttribute(new Attribute("X", (int)this.absoluteLocation.getX() + ""));
		element.setAttribute(new Attribute("Y", (int)this.absoluteLocation.getY() + ""));
		
		if (this.transparentColor != null)
		{
			element.setAttribute(new Attribute("r", this.transparentColor.getRed() + ""));
			element.setAttribute(new Attribute("g", this.transparentColor.getGreen() + ""));
			element.setAttribute(new Attribute("b", this.transparentColor.getBlue() + ""));
		}

		return element;
	}
}
