package org.swat.client.userinterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PieceImage  {
	private Context context;

	private Bitmap img; // the image of the ball

	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas

	/**
	 * @param c Context of the Activity
	 */
	public PieceImage(Context c)
	{
		context = c;
	}

	/**
	 * @param newValue
	 */
	void setX(int newValue) {
		coordX = newValue;
	}

	/**
	 * @return
	 */
	public int getX() {
		return coordX;
	}

	/**
	 * @param newValue
	 */
	void setY(int newValue) {
		coordY = newValue;
	}

	/**
	 * @return
	 */
	public int getY() {
		return coordY;
	}	

	/**
	 * @return
	 */
	public Bitmap getBitmap() {
		return img;
	}

	/** Sets the image for itself
	 * @param p Image resource
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param width Width of image
	 * @param height Height of image
	 */
	public void setImage(int p, int x, int y, int width, int height){
		try
		{			
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.outHeight = height;
			opts.outWidth = width;
			img = BitmapFactory.decodeResource(context.getResources(), p);
			coordX = x;
			coordY = y;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}	
}
