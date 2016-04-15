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

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.snakybo.sengine.debug.Logger;

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
	 * Get the file name and extension from a file
	 * @param file - The file
	 * @return The file extension
	 */
	public static String getFileNameAndExtension(String file)
	{
		if(File.exists(file) && !File.isDirectory(file))
		{
			Path path = Paths.get(file);
			return path.getFileName().toString();
		}
		
		Logger.logException(new FileNotFoundException(file), "FileUtils");
		return "";
	}
	
	/**
	 * Get the file name from a file
	 * @param file - The file
	 * @return The file extension, if there was an error, it will return an empty string
	 */
	public static String getFileName(String file)
	{
		String fileName = getFileNameAndExtension(file);
		
		if(fileName.length() > 0)
		{
			return fileName.substring(0, fileName.lastIndexOf('.'));
		}
		
		return "";
	}
	
	/**
	 * Get the file extension from a file
	 * @param file - The file
	 * @return The file extension, if there was an error, it will return an empty string
	 */
	public static String getFileExtension(String file)
	{
		String fileName = getFileNameAndExtension(file);
		
		if(fileName.length() > 0)
		{
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		
		return "";
	}
}
