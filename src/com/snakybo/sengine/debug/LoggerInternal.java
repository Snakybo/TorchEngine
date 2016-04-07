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

package com.snakybo.sengine.debug;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class LoggerInternal
{
	private static final boolean debug = Boolean.parseBoolean(System.getenv("DEBUG"));
	
	private LoggerInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Debug version of {@link Logger#log(boolean, Object)}, for internal engine use only
	 * @param b - The boolean to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(boolean b, Object source)
	{
		if(debug)
		{
			Logger.log(b, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(char, Object)}, for internal engine use only
	 * @param c - The char to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(char c, Object source)
	{
		if(debug)
		{
			Logger.log(c, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(int, Object)}, for internal engine use only
	 * @param i - The int to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(int i, Object source)
	{
		if(debug)
		{
			Logger.log(i, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(float, Object)}, for internal engine use only
	 * @param f - The float to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(float f, Object source)
	{
		if(debug)
		{
			Logger.log(f, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(double, Object)}, for internal engine use only
	 * @param d - The double to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(double d, Object source)
	{
		if(debug)
		{
			Logger.log(d, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(long, Object)}, for internal engine use only
	 * @param l - The long to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(long l, Object source)
	{
		if(debug)
		{
			Logger.log(l, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(char[], Object)}, for internal engine use only
	 * @param s - The char array to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(char[] s, Object source)
	{
		if(debug)
		{
			Logger.log(s, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(String, Object)}, for internal engine use only
	 * @param s - The string to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(String s, Object source)
	{		
		if(debug)
		{
			Logger.log(s, source);
		}
	}
	
	/**
	 * Debug version of {@link Logger#log(Object, Object)}, for internal engine use only
	 * @param o - The object to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(Object o, Object source)
	{
		if(debug)
		{
			Logger.log(o, source);
		}
	}
}
