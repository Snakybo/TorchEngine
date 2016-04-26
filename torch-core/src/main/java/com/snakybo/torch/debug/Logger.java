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

import java.io.PrintStream;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Logger
{
	private Logger()
	{
		throw new AssertionError();
	}
	
	/**
	 * Logs a message to the console
	 * @param b The boolean to log
	 */
	public static void log(boolean b)
	{
		log(b, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param b The boolean to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(boolean b, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(b);
	}
	
	/**
	 * Logs a message to the console
	 * @param c The char to log
	 */
	public static void log(char c)
	{
		log(c, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param c The char to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(char c, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(c);
	}
	
	/**
	 * Logs a message to the console
	 * @param i The int to log
	 */
	public static void log(int i)
	{
		log(i, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param i The int to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(int i, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(i);
	}
	
	/**
	 * Logs a message to the console
	 * @param f The float to log
	 */
	public static void log(float f)
	{
		log(f, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param f The float to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(float f, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(f);
	}
	
	/**
	 * Logs a message to the console
	 * @param d The double to log
	 */
	public static void log(double d)
	{
		log(d, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param d The double to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(double d, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(d);
	}
	
	/**
	 * Logs a message to the console
	 * @param l The long to log
	 */
	public static void log(long l)
	{
		log(l, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param l The long to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(long l, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(l);
	}
	
	/**
	 * Logs a message to the console
	 * @param s The char array to log
	 */
	public static void log(char[] s)
	{
		log(s, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param s The char array to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(char[] s, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(s);
	}
	
	/**
	 * Logs a message to the console
	 * @param s The string to log
	 */
	public static void log(String s)
	{		
		log(s, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param s The string to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(String s, Object source)
	{		
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(s);
	}
	
	/**
	 * Logs a message to the console
	 * @param o The object to log
	 */
	public static void log(Object o)
	{
		log(o, null);
	}
	
	/**
	 * Logs a message to the console
	 * @param o The object to log
	 * @param source The source of the message, can also be a string
	 */
	public static void log(Object o, Object source)
	{
		logPrefix(LogLevel.Message, source, System.out);
		System.out.println(o);
	}
	
	/**
	 * Logs a warning to the console
	 * @param b The boolean to log
	 */
	public static void logWarning(boolean b)
	{
		logWarning(b, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param b The boolean to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(boolean b, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(b);
	}
	
	/**
	 * Logs a warning to the console
	 * @param c The char to log
	 */
	public static void logWarning(char c)
	{
		logWarning(c, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param c The char to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(char c, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(c);
	}
	
	/**
	 * Logs a warning to the console
	 * @param i The int to log
	 */
	public static void logWarning(int i)
	{
		logWarning(i, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param i The int to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(int i, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(i);
	}
	
	/**
	 * Logs a warning to the console
	 * @param f The float to log
	 */
	public static void logWarning(float f)
	{
		logWarning(f, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param f The float to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(float f, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(f);
	}
	
	/**
	 * Logs a warning to the console
	 * @param d The double to log
	 */
	public static void logWarning(double d)
	{
		logWarning(d, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param d The double to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(double d, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(d);
	}
	
	/**
	 * Logs a warning to the console
	 * @param l The long to log
	 */
	public static void logWarning(long l)
	{
		logWarning(l, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param l The long to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(long l, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(l);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s The char array to log
	 */
	public static void logWarning(char[] s)
	{
		logWarning(s, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s The char array to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(char[] s, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(s);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s The string to log
	 */
	public static void logWarning(String s)
	{		
		logWarning(s, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s The string to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(String s, Object source)
	{		
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(s);
	}
	
	/**
	 * Logs a warning to the console
	 * @param o The object to log
	 */
	public static void logWarning(Object o)
	{
		logWarning(o, null);
	}
	
	/**
	 * Logs a warning to the console
	 * @param o The object to log
	 * @param source The source of the warning, can also be a string
	 */
	public static void logWarning(Object o, Object source)
	{
		logPrefix(LogLevel.Warning, source, System.out);
		System.out.println(o);
	}
	
	/**
	 * Logs an error to the console
	 * @param b The boolean to log
	 */
	public static void logError(boolean b)
	{
		logError(b, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param b The boolean to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(boolean b, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(b);
	}
	
	/**
	 * Logs an error to the console
	 * @param c The char to log
	 */
	public static void logError(char c)
	{
		logError(c, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param c The char to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(char c, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(c);
	}
	
	/**
	 * Logs an error to the console
	 * @param i The int to log
	 */
	public static void logError(int i)
	{
		logError(i, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param i The int to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(int i, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(i);
	}
	
	/**
	 * Logs an error to the console
	 * @param f The float to log
	 */
	public static void logError(float f)
	{
		logError(f, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param f The float to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(float f, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(f);
	}
	
	/**
	 * Logs an error to the console
	 * @param d The double to log
	 */
	public static void logError(double d)
	{
		logError(d, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param d The double to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(double d, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(d);
	}
	
	/**
	 * Logs an error to the console
	 * @param l The long to log
	 */
	public static void logError(long l)
	{
		logError(l, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param l The long to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(long l, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(l);
	}
	
	/**
	 * Logs an error to the console
	 * @param s The char array to log
	 */
	public static void logError(char[] s)
	{
		logError(s, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param s The char array to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(char[] s, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(s);
	}
	
	/**
	 * Logs an error to the console
	 * @param s The string to log
	 */
	public static void logError(String s)
	{		
		logError(s, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param s The string to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(String s, Object source)
	{		
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(s);
	}
	
	/**
	 * Logs an error to the console
	 * @param o The object to log
	 */
	public static void logError(Object o)
	{
		logError(o, null);
	}
	
	/**
	 * Logs an error to the console
	 * @param o The object to log
	 * @param source The source of the error, can also be a string
	 */
	public static void logError(Object o, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.out.println(o);
	}
	
	/**
	 * Logs an exception
	 * @param e The exception to log
	 */
	public static void logException(Exception e)
	{
		logException(e, null);
	}
	
	/**
	 * Logs an exception
	 * @param e The exception to log
	 * @param source The source of the exception, can also be a string
	 */
	public static void logException(Exception e, Object source)
	{
		logPrefix(LogLevel.Exception, source, System.err);
		e.printStackTrace();
	}
	
	/**
	 * Log the prefix
	 * @param level The desired log level, {@link LogLevel#Message}, {@link LogLevel#Warning}, {@link LogLevel#Error} or {@link LogLevel#Exception}
	 * @param source The source of the log
	 * @param ps The {@link PrintStream} to use
	 * @see LogLevel
	 */
	private static void logPrefix(LogLevel level, Object source, PrintStream ps)
	{
		ps.print("[" + level + "]");
		
		if(source != null && !source.toString().isEmpty())
		{
			if(source instanceof String)
			{
				ps.print("[" + source.toString() + "]");
			}
			else
			{
				ps.print("[" + source.getClass().getSimpleName() + "]");
			}
		}
		
		// Add a whitespace
		ps.print(" ");
	}
}
