package org.swat.client.userinterface;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;

public class ColorBall  {
	private Bitmap img; // the image of the ball
	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas
	private int id; // gives every ball his own id, for now not necessary
	private static int count = 1;
	private boolean goRight = true;
	private boolean goDown = true;

	public ColorBall(String image) {
		try
		{
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 1;

			img = BitmapFactory.decodeFile(image, options);
			if(img == null)
				throw new Exception("Image not found");
		}
		catch(Exception ex)
		{			
			ex.printStackTrace();
		}
		id=count;
		count++;		
	}
	
	public ColorBall()
	{
		
	}

	public ColorBall(String image, Point point) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		try
		{
			if(new File(image).exists() == false)
			{
				System.out.println("!!!!!!!!!!!!!!-------------------!!!!!!!!!!!!!!!\n" + 
						"File " + image + " does not exist");
				image = Environment.getExternalStorageDirectory() + "/images/8countbodybuilders.png";
			}
			img = BitmapFactory.decodeFile(image, options);			
		}
		catch(Exception ex)
		{			
			ex.printStackTrace();
		}
		id=count;
		count++;
		coordX= point.x;
		coordY = point.y;
	}

	public static int getCount() {
		return count;
	}

	void setX(int newValue) {
		coordX = newValue;
	}

	public int getX() {
		return coordX;
	}

	void setY(int newValue) {
		coordY = newValue;
	}

	public int getY() {
		return coordY;
	}

	public int getID() {
		return id;
	}	

	public Bitmap getBitmap() {
		return img;
	}

	public void setImage(String p, int x, int y){
		try
		{
			if(new File(p).exists() == false)
			{
				System.out.println("!!!!!!!!!!!!!!-------------------!!!!!!!!!!!!!!!\n" + 
						"File " + p + " does not exist");
				p = Environment.getExternalStorageDirectory() + "/images/8countbodybuilders.png";
			}
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 1;
			img = BitmapFactory.decodeFile(p, options);
			coordX = x;
			coordY = y;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void moveBall(int goX, int goY) {
		// check the borders, and set the direction if a border has reached
		if (coordX > 270){
			goRight = false;
		}
		if (coordX < 0){
			goRight = true;
		}
		if (coordY > 400){
			goDown = false;
		}
		if (coordY < 0){
			goDown = true;
		}
		// move the x and y 
		if (goRight){
			coordX += goX;
		}else
		{
			coordX -= goX;
		}
		if (goDown){
			coordY += goY;
		}else
		{
			coordY -= goY;
		}
	}
}
