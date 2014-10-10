package com.malchrosoft.game.sprite;

/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
/**
 *
 * @author Aymeric Malchrowicz
 */
public class AnimatedSpriteAnimation
{
	/**
	 * LoopMethod :<br/>
	 * <b>LOOP_REVERSE :</b> for looping the animation backwards, so it would be animated, if the frames
	 * were "1,2,3" like this, "1,2,3,2,1" etc.<br/>
	 * <b>LOOP_BEGINNING :</b> for looping the animation to the beggining, so it would be animated, if the frames
	 * were "1,2,3" like this, "1,2,1,2,3" etc.
	 */
	public static enum LoopMethod
	{
		LOOP_REVERSE, LOOP_BEGINNING, NO_LOOP
	}

	/**
	 * Image Flipping : <br/>
	 *
	 */
	public static enum ImageFlipping
	{
		NO_FLIP, HORIZONTAL_FLIP, VERTICAL_FLIP
	}

	/**
	 * Lood direction
	 */
	public static enum LoopDirection
	{
		backward, forward
	}
	/**
	 * Animation name just to be call by its name
	 */
	private String name;
	/**
	 * Image positions
	 */
	private int[] imagePositions;
	/**
	 * The current image position
	 */
	private int currentPositionIndex;
	private int animationSpeed;
	private LoopMethod loopMethod;
	private ImageFlipping imageFlipping;
	private int verticalDecal = 0;
	private int horizontalDecal = 0;
	/**
	 * Current loop direction.
	 */
	private LoopDirection currentLoopDirection = LoopDirection.forward;

	/**
	 * Creates a new animation
	 * @param name the animation name
	 * @param imagePositions the image positions composing the animation
	 * @param animationSpeed initial animation speed
	 * @param loopMethod the loop method
	 * @param flip the image flipping
	 */
	public AnimatedSpriteAnimation(CharacterAnimationName name, int[] imagePositions, int animationSpeed,
		LoopMethod loopMethod,
		ImageFlipping flip)
	{
		this(name.name(), imagePositions, animationSpeed, loopMethod, flip);
	}

	/**
	 * Creates a new animation
	 * @param name the animation name
	 * @param imagePositions the image positions composing the animation
	 * @param animationSpeed initial animation speed
	 * @param loopMethod the loop method
	 * @param flip the image flipping
	 */
	public AnimatedSpriteAnimation(String name, int[] imagePositions, int animationSpeed, LoopMethod loopMethod,
		ImageFlipping flip)
	{
		this.name = name;
		this.animationSpeed = animationSpeed;
		this.imagePositions = imagePositions;
		this.loopMethod = loopMethod;
		this.imageFlipping = flip;
		this.currentPositionIndex = 0;
	}

	public int getAnimationSpeed()
	{
		return animationSpeed;
	}

	public void setAnimationSpeed(int animationSpeed)
	{
		this.animationSpeed = animationSpeed;
	}

	public int[] getImagePositions()
	{
		return imagePositions;
	}

	public LoopMethod getLoopMethod()
	{
		return loopMethod;
	}

	public String getName()
	{
		return name;
	}

	public void init()
	{
		this.currentPositionIndex = 0;
	}

	public int getHorizontalDecal()
	{
		return horizontalDecal;
	}

	public void setHorizontalDecal(int horizontalDecal)
	{
		this.horizontalDecal = horizontalDecal;
	}

	public int getVerticalDecal()
	{
		return verticalDecal;
	}

	public void setVerticalDecal(int verticalDecal)
	{
		this.verticalDecal = verticalDecal;
	}

	/**
	 * @return the current position and automatically increment the position for the next step
	 */
	public int getCurrentPosition(boolean increment)
	{
		int position = imagePositions[currentPositionIndex];
		if (increment)
			if (currentPositionIndex == imagePositions.length - 1)
				if (loopMethod == LoopMethod.LOOP_BEGINNING)
					currentPositionIndex = 0;
				else if (loopMethod == LoopMethod.NO_LOOP) return position;
				else
				{
					currentLoopDirection = LoopDirection.backward;
					currentPositionIndex--;
				}
			else if (loopMethod == LoopMethod.LOOP_BEGINNING)
				currentPositionIndex++;
			else
			{
				if (currentPositionIndex == 0) currentLoopDirection = LoopDirection.forward;
				if (currentLoopDirection == LoopDirection.forward)
					currentPositionIndex++;
				else currentPositionIndex--;
			}
		return position;
	}

	public ImageFlipping getImageFlipping()
	{
		return imageFlipping;
	}
}
