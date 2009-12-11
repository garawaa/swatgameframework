package org.swat.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Provides a stateful line-by-line reader. It stores the most recently read
 * line until it is told to advance.
 * 
 * @author tombuzbee
 * 
 */
public class LineReader
{
	private BufferedReader reader;
	private String currentLine = null;

	/**
	 * Constructs a LineReader around an existing Reader
	 * 
	 * @param reader
	 *            The reader to wrap
	 */
	public LineReader(Reader reader)
	{
		this.reader = new BufferedReader(reader);
	}

	/**
	 * Reads the next line from the stream, discarding the old
	 * 
	 * @return The String just read, or null if the end of the stream has been
	 *         reached
	 */
	public String advance()
	{
		try
		{
			if (reader != null)
			{
				currentLine = reader.readLine();
			}
		}
		catch (IOException e)
		{
			currentLine = null;
			e.printStackTrace();
		}
		return currentLine;
	}

	/**
	 * @return The most recently read line
	 */
	public String getLine()
	{
		return currentLine;
	}

	/**
	 * Closes the stored Reader and nulls the stored line
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		reader.close();
		reader = null;
		currentLine = null;
	}
}
