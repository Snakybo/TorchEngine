// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.sengine.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class File
{
	private File()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a new file
	 * @param file The path of the file to create
	 */
	public static void create(String file)
	{
		try
		{
			Files.createFile(getPath(file));
		}
		catch(FileAlreadyExistsException e)
		{
			Logger.logWarning("File: " + file + " already exists", "FileUtils");
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Create a new directory
	 * @param directory The path to the new directory
	 */
	public static void creatDirectory(String directory)
	{
		try
		{
			Files.createDirectory(getPath(directory));
		}
		catch(FileAlreadyExistsException e)
		{
			Logger.logWarning("Unable to create directory: " + directory + ", a file with that name already exists" , "FileUtils");
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Append bytes to a file
	 * @param file The path of the file
	 * @param bytes The bytes to append
	 */
	public static void appendBytes(String file, byte[] bytes)
	{
		write(file, bytes, StandardOpenOption.APPEND);
	}
	
	/**
	 * Append to a file
	 * @param file The path of the file
	 * @param lines The lines to append
	 */
	public static void appendLines(String file, Iterable<? extends CharSequence> lines)
	{
		write(file, lines, StandardOpenOption.APPEND);
	}
	
	/**
	 * Truncate a file, and write bytes to it
	 * @param file The path of the file
	 * @param bytes The bytes to write
	 */
	public static void writeBytes(String file, byte[] bytes)
	{
		write(file, bytes, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	/**
	 * Truncate a file, and write to it
	 * @param file The path of the file
	 * @param lines The lines to write
	 */
	public static void writeLines(String file, Iterable<? extends CharSequence> lines)
	{
		write(file, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	/**
	 * Read binary contents from a file
	 * @param file The file to read
	 * @return The binary contents of a file
	 */
	public static byte[] readBytes(String file)
	{
		try
		{
			return Files.readAllBytes(getPath(file));
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
		
		return null;
	}
	
	/**
	 * Read the contents of a file
	 * @param file The file to read
	 * @return The contents of the file
	 */
	public static List<String> readLines(String file)
	{
		try
		{
			return Files.readAllLines(getPath(file), StandardCharsets.UTF_8);
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
		
		return null;
	}
	
	/**
	 * Read the contents of a file, and merge them togheter in a single string
	 * @param file The file to read
	 * @return The contents of the file
	 */
	public static String readLinesMerge(String file)
	{
		List<String> lines = readLines(file);
		StringBuilder result = new StringBuilder();
		
		for(String line : lines)
		{
			result.append(line).append("\n");
		}
		
		return result.toString();
	}
	
	/**
	 * Retrieve all files in a directory, and it's sub-directories
	 * @param directory The directory to walk
	 * @return A list containing all found files
	 */
	public static List<String> listDirectory(String directory)
	{
		List<String> result = new ArrayList<String>();
		
		try
		{			
			Files.walk(getPath(directory)).forEach(filePath -> {
				result.add(filePath.toString());
			});
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
		
		return result;
	}
	
	/**
	 * Clear a file
	 * @param file The path of the file
	 */
	public static void truncate(String file)
	{
		writeLines(file, new ArrayList<String>());
	}
	
	/**
	 * Delete a file or directory.
	 * @param path The file or directory to delete
	 */
	public static void delete(String path)
	{
		if(!exists(path))
		{
			Logger.logError("Unable to delete a file or directory, it doesn't exist: " + path, "FileUtils");
			return;
		}
		
		try
		{
			if(!isDirectory(path))
			{
				Files.delete(getPath(path));
			}
			else
			{
				List<String> files = listDirectory(path);
				
				// Delete all found files
				for(String e : files)
				{
					if(!isDirectory(e))
					{
						Files.delete(getPath(e));
					}
				}
				
				// Reverse the list and delete all directories starting from max depth
				Collections.reverse(files);
				for(String e : files)
				{
					if(isDirectory(e))
					{
						Files.delete(getPath(e));
					}
				}
			}
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Move or rename a file to a target file
	 * @param from The file to move or rename
	 * @param to The target location
	 */
	public static void move(String from, String to)
	{
		try
		{
			Files.move(getPath(from), getPath(to));
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Copy a file to the target file
	 * @param from The file to copy
	 * @param to The path to copy it to
	 */
	public static void copy(String from, String to)
	{
		try
		{
			Files.copy(getPath(from), getPath(to));
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Check if a file exists
	 * @param file The file to check
	 * @return Whether or not the file exists
	 */
	public static boolean exists(String file)
	{
		return Files.exists(getPath(file));
	}
	
	/**
	 * Check if a file is a directory
	 * @param file The file to check
	 * @return Whether or not the file is a directory
	 */
	public static boolean isDirectory(String file)
	{
		return Files.isDirectory(getPath(file));
	}
	
	/**
	 * Parse a {@link Path}
	 * @param file The path to the file
	 * @return The path of the file
	 */
	private static Path getPath(String file)
	{
		return Paths.get(file);
	}
	
	/**
	 * Write binary contents to file
	 * @param file The target file
	 * @param bytes The bytes to write
	 * @param openOptions Optional open options
	 */
	private static void write(String file, byte[] bytes, OpenOption... openOptions)
	{
		if(!exists(file) && !isDirectory(file))
		{
			Logger.logError("Unable to write to file \"" + file + "\". Not found", "FileUtils");
			return;
		}
		
		try
		{
			Files.write(getPath(file), bytes, openOptions);
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
	
	/**
	 * Write a sequence of lines to a file
	 * @param file The target file
	 * @param lines The lines to write
	 * @param openOptions Optional open options
	 */
	private static void write(String file, Iterable<? extends CharSequence> lines, OpenOption... openOptions)
	{
		if(!exists(file) && !isDirectory(file))
		{
			Logger.logError("Unable to write to file \"" + file + "\". Not found", "FileUtils");
			return;
		}
		
		try
		{
			Files.write(getPath(file), lines, StandardCharsets.UTF_8, openOptions);
		}
		catch(IOException e)
		{
			Logger.logException(e, "FileUtils");
		}
	}
}
