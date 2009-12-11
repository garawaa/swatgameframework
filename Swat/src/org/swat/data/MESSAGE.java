package org.swat.data;

/**
 * The enumeration containing the list of error messages sent to the clients
 * 
 * ILLEGAL_GAME_STATE - sent when a request is made to operate on a game state that
 * 		is stale, invalid etc.
 * ILLEGAL_GAME_MOVE - same as above, but sent when an illegal game move request is
 * 		made.
 * ILLEGAL_GAME_JOIN - sent when an attempt is made to join an already full game or a non-
 * 		existent game.
 * ILLEGAL_GAME_SPEC - sent when a request is made to instantiate a game that is currently 
 * 		not deployed.
 * 
 * @author steve
 *
 */
public enum MESSAGE {
	
	ILLEGAL_GAME_STATE,
	ILLEGAL_GAME_MOVE,
	ILLEGAL_GAME_JOIN,
	ILLEGAL_GAME_SPEC;
}
