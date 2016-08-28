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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * <p>
 * Used internally by the engine.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class LoggerInternal
{
	private static Logger logger;
	
	static
	{
		logger = Logger.getLogger("Logger");
		logger.setLevel(Level.ALL);
		
		try
		{
			String path = System.getenv("APPDATA") + "\\TorchEngine\\log";
			Files.createDirectories(Paths.get(path));
			
			FileHandler fileHandler = new FileHandler(path + "\\log.txt");
			logger.addHandler(fileHandler);
			
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		}
		catch(Exception e)
		{
			com.snakybo.torch.debug.Logger.logError(e.toString(), e);
		}
	}
	
	private LoggerInternal()
	{
		throw new AssertionError();
	}
	
	public static void log(Object msg)
	{
		logInternal(Level.FINE, msg);
	}
	
	static void logInternal(Level level, Object msg)
	{
		StackTraceElement ste = getStackTraceElement();
		logger.logp(level, ste.getClassName(), ste.getMethodName(), msg.toString());
	}
	
	static void logInternal(Level level, Object msg, Throwable thrown)
	{
		StackTraceElement ste = getStackTraceElement();
		logger.logp(level, ste.getClassName(), ste.getMethodName(), msg.toString(), thrown);
	}
	
	private static StackTraceElement getStackTraceElement()
	{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		if(stackTrace[4].getClassName().contains("LoggerInternal"))
		{
			return stackTrace[5];
		}
		
		return stackTrace[4];
	}
}
