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

import java.util.logging.Level;

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
	
	public static void log(String msg)
	{
		LoggerInternal.logInternal(Level.INFO, msg);
	}
	
	public static void log(String msg, Object param1)
	{
		LoggerInternal.logInternal(Level.INFO, msg, param1);
	}
	
	public static void log(String msg, Object[] params)
	{
		LoggerInternal.logInternal(Level.INFO, msg, params);
	}
	
	public static void logWarning(String msg)
	{
		LoggerInternal.logInternal(Level.WARNING, msg);
	}
	
	public static void logWarning(String msg, Object param1)
	{
		LoggerInternal.logInternal(Level.WARNING, msg, param1);
	}
	
	public static void logWarning(String msg, Object[] params)
	{
		LoggerInternal.logInternal(Level.WARNING, msg, params);
	}
	
	public static void logError(String msg)
	{
		LoggerInternal.logInternal(Level.SEVERE, msg);
	}
	
	public static void logError(String msg, Object param1)
	{
		LoggerInternal.logInternal(Level.SEVERE, msg, param1);
	}
	
	public static void logError(String msg, Object[] params)
	{
		LoggerInternal.logInternal(Level.SEVERE, msg, params);
	}
	
	public static void logError(String msg, Throwable thrown)
	{
		LoggerInternal.logInternal(Level.SEVERE, msg, thrown);
	}
	
	public static void setLogLevel(Level level)
	{
		LoggerInternal.logger.setLevel(level);
	}
}