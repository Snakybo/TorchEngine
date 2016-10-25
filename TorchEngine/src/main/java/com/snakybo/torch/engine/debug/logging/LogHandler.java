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

package com.snakybo.torch.engine.debug.logging;

import com.snakybo.torch.engine.debug.Debug;
import com.snakybo.torch.engine.util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Snakybo
 * @since 1.0
 */
public class LogHandler implements ILogHandler
{
	private FileChannel fileChannel;
	
	public LogHandler()
	{
		try
		{
			Path logPath = Paths.get(System.getenv("APPDATA") + "\\TorchEngine\\log.txt");
			
			if(Files.exists(logPath))
			{
				Path logPathPrev = Paths.get(System.getenv("APPDATA") + "\\TorchEngine\\log-prev.txt");
				Files.move(logPath, logPathPrev, StandardCopyOption.REPLACE_EXISTING);
			}
			
			Files.createDirectories(logPath.getParent());
			Files.createFile(logPath);
			
			FileOutputStream fileOutputStream = new FileOutputStream(logPath.toFile());
			fileChannel = fileOutputStream.getChannel();
		}
		catch(IOException e)
		{
			System.err.println("Unable to create log handler: " + e.getMessage());
			System.exit(1);
		}
	}
	
	@Override
	public void destroy()
	{
		try
		{
			fileChannel.close();
		}
		catch(IOException e)
		{
			System.err.println("Unable to close log handler: " + e.getMessage());
		}
	}
	
	@Override
	public void log(LogType logType, Object message)
	{
		StackTraceElement[] stackTrace = Util.getStackTrace(true);
		StackTraceElement source = null;
		
		// Get source
		for(StackTraceElement stackTraceElement : stackTrace)
		{
			if( !stackTraceElement.getClassName().equals(Util.class.getName()) &&
				!stackTraceElement.getClassName().equals(LogHandler.class.getName()) &&
				!stackTraceElement.getClassName().equals(Logger.class.getName()) &&
				!stackTraceElement.getClassName().equals(Debug.class.getName()) &&
				!stackTraceElement.getClassName().equals(Thread.class.getName()))
			{
				source = stackTraceElement;
				break;
			}
		}
		
		writeToConsole(logType, message, stackTrace, source);
		writeToFile(message, stackTrace, source);
		
		Logger.invokeCallback(logType, message, stackTrace);
	}
	
	private String constructMessage(Object message)
	{
		StringBuilder builder = new StringBuilder();
		
		if(message != null)
		{
			if(!message.getClass().isArray())
			{
				builder.append(message.toString());
			}
			else
			{
				// Prettify arrays
				builder.append("[");
				
				int l = Array.getLength(message);
				for(int i = 0; i < l; i++)
				{
					builder.append(i);
					builder.append("=");
					builder.append(Array.get(message, i));
					
					if(i < l - 1)
					{
						builder.append(", ");
					}
				}
				
				builder.append("]");
			}
		}
		else
		{
			builder.append("null");
		}
		
		return builder.toString();
	}
	
	private void writeToConsole(LogType logType, Object message, StackTraceElement[] stackTrace, StackTraceElement source)
	{
		StringBuilder builder = new StringBuilder();
		
		// Message
		builder.append(constructMessage(message));
		
		// Source
		builder.append("\n");
		builder.append(source.toString());
		
		// Empty line
		builder.append("\n");
		
		switch(logType)
		{
		case Message:
		case Warning:
			System.out.println(builder.toString());
			break;
		case Error:
		case Exception:
		case Assert:
			System.err.println(builder.toString());
			break;
		}
	}
	
	private void writeToFile(Object message, StackTraceElement[] stackTrace, StackTraceElement source)
	{
		StringBuilder builder = new StringBuilder();
		
		// Message
		builder.append(constructMessage(message));
		
		// Stack trace
		builder.append("\n");
		
		for(StackTraceElement stackTraceElement : stackTrace)
		{
			builder.append(stackTraceElement.toString()).append("\n");
		}
		
		// Source
		builder.append("\n");
		builder.append("Source: ");
		builder.append(source.toString());
		
		// Empty line
		builder.append("\n");
		
		// Write to file
		try
		{
			byte[] bytes = builder.toString().getBytes(Charset.forName("UTF-8"));
			ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
			
			fileChannel.write(byteBuffer);
		}
		catch(IOException e)
		{
			System.err.println("Error while writing message to log: " + e.getMessage());
		}
	}
}
