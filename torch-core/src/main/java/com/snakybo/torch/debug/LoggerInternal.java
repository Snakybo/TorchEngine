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
	
	public static void log(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(b, source);
		}
	}
	
	public static void log(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(c, source);
		}
	}
	
	public static void log(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(i, source);
		}
	}
	
	public static void log(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(f, source);
		}
	}
	
	public static void log(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(d, source);
		}
	}
	
	public static void log(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(l, source);
		}
	}
	
	public static void log(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(s, source);
		}
	}
	
	public static void log(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.log(s, source);
		}
	}
	
	public static void log(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.log(o, source);
		}
	}
	
	public static void logWarning(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(b, source);
		}
	}
	
	public static void logWarning(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(c, source);
		}
	}
	
	public static void logWarning(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(i, source);
		}
	}
	
	public static void logWarning(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(f, source);
		}
	}
	
	public static void logWarning(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(d, source);
		}
	}
	
	public static void logWarning(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(l, source);
		}
	}
	
	public static void logWarning(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(s, source);
		}
	}
	
	public static void logWarning(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(s, source);
		}
	}
	
	public static void logWarning(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logWarning(o, source);
		}
	}
	
	public static void logError(boolean b, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(b, source);
		}
	}
	
	public static void logError(char c, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(c, source);
		}
	}
	
	public static void logError(int i, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(i, source);
		}
	}
	
	public static void logError(float f, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(f, source);
		}
	}
	
	public static void logError(double d, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(d, source);
		}
	}
	
	public static void logError(long l, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(l, source);
		}
	}
	
	public static void logError(char[] s, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(s, source);
		}
	}
	
	public static void logError(String s, Object source)
	{		
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(s, source);
		}
	}
	
	public static void logError(Object o, Object source)
	{
		if(Debug.LOG_DEBUG)
		{
			Logger.logError(o, source);
		}
	}
}
