package org.swat.data.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.swat.data.Coordinate;
import org.swat.data.DataParsing;
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

	private Map<String, Integer> stringIntMap;
	private Map<Integer, String> intStringMap;
	private List<String> stringList;
	private Coordinate coordinate;
	private List<Coordinate> coordList;
	private int[][] intArray;

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
	}

	@Test
	public void testReadGameState()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testWriteGameState()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadGameStateList() throws IOException
	{
		// LineReader reader = new LineReader(new
		// StringReader(GAME_STATE_LIST));
		// List<GameState> list = DataParsing.readCoordinateList(reader);
		// reader.close();
		//
		// assertEquals(stateList, list);
		fail("Not yet implemented");
	}

	@Test
	public void testWriteGameStateList() throws IOException
	{
		// StringWriter sWriter = new StringWriter();
		// PrintWriter pWriter = new PrintWriter(sWriter);
		// DataParsing.writeCoordinateList(pWriter, stateList);
		// sWriter.close();
		// pWriter.close();
		//
		// assertEquals(GAME_STATE_LIST, sWriter.toString());
		fail("Not yet implemented");
	}

	@Test
	public void testReadGameInfo()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testWriteGameInfo()
	{
		fail("Not yet implemented");
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
