package com.snakybo.sengine.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class FileUtils
{
	private FileUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a new file
	 * @param file - The path of the file to create
	 * @return The file
	 */
	public static Path create(Path file)
	{
		try
		{
			if(!Files.exists(file))
			{
				return Files.createFile(file);
			}
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileHandler");
		}
		
		return null;
	}
	
	/**
	 * Write the contents of a file
	 * @param file - The path of the file
	 * @param lines - The lines to write
	 */
	public static void write(Path file, Iterable<? extends CharSequence> lines)
	{
		try
		{
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileHandler");
		}
	}
	
	/**
	 * Load a file
	 * @param path - The path to the file
	 * @param extension - The file extension
	 * @return The path of the file
	 * @see #load(String, String, boolean)
	 */
	public static Path load(String path, String extension)
	{
		return load(path, extension, true);
	}
	
	/**
	 * Load a file
	 * @param path - The path to the file
	 * @param extension - The file extension
	 * @param allowCreate - Whether or not we should create the file if it doesn't exist
	 * @return The path of the file
	 */
	public static Path load(String path, String extension, boolean allowCreate)
	{
		Path result = parsePath(path, extension);
		
		if(!exists(result) && allowCreate)
		{
			return create(result);
		}
		
		return result;
	}
	
	/**
	 * Read the contents of a file
	 * @param file - The file to read
	 * @return The contents of the file
	 */
	public static List<String> read(Path file)
	{
		try
		{
			return Files.readAllLines(file);
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileHandler");
		}
		
		return null;
	}
	
	/**
	 * Check if a file exists
	 * @param file - The file to check
	 * @return Whether or not the file exists
	 */
	public static boolean exists(Path file)
	{
		return Files.exists(file) && !Files.isDirectory(file);
	}
	
	/**
	 * Parse a {@link Path}
	 * @param path - The path to the file
	 * @param extension - The file extension
	 * @return The path of the file
	 */
	public static Path parsePath(String path, String extension)
	{
		return Paths.get("./res/" + path + "." + extension);
	}
}
