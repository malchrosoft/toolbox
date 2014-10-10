/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.game.event;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class CollisionEvent implements GameEvent
{
	public enum CollisionDirection
	{
		UP, DOWN, LEFT, RIGHT
	}

	private Point2D absoluteLocation;
	private List<CollisionDirection> directions;

	/**
	 * Creates a new CollisionEvent
	 * @param absoluteLocation the absolute location
	 * @param directions the directions
	 */
	public CollisionEvent(Point2D absoluteLocation, List<CollisionDirection> directions)
	{
		this.absoluteLocation = absoluteLocation;
		this.directions = directions;
	}

	/**
	 * Creates a new CollisionEvent
	 * @param absoluteLocation the absolute location
	 * @param directions the directions
	 */
	public CollisionEvent(Point2D absoluteLocation, CollisionDirection... directions)
	{
		this(absoluteLocation, new ArrayList<CollisionDirection>());
		for (final CollisionDirection dir : directions)
			this.directions.add(dir);
		
	}

	public Point2D getAbsoluteLocation()
	{
		return absoluteLocation;
	}

	public void setAbsoluteLocation(Point2D absoluteLocation)
	{
		this.absoluteLocation = absoluteLocation;
	}

	public List<CollisionDirection> getDirections()
	{
		return directions;
	}

	public void setDirections(List<CollisionDirection> directions)
	{
		this.directions = directions;
	}

	/**
	 * @param direction the direction to find
	 * @return true if the event contains the direction, false otherwise
	 */
	public boolean hasDisrection(CollisionDirection direction)
	{
		for (final CollisionDirection dir : this.getDirections())
			if (dir.name().equals(direction.name()))
				return true;
		return false;
	}
}
