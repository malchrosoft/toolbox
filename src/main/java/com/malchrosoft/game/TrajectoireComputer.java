/**
 *
 */
package com.malchrosoft.game;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * @author amalchrowicz
 *
 */
public class TrajectoireComputer
{
	private static Rectangle obstacle1 = new Rectangle(220, 120, 60, 50);
	private static Rectangle obstacle2 = new Rectangle(80, 200, 100, 20);
	private static Rectangle obstacle3 = new Rectangle(100, 100, 60, 60);
	private static Rectangle obstacle4 = new Rectangle(500, 200, 100, 100);

	private static void traceTrajetSimple(Point from, Point to, Graphics2D g)
	{
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		Point current = new Point(from);
		while (current.distance(to) > 8)
		{
			if (obstacle1.contains(current))
				g.setColor(Color.red);
			else
				g.setColor(Color.blue);
			if (current.x < to.x)
				current.x += 4;
			else if (current.x > to.x)
				current.x -= 4;
			if (current.y < to.y)
				current.y += 4;
			else if (current.y > to.y)
				current.y -= 4;
			if (Math.abs(current.x - to.x) <= 4)
				current.x = to.x;
			if (Math.abs(current.y - to.y) <= 4)
				current.y = to.y;
			g.fillOval(current.x, current.y, 1, 1);
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException ex)
			{
				Logger.getLogger(TrajectoireComputer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void traceTrajet(List<Point> trajet, Graphics2D g, Color c)
	{
		traceTrajet(trajet, g, c, false);
	}

	private static void traceTrajet(List<Point> trajet, Graphics2D g, Color c, boolean withPauses)
	{
		if (trajet == null || trajet.size() < 1)
			return;
		Point pP = new Point(-1, -1);
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for (Point p : trajet)
		{
			if (withPauses)
			{
				try
				{
					Thread.sleep(150);
				} catch (InterruptedException ex)
				{
					Logger.getLogger(TrajectoireComputer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			if (pP.x > -1 && pP.y > -1)
			{
				g.setColor(c);
				g.drawLine(pP.x, pP.y, p.x, p.y);
				traceTrajetSimple(pP, p, g);
			}
			g.setColor(c);
			g.drawOval(p.x - 3, p.y - 3, 6, 6);
			pP.x = p.x;
			pP.y = p.y;
		}
		g.setColor(Color.green);
		g.drawOval(trajet.get(0).x - 1, trajet.get(0).y - 1, 3, 3);
		g.setColor(Color.red);
		g.drawOval(trajet.get(trajet.size() - 1).x - 1, trajet.get(trajet.size() - 1).y - 1, 3, 3);
	}

	/**
	 * Compute the optimized travel from two points
	 *
	 * @param from
	 * @param to
	 * @return the point list as travel
	 */
	public static List<Point> computeTrajetOmptimise(Point from, Point to, List<Rectangle> obstables)
	{
		List<Point> trajet = new ArrayList<Point>();
		trajet.add(from);
		Point current = new Point(from);
		Point nextPoint = new Point(current);
		int stepX = (int) ((to.x - from.x) * 0.2d);
		int stepY = (int) ((to.y - from.y) * 0.2d);
		if (from.distance(to) < 30)
		{
			trajet.add(to);
		}
		int currentStep = 0;
		while (current.distance(to) > 8 && currentStep < 20)
		{
			nextPoint.x = current.x + stepX;
			nextPoint.y = current.y + stepY;
			if (current.distance(nextPoint) > 100)
			{
				trajet.addAll(computeTrajetOmptimise(current, nextPoint, obstables));
			}
			else
			{
				// trajet.addAll(computeCollision(current, nextPoint));
				trajet.add(new Point(nextPoint));
			}
			current = new Point(nextPoint);
			currentStep++;
		}
		return trajet;
	}

	public static Point closestPoint(Point toBeClose, Point... toCheck)
	{
		if (toCheck == null || toBeClose == null)
			return null;
		Point closest = toCheck[0];
		for (Point p : toCheck)
		{
			if (closest == null || closest.distance(toBeClose) > p.distance(toBeClose))
			{
				closest = p;
			}
		}
		return closest;
	}

	/**
	 * Losque l'on sait qu'on doit contourner un objet
	 *
	 * @param from
	 * @param destination
	 * @param r
	 * @return
	 */
	public static List<Point> bestPoints(Point from, Point destination, Rectangle r)
	{
		List<Point> bestPt = new ArrayList<Point>();
		Point tmp;
		if (from == null || destination == null)
			return null;
		// traiter que les cas depart au sud sinon inverser le trajet pour les
		// calculs
		if (from.y > r.getMinY())
		{
			bestPt.add(from);
			if (from.x < r.getMinX())
			{
				if (from.y > r.getMaxY())
				{
					if (destination.x > r.getMaxX() && destination.y < r.getMinY())
					{
						// prendre le chemin le plus court
						if (from.distance(new Point2D.Double(r.getMaxX(), r.getMaxY())) +
							destination.distance(new Point2D.Double(r.getMaxX(), r.getMaxY())) <
							from.distance(new Point2D.Double(r.getMinX(), r.getMinY())) +
							destination.distance(new Point2D.Double(r.getMinX(), r.getMinY())))
							bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMaxY() + 1));
						else
							bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMinY() - 1));
					}
					else if (destination.y < r.getMinY())
						bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMinY() - 1));
					else
						bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMaxY() + 1));
				}
				else if (from.y >= r.getMinY() && from.y <= r.getMaxY() && destination.y >= r.getMinY() && destination.y <=
					r.getMaxY())
				{
					tmp = closestPoint(from, new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1),
						new Point((int) r.getMinX() - 1, (int) r.getMinY() - 1));
					bestPt.add(new Point(tmp));
					bestPt.add(new Point((int) r.getMaxX() + 1, tmp.y));
				}
				else
				{
					if (destination.x > r.getMinX() && destination.y < r.getMinY())
					{
						bestPt.add(new Point(r.getLocation().x - 1, r.getLocation().y - 1));
					}
					else
						bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1));
				}
			}
			else if (from.x >= r.getMinX() && from.x <= r.getMaxX())
			{
				if (destination.x > r.getMaxX())
				{
					bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMaxY() + 1));
				}
				else if (destination.x < r.getMinX())
				{
					bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1));
				}
				else
				{
					tmp = closestPoint(from, new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1),
						new Point((int) r.getMaxX() + 1, (int) r.getMaxY() + 1));
					bestPt.add(new Point(tmp));
					bestPt.add(new Point(tmp.x, (int) r.getMinY() - 1));
				}
			}
			else
			{
				if (from.y > r.getMaxY())
				{
					if (destination.x < r.getMinX() && destination.y < r.getMinY())
					{
						// prendre le chemin le plus court
						if (from.distance(new Point2D.Double(r.getMaxX(), r.getMinY())) +
							destination.distance(new Point2D.Double(r.getMaxX(), r.getMinY())) <
							from.distance(new Point2D.Double(r.getMinX(), r.getMaxY())) +
							destination.distance(new Point2D.Double(r.getMinX(), r.getMaxY())))
							bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMinY() - 1));
						else
							bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1));
					}
					else if (destination.y < r.getMinY())
						bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMinY() - 1));
					else
						bestPt.add(new Point((int) r.getMinX() - 1, (int) r.getMaxY() + 1));
				}
				else if (from.y >= r.getMinY() && from.y <= r.getMaxY())
				{
					if (destination.x < r.getMaxX() && destination.y < r.getMinY())
						bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMinY() - 1));
					else
						bestPt.add(new Point((int) r.getMaxX() + 1, (int) r.getMaxY() + 1));
				}
			}
			bestPt.add(destination);
		}
		else
		{
			// reverse it and retry and rereverse it to return
			List<Point> reverse = bestPoints(destination, from, r);
			for (int i = reverse.size() - 1; i >= 0; i--)
				bestPt.add(reverse.get(i));
		}
		return bestPt;
	}

	/**
	 * Compute the travel according to the obstacle
	 *
	 * @param trajet
	 * @param obstables
	 * @return
	 */
	public static List<Point> computeTrajet(List<Point> trajet, List<Rectangle> obstables)
	{
		List<Point> trajetResultant = new ArrayList<Point>();
		List<Point> tmp;
		List<Point> currentLine;
		boolean intersected = false;
		for (Rectangle r : obstables)
		{
			// traiter cas un des points est dans un obstable donc deplacer le
			// point contre
			if (r.contains(trajet.get(trajet.size() - 1)))
			{
				trajet.set(trajet.size() - 1, collisionPoint(trajet.get(0), trajet.get(trajet.size() - 1), r));
			}
			if (r.contains(trajet.get(0)))
			{
				trajet.set(0, collisionPoint(trajet.get(trajet.size() - 1), trajet.get(0), r));
			}
			if (r.intersectsLine(new Line2D.Double(trajet.get(0), trajet.get(trajet.size() - 1))))
			{
				trajetResultant = bestPoints(trajet.get(0), trajet.get(trajet.size() - 1), r);
				tmp = new ArrayList<Point>();
				for (int i = 1; i < trajetResultant.size(); i++)
				{
					currentLine = new ArrayList<Point>();
					currentLine.add(trajetResultant.get(i - 1));
					currentLine.add(trajetResultant.get(i));
					tmp.addAll(computeTrajet(currentLine, obstables));
				}
				trajetResultant = tmp;
				intersected = true;
			}
		}
		if (!intersected)
		{
			trajetResultant.add(trajet.get(0));
			trajetResultant.add(trajet.get(trajet.size() - 1));
		}
		return trajetResultant;
	}

	/**
	 * @param from
	 * @param to
	 * @param r
	 * @return
	 */
	private static Point collisionPoint(Point from, Point to, Rectangle r)
	{
		Point result = new Point(to);
		int ratioY = Math.abs((to.x - from.x) / (to.y - from.y));
		double signumX = Math.signum(to.getX() - from.getX());
		double signumY = Math.signum(to.getY() - from.getY());
		if (r.contains(to) && !r.contains(from))
		{
			while (r.contains(result))
			{
				result.x -= (4 * signumX);
				result.y -= (4 * signumY * ratioY);
			}
		}
		return result;
	}

	public static void main(String[] args)
	{
		JFrame f = new JFrame("Test trajectoires computer");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final List<Rectangle> obs = new ArrayList<Rectangle>();
		obs.add(obstacle1);
		obs.add(obstacle2);
		obs.add(obstacle3);
		obs.add(obstacle4);
		Canvas c = new Canvas()
		{
			@Override
			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D g2D = (Graphics2D) g;
				g2D.setColor(Color.white);
				g.fillRect(0, 0, 800, 600);
				List<Point> pts = new ArrayList<Point>();
				pts.add(new Point(10, 10));
				pts.add(new Point(50, 100));
				pts.add(new Point(200, 300));
				pts.add(new Point(150, 150));
				pts.add(new Point(320, 20));
				pts.add(new Point(200, 150));
				pts.add(new Point(350, 200));

				pts.add(new Point(790, 590));

				pts.add(new Point(555, 100)); //8
				pts.add(new Point(400, 120));
				pts.add(new Point(380, 240));
				pts.add(new Point(420, 430));
				pts.add(new Point(570, 320));
				pts.add(new Point(700, 500));
				pts.add(new Point(650, 250));
				pts.add(new Point(690, 50));

				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2D.setColor(Color.gray);
				for (Rectangle ob : obs)
					g2D.fill(ob);
				g2D.drawLine(pts.get(1).x, pts.get(1).y, pts.get(2).x, pts.get(2).y);
				g2D.drawLine(pts.get(1).x - 5, pts.get(1).y, pts.get(1).x + 5, pts.get(1).y);
				g2D.drawLine(pts.get(1).x, pts.get(1).y + 5, pts.get(1).x, pts.get(1).y - 5);
				g2D.drawLine(pts.get(2).x - 5, pts.get(2).y, pts.get(2).x + 5, pts.get(2).y);
				g2D.drawLine(pts.get(2).x, pts.get(2).y + 5, pts.get(2).x, pts.get(2).y - 5);
				g2D.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2D.setColor(Color.BLUE);
				traceTrajetSimple(pts.get(1), pts.get(3), g2D);
				// traceTrajet(computeTrajetOmptimise(p1, p2, obs), g2D,
				// Color.magenta);
				// traceTrajet(computeTrajetOmptimise(p2, p3, obs), g2D,
				// Color.magenta);
				// traceTrajet(computeTrajetOmptimise(p3, p4, obs), g2D,
				// Color.magenta);
				// traceTrajet(computeTrajetOmptimise(p4, p2, obs), g2D,
				// Color.orange);
				List<List<Point>> trajets = new ArrayList<List<Point>>();
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(2), pts.get(4), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(1), pts.get(2), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(2), pts.get(3), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(1), pts.get(3), obs), obs));
//				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(1), pts.get(5), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(1), pts.get(6), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(5), pts.get(4), obs), obs));
				trajets.add(computeTrajet(computeTrajetOmptimise(pts.get(5), pts.get(6), obs), obs));
				for (List<Point> trajet : trajets)
					for (int i = 1; i < trajet.size(); i++)
					{
						traceTrajet(computeTrajetOmptimise(trajet.get(i - 1), trajet.get(i), obs), g2D, Color.green, true);
						g2D.setColor(Color.black);
						g2D.drawString("pi", trajet.get(i - 1).x, trajet.get(i - 1).y);
					}

				g2D.setColor(Color.black);
				int i = 0;
				for (Point pc : pts)
				{
					g2D.drawString("P" + i++, pc.x, pc.y);
					g2D.drawOval(pc.x, pc.y, 1, 1);
				}
				List<List<Point>> ts2 = new ArrayList<List<Point>>();
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(9), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(10), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(11), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(12), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(13), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(14), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(8), pts.get(15), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(8), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(10), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(11), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(12), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(13), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(14), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(9), pts.get(15), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(8), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(9), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(11), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(12), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(13), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(14), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(10), pts.get(15), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(8), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(9), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(10), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(12), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(13), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(14), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(11), pts.get(15), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(8), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(9), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(10), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(11), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(13), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(14), obs), obs));
				ts2.add(computeTrajet(computeTrajetOmptimise(pts.get(12), pts.get(15), obs), obs));

				int k = 0;
				int l = 0;
				Color c = Color.green;
				for (List<Point> trajet : ts2)
				{
					for (int j = 1; j < trajet.size(); j++)
					{
						switch (k)
						{
							case 1:
								c = Color.magenta;
								break;
							case 2:
								c = Color.orange;
								break;
							case 3:
								c = Color.blue;
								break;
							case 4:
								c = Color.red;
								break;
							case 5:
								c = Color.PINK;
								break;
						}
						traceTrajet(computeTrajetOmptimise(trajet.get(j - 1), trajet.get(j), obs), g2D, c, true);
						g2D.drawString("pt intersec", trajet.get(j - 1).x, trajet.get(j - 1).y);

						try
						{
							Thread.sleep(500);
						} catch (InterruptedException ex)
						{
							Logger.getLogger(TrajectoireComputer.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					l++;
					if (l > 5)
					{
						k++;
						l = 0;
					}
				}
			}

		};

		f.getContentPane().setSize(new Dimension(800, 600));
		f.setSize(800, 600);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(c, BorderLayout.CENTER);

		f.setVisible(true);
	}

}
