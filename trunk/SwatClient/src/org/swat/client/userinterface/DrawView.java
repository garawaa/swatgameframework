package org.swat.client.userinterface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class DrawView extends View {
	private static ColorBall leftCard; 
	private static ColorBall mainCard;

	private int picX;

	public DrawView(Context context, String img1, String img2, String img3) {
		super(context);
		setFocusable(true); //necessary for getting the touch events

		// setting the start point for the balls
		Point point1 = new Point();
		point1.x = -320;
		point1.y = 0;
		Point point2 = new Point();
		point2.x = 0;
		point2.y = 0;
		Point point3 = new Point();
		point3.x = 640;
		point3.y = 0;

		// declare each ball with the ColorBall class
		leftCard = new ColorBall(img1, point1);
		mainCard = new ColorBall(img2, point2);
	}
	
	public DrawView(Context context) {
		super(context);
		setFocusable(true); //necessary for getting the touch events
		leftCard = new ColorBall();
		mainCard = new ColorBall();
	}

	// the method that draws the balls
	@Override protected void onDraw(Canvas canvas) {
		//canvas.drawColor(0xFFCCCCCC);     //if you want another background color       

		try
		{
			//draw the cards on the canvas
			if(leftCard.getBitmap() != null)
				canvas.drawBitmap(leftCard.getBitmap(), leftCard.getX(), leftCard.getY(), null);
			if(mainCard.getBitmap() != null)
				canvas.drawBitmap(mainCard.getBitmap(), mainCard.getX(), mainCard.getY(), null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction(); 		

		switch (eventaction ) { 
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
			picX = (int)event.getX();
			leftCard.setX(-320);
			mainCard.setX(0);
			// redraw the canvas
			invalidate();
			break; 

		case MotionEvent.ACTION_MOVE:   // touch drag with the ball		
			int diffX = picX - (int)event.getX();
			if(diffX < 5 && diffX >-5)
			{
				if(diffX < 1 && diffX > -1)
					performClick();
				break;
			}
			int currX = mainCard.getX();
			leftCard.setX(currX - diffX - 320);
			mainCard.setX(currX - diffX);
			// redraw the canvas			
			invalidate();			
			break; 

		case MotionEvent.ACTION_UP: 
			// touch drop - just do things here after dropping
			leftCard.setX(-320);
			mainCard.setX(0);
			// redraw the canvas
			invalidate();			
			break; 
		} 		
		return true; 	
	}

	/**Animates next/previous card motion
	 * @param ctx context**/
	public Animation runAnimation(Activity ctx, int animDirection) {
		Animation animation = AnimationUtils.loadAnimation(ctx,
				animDirection);		
		animation.setDuration(1000);
		animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());		
		this.startAnimation(animation);			
		return animation;
	}

	/**Set images for the three cards**/
	public void setImages(String img1, String img2, String img3)
	{
		if(!img1.contentEquals(""))
			leftCard.setImage(img1, -320, 0);
		if(!img2.contentEquals(""))
			mainCard.setImage(img2, 0, 0);
		invalidate();
	}
}
