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

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.snakybo.torch.debug.Logger;

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
	 * Get the name and extension of a file
	 * @param uri The path to the file.
	 * @return The file name and extension.
	 */
	public static String getName(URI uri)
	{
		return getName(Paths.get(uri));
	}
	
	/**
	 * Get the name and extension of a file
	 * @param path The path to the file.
	 * @return The file name and extension.
	 */
	public static String getName(String path)
	{
		return getName(Paths.get(path));
	}
	
	/**
	 * Get the name of a file
	 * @param uri The path to the file.
	 * @return The file name.
	 */
	public static String getSimpleName(URI uri)
	{
		return getSimpleName(Paths.get(uri));
	}
	
	/**
	 * Get the name of a file
	 * @param path The path to the file.
	 * @return The file name.
	 */
	public static String getSimpleName(String path)
	{
		return getSimpleName(Paths.get(path));
	}
	
	/**
	 * Get the extension of a file
	 * @param uri The path to the file.
	 * @return The file extension.
	 */
	public static String getExtension(URI uri)
	{
		return getExtension(Paths.get(uri));
	}
	
	/**
	 * Get the extension of a file
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
		
		Logger.logError(path.toString());
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
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		
		return null;
	}
}
