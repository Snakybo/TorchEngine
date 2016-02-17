package com.snakybo.sengine.debug;

import java.io.PrintStream;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class Logger
{
	private Logger()
	{
		throw new AssertionError();
	}
	
	/**
	 * Logs a message to the console
	 * @param b - The boolean to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(boolean b, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(b);
	}
	
	/**
	 * Logs a message to the console
	 * @param c - The char to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(char c, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(c);
	}
	
	/**
	 * Logs a message to the console
	 * @param i - The int to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(int i, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(i);
	}
	
	/**
	 * Logs a message to the console
	 * @param f - The float to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(float f, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(f);
	}
	
	/**
	 * Logs a message to the console
	 * @param d - The double to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(double d, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(d);
	}
	
	/**
	 * Logs a message to the console
	 * @param l - The long to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(long l, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(l);
	}
	
	/**
	 * Logs a message to the console
	 * @param s - The char array to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(char[] s, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(s);
	}
	
	/**
	 * Logs a message to the console
	 * @param s - The string to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(String s, Object source)
	{		
		logPrefix(LogLevel.Message, source);
		System.out.println(s);
	}
	
	/**
	 * Logs a message to the console
	 * @param o - The object to log
	 * @param source - The source of the message, can also be a string
	 */
	public static void log(Object o, Object source)
	{
		logPrefix(LogLevel.Message, source);
		System.out.println(o);
	}
	
	/**
	 * Logs a warning to the console
	 * @param b - The boolean to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(boolean b, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(b);
	}
	
	/**
	 * Logs a warning to the console
	 * @param c - The char to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(char c, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(c);
	}
	
	/**
	 * Logs a warning to the console
	 * @param i - The int to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(int i, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(i);
	}
	
	/**
	 * Logs a warning to the console
	 * @param f - The float to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(float f, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(f);
	}
	
	/**
	 * Logs a warning to the console
	 * @param d - The double to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(double d, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(d);
	}
	
	/**
	 * Logs a warning to the console
	 * @param l - The long to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(long l, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(l);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s - The char array to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(char[] s, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(s);
	}
	
	/**
	 * Logs a warning to the console
	 * @param s - The string to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(String s, Object source)
	{		
		logPrefix(LogLevel.Warning, source);
		System.out.println(s);
	}
	
	/**
	 * Logs a warning to the console
	 * @param o - The object to log
	 * @param source - The source of the warning, can also be a string
	 */
	public static void logWarning(Object o, Object source)
	{
		logPrefix(LogLevel.Warning, source);
		System.out.println(o);
	}
	
	/**
	 * Logs an error to the console
	 * @param b - The boolean to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(boolean b, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(b);
	}
	
	/**
	 * Logs an error to the console
	 * @param c - The char to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(char c, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(c);
	}
	
	/**
	 * Logs an error to the console
	 * @param i - The int to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(int i, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(i);
	}
	
	/**
	 * Logs an error to the console
	 * @param f - The float to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(float f, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(f);
	}
	
	/**
	 * Logs an error to the console
	 * @param d - The double to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(double d, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(d);
	}
	
	/**
	 * Logs an error to the console
	 * @param l - The long to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(long l, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(l);
	}
	
	/**
	 * Logs an error to the console
	 * @param s - The char array to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(char[] s, Object source)
	{
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(s);
	}
	
	/**
	 * Logs an error to the console
	 * @param s - The string to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(String s, Object source)
	{		
		logPrefix(LogLevel.Error, source, System.err);
		System.err.println(s);
	}
	
	/**
	 * Logs an error to the console
	 * @param o - The object to log
	 * @param source - The source of the error, can also be a string
	 */
	public static void logError(Object o, Object source)
	{
		logPrefix(LogLevel.Error, source);
		System.out.println(o);
	}
	
	/**
	 * Throws an exception, and logs it to the console
	 * @param clazz - The type of the exception
	 * @param e - The exception to log
	 * @param source - The source of the exception, can also be a string
	 */
	public static <T extends Exception> void throwException(Class<T> clazz, Exception e, Object source) throws T
	{
		logPrefix(LogLevel.Exception, source, System.err);
		throw clazz.cast(e);
	}
	
	/**
	 * Logs an exception
	 * @param e - The exception to log
	 * @param source - The source of the exception, can also be a string
	 */
	public static void logException(Exception e, Object source)
	{
		logPrefix(LogLevel.Exception, source, System.err);
		e.printStackTrace();
	}
	
	/**
	 * Log the prefix
	 * @param level - The desired log level, {@link LogLevel#Message}, {@link LogLevel#Warning}, {@link LogLevel#Error} or {@link LogLevel#Exception}
	 * @param source - The source of the log
	 * @see LogLevel
	 * @see #logPrefix(LogLevel, Object, PrintStream)
	 */
	private static void logPrefix(LogLevel level, Object source)
	{
		logPrefix(level, source, System.out);
	}
	
	/**
	 * Log the prefix
	 * @param level - The desired log level, {@link LogLevel#Message}, {@link LogLevel#Warning}, {@link LogLevel#Error} or {@link LogLevel#Exception}
	 * @param source - The source of the log
	 * @param ps - The {@link PrintStream} to use
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
