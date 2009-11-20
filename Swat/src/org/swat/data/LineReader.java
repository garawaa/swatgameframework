package org.swat.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class LineReader
{
	private BufferedReader reader;
	private String currentLine = null;

	public LineReader(Reader reader)
	{
		this.reader = new BufferedReader(reader);
	}

	// Reads the next line from the stream
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentLine;
	}

	// Returns the current line
	public String getLine()
	{
		return currentLine;
	}

	// Closes the stream
	public void close() throws IOException
	{
		reader.close();
		reader = null;
		currentLine = null;
	}
}
