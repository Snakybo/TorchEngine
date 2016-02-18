package com.snakybo.sengine.io;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class FileUtils
{
	private FileUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Get the file extension from a file
	 * @param file - The file
	 * @return The file extension, if there was an error, it will return an empty string
	 */
	public static String getFileExtension(String file)
	{
		if(File.exists(file) && !File.isDirectory(file))
		{
			Path path = Paths.get(file);
			String fileName = path.getFileName().toString();
			
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		
		return "";
	}
}
