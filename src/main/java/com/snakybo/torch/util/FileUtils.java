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

package com.snakybo.torch.util;

import com.snakybo.torch.debug.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * A collection of file utilities.
 * </p>
 *
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
	 * <p>
	 * Convert the path to a file to an {@link URI}.
	 * </p>
	 *
	 * @param path The path to the file.
	 * @return An {@code URI} representing the path.
	 * @throws NoSuchFileException Thrown if the specified {@code path} does not exist.
	 */
	public static URI toURI(String path) throws NoSuchFileException
	{
		try
		{
			URL resource = FileUtils.class.getResource("/" + path);
			if(resource != null)
			{
				return resource.toURI();
			}
		}
		catch(URISyntaxException e)
		{
			Logger.logError(e.toString(), e);
		}
		
		throw new NoSuchFileException("No file found at: " + path);
	}
	
	/**
	 * <p>
	 * Get the name and extension of a file.
	 * </p>
	 *
	 * @param uri The {@link URI} of the file.
	 * @return The file name and extension.
	 */
	public static String getName(URI uri)
	{
		return getName(Paths.get(uri));
	}
	
	/**
	 * <p>
	 * Get the name and extension of a file
	 * </p>
	 *
	 * @param path The path to the file.
	 * @return The file name and extension.
	 */
	public static String getName(String path)
	{
		return getName(Paths.get(path));
	}
	
	/**
	 * <p>
	 * Get the name of a file.
	 * </p>
	 *
	 * @param uri The {@link URI} of the file.
	 * @return The file name.
	 */
	public static String getSimpleName(URI uri)
	{
		return getSimpleName(Paths.get(uri));
	}
	
	/**
	 * <p>
	 * Get the name of a file.
	 * </p>
	 *
	 * @param path The path to the file.
	 * @return The file name.
	 */
	public static String getSimpleName(String path)
	{
		return getSimpleName(Paths.get(path));
	}
	
	/**
	 * <p>
	 * Get the extension of a file.
	 * </p>
	 *
	 * @param uri The {@link URI} of the file.
	 * @return The file extension.
	 */
	public static String getExtension(URI uri)
	{
		return getExtension(Paths.get(uri));
	}
	
	/**
	 * <p>
	 * Get the extension of a file.
	 * </p>
	 *
	 * @param path The path to the file.
	 * @return The file extension.
	 */
	public static String getExtension(String path)
	{
		return getExtension(Paths.get(path));
	}
	
	private static String getName(Path path)
	{
		Path fileName = path.getFileName();
		
		if(fileName != null)
		{
			return fileName.toString();
		}
		
		return null;
	}

	private static String getSimpleName(Path path)
	{
		String fileName = getName(path);
		
		if(fileName != null)
		{
			return fileName.substring(0, fileName.lastIndexOf('.'));
		}
		
		return null;
	}

	private static String getExtension(Path path)
	{
		String fileName = getName(path);
		
		if(fileName != null)
		{
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			return ext.toLowerCase();
		}
		
		return null;
	}
}
