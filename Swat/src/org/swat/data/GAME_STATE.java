package org.swat.data;

/**
 * The enumeration containing the different states a game can be in.
 * 
 * created - soon after a player creates a new instance of a game
 * started - a created game transitions to started when sufficient players have joined the game
 * finished - when there is a winner in the game
 * drawn - stalemate :(
 * 
 * @author steve
 *
 */
public enum GAME_STATE {
	
	CREATED, STARTED, FINISHED, DRAWN;

}
