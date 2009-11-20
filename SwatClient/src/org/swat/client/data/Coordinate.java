package org.swat.client.data;

public class Coordinate
{
	int x, y;

	public Coordinate(int currX, int currY)
	{
		this.x = currX;
		this.y = currY;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
