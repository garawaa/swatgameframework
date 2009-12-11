package org.swat.desktopclient;

/**
 * Implementors will be updated if registered with the controller whenever the
 * GameState changes
 * 
 * @author tombuzbee
 * 
 */
public interface StateListener
{
	/**
	 * Callback method for notifying of updates
	 */
	public void updateState();
}
