package org.swat.data;

/**
 * Stores an x and y coordinate
 * 
 * @author tombuzbee
 * 
 */
public class Coordinate
{
	private int x, y;

	/**
	 * Constructs a Coordinate object given coordinates
	 * 
	 * @param x
	 *            The x coordinate to use
	 * @param y
	 *            The y coordinate to use
	 */
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * @return
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "Coordinate [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
