package org.swat.data.tests;

import java.io.IOException;

import org.swat.server.controller.*;
import org.swat.data.*;

import java.util.*;

public class ServerControllerTest {
	
	public ServerControllerTest () {
		ServerController.getInstance();
		GamePersistence gp = new GamePersistence();
		try {
			testGamePersistenceTask(gp);
		} catch (IOException ex) {
			System.out.println("test fail");
		}
	
	}
	
	private static void assertEquals(List<GameState> l1, List<GameState> l2) throws IOException {
		if (!l1.equals(l2)) {
			throw (new IOException());
		}
	}
	
	public void testGamePersistenceTask(GamePersistence gp) throws IOException {
		assertEquals(gp.getGameStates(), new LinkedList<GameState>());
	}
	

}
