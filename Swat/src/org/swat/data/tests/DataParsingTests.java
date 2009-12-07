package org.swat.data.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.swat.data.Coordinate;
import org.swat.data.DataParsing;
import org.swat.data.GAME_STATE;
import org.swat.data.GAME_TYPE;
import org.swat.data.GameInfo;
import org.swat.data.GameState;
import org.swat.data.LineReader;

public class DataParsingTests
{
	public static final String STRING_LIST = 
		"BEGIN_STRING_LIST\n" 
		+ "Test1\n"
		+ "Test2\n" 
		+ "Test3\n" 
		+ "END_STRING_LIST\n";

	public static final String STRING_INT_MAP = 
		"BEGIN_STRING_INT_MAP\n"
		+ "Key1:1\n" 
		+ "Key2:2\n" 
		+ "Key3:3\n"
		+ "END_STRING_INT_MAP\n";

	public static final String INT_STRING_MAP = 
		"BEGIN_INT_STRING_MAP\n"
		+ "1:Value1\n" 
		+ "2:Value2\n" 
		+ "3:Value3\n"
		+ "END_INT_STRING_MAP\n";

	public static final String INT_ARRAY = 
		"BEGIN_2D_INT_ARRAY\n"
		+ "width=2\n"
		+ "height=2\n"
		+ "1:2\n"
		+ "3:4\n"
		+ "END_2D_INT_ARRAY\n";

	public static final String COORDINATE = 
		"BEGIN_COORDINATE\n"
		+ "x=6\n"
		+ "y=3\n"
		+ "END_COORDINATE\n";

	public static final String COORDINATE_LIST =
		"BEGIN_COORDINATE_LIST\n"
		+ "BEGIN_COORDINATE\n"
		+ "x=1\n"
		+ "y=2\n"
		+ "END_COORDINATE\n"
		+ "BEGIN_COORDINATE\n"
		+ "x=3\n"
		+ "y=4\n"
		+ "END_COORDINATE\n"
		+ "BEGIN_COORDINATE\n"
		+ "x=5\n"
		+ "y=6\n"
		+ "END_COORDINATE\n"
		+ "END_COORDINATE_LIST\n";
	
	public static final String GAME_STATE1 =
		"BEGIN_GAME_STATE\n"
		+ "counter=7\n"
		+ "gameInstanceID=123\n"
		+ "BEGIN_2D_INT_ARRAY\n"
		+ "width=3\n"
		+ "height=3\n"
		+ "1:2:3\n"
		+ "4:5:6\n"
		+ "7:8:9\n"
		+ "END_2D_INT_ARRAY\n"
		+ "gameState=DRAWN\n"
		+ "turnOfPlayer=Player1\n"
		+ "winnerID=Player2\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Message1\n"
		+ "Message2\n"
		+ "Message3\n"
		+ "END_STRING_LIST\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Player1\n"
		+ "Player2\n"
		+ "Player3\n"
		+ "END_STRING_LIST\n"
		+ "END_GAME_STATE\n";
	
	public static final String GAME_STATE2 =
		"BEGIN_GAME_STATE\n"
		+ "counter=3\n"
		+ "gameInstanceID=999\n"
		+ "BEGIN_2D_INT_ARRAY\n"
		+ "width=3\n"
		+ "height=3\n"
		+ "12:13:14\n"
		+ "15:16:17\n"
		+ "18:19:20\n"
		+ "END_2D_INT_ARRAY\n"
		+ "gameState=CREATED\n"
		+ "turnOfPlayer=U2\n"
		+ "winnerID=U3\n"
		+ "BEGIN_STRING_LIST\n"
		+ "M1\n"
		+ "M2\n"
		+ "M3\n"
		+ "END_STRING_LIST\n"
		+ "BEGIN_STRING_LIST\n"
		+ "U1\n"
		+ "U2\n"
		+ "U3\n"
		+ "END_STRING_LIST\n"
		+ "END_GAME_STATE\n";
	
	public static final String GAME_STATE_LIST = 
		"BEGIN_GAME_STATE_LIST\n"
		+ "BEGIN_GAME_STATE\n"
		+ "counter=7\n"
		+ "gameInstanceID=123\n"
		+ "BEGIN_2D_INT_ARRAY\n"
		+ "width=3\n"
		+ "height=3\n"
		+ "1:2:3\n"
		+ "4:5:6\n"
		+ "7:8:9\n"
		+ "END_2D_INT_ARRAY\n"
		+ "gameState=DRAWN\n"
		+ "turnOfPlayer=Player1\n"
		+ "winnerID=Player2\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Message1\n"
		+ "Message2\n"
		+ "Message3\n"
		+ "END_STRING_LIST\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Player1\n"
		+ "Player2\n"
		+ "Player3\n"
		+ "END_STRING_LIST\n"
		+ "END_GAME_STATE\n"
		+ "BEGIN_GAME_STATE\n"
		+ "counter=3\n"
		+ "gameInstanceID=999\n"
		+ "BEGIN_2D_INT_ARRAY\n"
		+ "width=3\n"
		+ "height=3\n"
		+ "12:13:14\n"
		+ "15:16:17\n"
		+ "18:19:20\n"
		+ "END_2D_INT_ARRAY\n"
		+ "gameState=CREATED\n"
		+ "turnOfPlayer=U2\n"
		+ "winnerID=U3\n"
		+ "BEGIN_STRING_LIST\n"
		+ "M1\n"
		+ "M2\n"
		+ "M3\n"
		+ "END_STRING_LIST\n"
		+ "BEGIN_STRING_LIST\n"
		+ "U1\n"
		+ "U2\n"
		+ "U3\n"
		+ "END_STRING_LIST\n"
		+ "END_GAME_STATE\n"
		+ "END_GAME_STATE_LIST\n";
	
	private static final String GAME_INFO = 
		"BEGIN_GAME_INFO\n"
		+ "gameID=4\n"
		+ "gameName=Test Game\n"
		+ "gameType=MOVE_ADD\n"
		+ "numPlayersNeeded=2\n"
		+ "boardLength=3\n"
		+ "boardWidth=3\n"
		+ "BEGIN_GAME_STATE\n"
		+ "counter=7\n"
		+ "gameInstanceID=123\n"
		+ "BEGIN_2D_INT_ARRAY\n"
		+ "width=3\n"
		+ "height=3\n"
		+ "1:2:3\n"
		+ "4:5:6\n"
		+ "7:8:9\n"
		+ "END_2D_INT_ARRAY\n"
		+ "gameState=DRAWN\n"
		+ "turnOfPlayer=Player1\n"
		+ "winnerID=Player2\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Message1\n"
		+ "Message2\n"
		+ "Message3\n"
		+ "END_STRING_LIST\n"
		+ "BEGIN_STRING_LIST\n"
		+ "Player1\n"
		+ "Player2\n"
		+ "Player3\n"
		+ "END_STRING_LIST\n"
		+ "END_GAME_STATE\n"
		+ "BEGIN_INT_STRING_MAP\n"
		+ "1:Value1\n"
		+ "2:Value2\n"
		+ "3:Value3\n"
		+ "END_INT_STRING_MAP\n"
		+ "END_GAME_INFO\n";

	private Map<String, Integer> stringIntMap;
	private Map<Integer, String> intStringMap;
	private List<String> stringList;
	private Coordinate coordinate;
	private List<Coordinate> coordList;
	private int[][] intArray;
	private GameState state, state2;
	private GameInfo info;
	private List<GameState> stateList;

	// Create common test data
	@Before
	public void setUp()
	{
		stringIntMap = new TreeMap<String, Integer>();
		stringIntMap.put("Key1", 1);
		stringIntMap.put("Key2", 2);
		stringIntMap.put("Key3", 3);

		intStringMap = new TreeMap<Integer, String>();
		intStringMap.put(1, "Value1");
		intStringMap.put(2, "Value2");
		intStringMap.put(3, "Value3");

		stringList = new ArrayList<String>();
		stringList.add("Test1");
		stringList.add("Test2");
		stringList.add("Test3");
		
		coordList = new ArrayList<Coordinate>();
		coordList.add(new Coordinate(1, 2));
		coordList.add(new Coordinate(3, 4));
		coordList.add(new Coordinate(5, 6));

		intArray = new int[2][2];
		intArray[0][0] = 1;
		intArray[0][1] = 2;
		intArray[1][0] = 3;
		intArray[1][1] = 4;
		
		coordinate = new Coordinate(6, 3);

		state = new GameState();
		state.setCounter(7);
		state.setGameInstanceID(123);
		state
				.setPieceInfo(new int[][] { { 1, 2, 3 }, { 4, 5, 6 },
						{ 7, 8, 9 } });
		state.setGameState(GAME_STATE.DRAWN);
		state.setTurnOfPlayer("Player1");
		state.setWinnerID("Player2");
		List<String> messages = new ArrayList<String>();
		messages.add("Message1");
		messages.add("Message2");
		messages.add("Message3");
		state.setMessages(messages);
		List<String> players = new ArrayList<String>();
		players.add("Player1");
		players.add("Player2");
		players.add("Player3");
		state.setPlayers(players);

		state2 = new GameState();
		state2.setCounter(3);
		state2.setGameInstanceID(999);
		state2.setPieceInfo(new int[][] { { 12, 13, 14 }, { 15, 16, 17 },
				{ 18, 19, 20 } });
		state2.setGameState(GAME_STATE.CREATED);
		state2.setTurnOfPlayer("U2");
		state2.setWinnerID("U3");
		List<String> messages2 = new ArrayList<String>();
		messages2.add("M1");
		messages2.add("M2");
		messages2.add("M3");
		state2.setMessages(messages2);
		List<String> players2 = new ArrayList<String>();
		players2.add("U1");
		players2.add("U2");
		players2.add("U3");
		state2.setPlayers(players2);

		stateList = new ArrayList<GameState>();
		stateList.add(state);
		stateList.add(state2);

		info = new GameInfo();
		info.setGameID(4);
		info.setGameName("Test Game");
		info.setGameType(GAME_TYPE.MOVE_ADD);
		info.setNumPlayersNeeded(2);
		info.setBoardLength(3);
		info.setBoardWidth(3);
		info.setInitialState(state);
		info.setPieces(intStringMap);
	}

	private boolean equalsGameInfo(GameInfo first, GameInfo second)
	{
		// Compare each field
		if (first.getGameID() != second.getGameID())
			return false;
		if (!first.getGameName().equals(second.getGameName()))
			return false;
		if (first.getGameType() != second.getGameType())
			return false;
		if (first.getNumPlayersNeeded() != second.getNumPlayersNeeded())
			return false;
		if (first.getBoardLength() != second.getBoardLength())
			return false;
		if (first.getBoardWidth() != second.getBoardWidth())
			return false;
		if (!equalsGameState(first.getInitialState(), second.getInitialState()))
			return false;
		if (!first.getPieces().equals(second.getPieces()))
			return false;

		return true;
	}

	private boolean equalsGameState(GameState first, GameState second)
	{
		// Compare each field
		if (first.getCounter() != second.getCounter())
			return false;
		if (first.getGameInstanceID() != second.getGameInstanceID())
			return false;
		if (first.getPieceInfo().length != second.getPieceInfo().length)
			return false;
		for (int i = 0; i < first.getPieceInfo().length; i++)
		{
			if (!Arrays.equals(first.getPieceInfo()[i], second.getPieceInfo()[i]))
				return false;
		}
		if (first.getGameState() != second.getGameState())
			return false;
		if (!first.getTurnOfPlayer().equals(second.getTurnOfPlayer()))
			return false;
		if (!first.getWinnerID().equals(second.getWinnerID()))
			return false;
		if (!first.getMessages().equals(second.getMessages()))
			return false;
		if (!first.getPlayers().equals(second.getPlayers()))
			return false;
		
		return true;
	}

	@Test
	public void testReadGameState() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(GAME_STATE1));
		GameState gstate = DataParsing.readGameState(reader);
		reader.close();

		assertTrue(equalsGameState(state, gstate));
	}

	@Test
	public void testWriteGameState() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeGameState(pWriter, state);
		sWriter.close();
		pWriter.close();

		assertEquals(GAME_STATE1, sWriter.toString());
	}

	@Test
	public void testReadGameStateList() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(GAME_STATE_LIST));
		List<GameState> list = DataParsing.readGameStateList(reader);
		reader.close();

		assertEquals(stateList.size(), list.size());
		for (int i = 0; i < stateList.size(); i++)
			assertTrue(equalsGameState(stateList.get(i), list.get(i)));
	}

	@Test
	public void testWriteGameStateList() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeGameStateList(pWriter, stateList);
		sWriter.close();
		pWriter.close();

		assertEquals(GAME_STATE_LIST, sWriter.toString());
	}

	@Test
	public void testReadGameInfo() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(GAME_INFO));
		GameInfo ginfo = DataParsing.readGameInfo(reader);
		reader.close();

		assertTrue(equalsGameInfo(info, ginfo));
	}

	@Test
	public void testWriteGameInfo() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeGameInfo(pWriter, info);
		sWriter.close();
		pWriter.close();

		assertEquals(GAME_INFO, sWriter.toString());
	}

	@Test
	public void testReadCoordinate() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(COORDINATE));
		Coordinate coord = DataParsing.readCoordinate(reader);
		reader.close();

		assertEquals(coordinate, coord);
	}

	@Test
	public void testWriteCoordinate() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeCoordinate(pWriter, coordinate);
		sWriter.close();
		pWriter.close();

		assertEquals(COORDINATE, sWriter.toString());
	}
	
	@Test
	public void testReadCoordinateList() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(COORDINATE_LIST));
		List<Coordinate> list = DataParsing.readCoordinateList(reader);
		reader.close();

		assertEquals(coordList, list);
	}

	@Test
	public void testWriteCoordinateList() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeCoordinateList(pWriter, coordList);
		sWriter.close();
		pWriter.close();

		assertEquals(COORDINATE_LIST, sWriter.toString());
	}

	@Test
	public void testReadStringList() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(STRING_LIST));
		List<String> list = DataParsing.readStringList(reader);
		reader.close();

		assertEquals(stringList, list);
	}

	@Test
	public void testWriteStringList() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeStringList(pWriter, stringList);
		sWriter.close();
		pWriter.close();

		assertEquals(STRING_LIST, sWriter.toString());
	}

	@Test
	public void testReadStringIntMap() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(STRING_INT_MAP));
		Map<String, Integer> map = DataParsing.readStringIntMap(reader);
		reader.close();

		assertEquals(stringIntMap, map);
	}

	@Test
	public void testWriteStringIntMap() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeStringIntMap(pWriter, stringIntMap);
		sWriter.close();
		pWriter.close();

		assertEquals(STRING_INT_MAP, sWriter.toString());
	}

	@Test
	public void testReadIntStringMap() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(INT_STRING_MAP));
		Map<Integer, String> map = DataParsing.readIntStringMap(reader);
		reader.close();

		assertEquals(intStringMap, map);
	}

	@Test
	public void testWriteIntStringMap() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.writeIntStringMap(pWriter, intStringMap);
		sWriter.close();
		pWriter.close();

		assertEquals(INT_STRING_MAP, sWriter.toString());
	}

	@Test
	public void testRead2DIntArray() throws IOException
	{
		LineReader reader = new LineReader(new StringReader(INT_ARRAY));
		int[][] array = DataParsing.read2DIntArray(reader);
		reader.close();

		assertEquals(intArray[0][0], array[0][0]);
		assertEquals(intArray[0][1], array[0][1]);
		assertEquals(intArray[1][0], array[1][0]);
		assertEquals(intArray[1][1], array[1][1]);
	}

	@Test
	public void testWrite2DIntArray() throws IOException
	{
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		DataParsing.write2DIntArray(pWriter, intArray);
		sWriter.close();
		pWriter.close();

		assertEquals(INT_ARRAY, sWriter.toString());
	}

}
