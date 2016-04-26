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

package com.snakybo.torch.debug;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class LoggerInternal
{
	private LoggerInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(boolean, Object)}, for internal engine use only
	 * @param b The boolean to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(b, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(char, Object)}, for internal engine use only
	 * @param c The char to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(c, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(int, Object)}, for internal engine use only
	 * @param i The int to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(i, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(float, Object)}, for internal engine use only
	 * @param f The float to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(f, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(double, Object)}, for internal engine use only
	 * @param d The double to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(d, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(long, Object)}, for internal engine use only
	 * @param l The long to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(l, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(char[], Object)}, for internal engine use only
	 * @param s The char array to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(String, Object)}, for internal engine use only
	 * @param s The string to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.log(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#log(Object, Object)}, for internal engine use only
	 * @param o The object to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(o, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(boolean, Object)}, for internal engine use only
	 * @param b The boolean to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(b, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(char, Object)}, for internal engine use only
	 * @param c The char to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(c, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(int, Object)}, for internal engine use only
	 * @param i The int to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(i, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(float, Object)}, for internal engine use only
	 * @param f The float to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(f, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(double, Object)}, for internal engine use only
	 * @param d The double to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(d, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(long, Object)}, for internal engine use only
	 * @param l The long to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(l, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(char[], Object)}, for internal engine use only
	 * @param s The char array to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(String, Object)}, for internal engine use only
	 * @param s The string to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logWarning(Object, Object)}, for internal engine use only
	 * @param o The object to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logWarning(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(o, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(boolean, Object)}, for internal engine use only
	 * @param b The boolean to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(b, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(char, Object)}, for internal engine use only
	 * @param c The char to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(c, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(int, Object)}, for internal engine use only
	 * @param i The int to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(i, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(float, Object)}, for internal engine use only
	 * @param f The float to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(f, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(double, Object)}, for internal engine use only
	 * @param d The double to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(d, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(long, Object)}, for internal engine use only
	 * @param l The long to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(l, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(char[], Object)}, for internal engine use only
	 * @param s The char array to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(String, Object)}, for internal engine use only
	 * @param s The string to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(s, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logError(Object, Object)}, for internal engine use only
	 * @param o The object to log
	 * @param source The source of the message, can also be a string
	 */
	public static void logError(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(o, source);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logException(Exception)}, for internal engine use only
	 * @param e The exception to log
	 */
	public static void logException(Exception e)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logException(e);
		}
	}
	
	/**
	 * Debug.LOG_DEBUG version of {@link Logger#logException(Exception, Object)}, for internal engine use only
	 * @param e The exception to log
	 * @param source The source of the exception, can also be a string
	 */
	public static void logException(Exception e, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logException(e, source);
		}
	}
}
