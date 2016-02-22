package com.snakybo.sengine.debug;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class LoggerInternal
{
	private static boolean debug;
	
	static
	{
		String env = System.getenv("DEBUG");
		
		if(env != null)
		{
			debug = env.equals("true");
		}
	}
	
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
