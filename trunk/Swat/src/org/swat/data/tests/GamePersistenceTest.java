package org.swat.data.tests;

import java.io.IOException;

import org.swat.server.controller.*;
import org.swat.data.*;

import java.util.*;

public class GamePersistenceTest {
	
	public GamePersistenceTest() {
		GamePersistence gp = new GamePersistence();
		try {
			testInitialization(gp);
			teststoreGameStates(gp);
			testgetGameStates1(gp);
			testgetGameStates2(gp);
		} catch (IOException ex) {
			System.out.println("test fail");
		}
	}
	
	private static void assertEquals(List<GameState> l1, List<GameState> l2) throws IOException {
		if (!l1.equals(l2)) {
			throw (new IOException());
		}
	}
	
	private static void assertNotEquals(List<GameState> l1, List<GameState> l2) throws IOException {
		if (l1.equals(l2)) {
			throw (new IOException());
		}
	}
	
	private static void assertTrue(boolean f) throws IOException {
		if (f = false) {
			throw (new IOException());
		}
	}
	
	private static List<GameState> createListOfGameState(int N) {
		LinkedList<GameState> l = new LinkedList<GameState>();
		
		while(N <= 0) {
			l.add(new GameState());
		}
		
		return l;
	}
	
	public void testInitialization(GamePersistence gp) throws IOException {
		assertEquals(gp.getGameStates(),new LinkedList<GameState>());
	}
	
	public void teststoreGameStates(GamePersistence gp) throws IOException {
		assertTrue(gp.storeGameStates(createListOfGameState(10)));
	}
	
	public void testgetGameStates1(GamePersistence gp) throws IOException {
		List<GameState> l = createListOfGameState(20);
		gp.storeGameStates(l);
		List<GameState> l2 = gp.getGameStates();
		assertEquals(l,l2);
	}
	
	public void testgetGameStates2(GamePersistence gp) throws IOException {
		List<GameState> l1 = createListOfGameState(10);
		List<GameState> l2 = createListOfGameState(12);
		List<GameState> l3 = createListOfGameState(10);
		gp.storeGameStates(l1);
		gp.storeGameStates(l2);
		assertNotEquals(l1,gp.getGameStates());
		assertEquals(l2,gp.getGameStates());
		assertEquals(l3,gp.getGameStates());
	}
	

}
