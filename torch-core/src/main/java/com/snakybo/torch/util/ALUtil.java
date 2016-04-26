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

package com.snakybo.torch.util;

import static org.lwjgl.openal.AL10.AL_INVALID_ENUM;
import static org.lwjgl.openal.AL10.AL_INVALID_NAME;
import static org.lwjgl.openal.AL10.AL_INVALID_OPERATION;
import static org.lwjgl.openal.AL10.AL_INVALID_VALUE;
import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.AL_OUT_OF_MEMORY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.lwjgl.BufferUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ALUtil
{
	private ALUtil()
	{
		throw new AssertionError();
	}
	
	/**
	 * Convert an OpenAL error code to a string
	 * @param error The error code
	 * @return A string representation of the error code
	 */
	public static String getALErrorString(int error)
	{
		switch(error)
		{
		case AL_NO_ERROR:
			return "AL_NO_ERROR";
		case AL_INVALID_NAME:
			return "AL_INVALID_NAME";
		case AL_INVALID_ENUM:
			return "AL_INVALID_ENUM";
		case AL_INVALID_VALUE:
			return "AL_INVALID_VALUE";
		case AL_INVALID_OPERATION:
			return "AL_INVALID_OPERATION";
		case AL_OUT_OF_MEMORY:
			return "AL_OUT_OF_MEMORY";
		default:
			return "Invalid error code";
		}
	}
	
	// TODO: Remove this method once LWJGL is updated, LWJGL 3.0.0b doesn't have this method yet
	/**
	 * Reads the specified resource and returns the raw data as a ByteBuffer.
	 *
	 * @param resource   the resource to read
	 * @param bufferSize the initial buffer size
	 *
	 * @return the resource data
	 *
	 * @throws IOException if an IO error occurs
	 */
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;

		File file = new File(resource);
		if(file.isFile())
		{
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			
			buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);

			while(fc.read(buffer) != -1)
			{
			}
			
			fis.close();
			fc.close();
		}
		else
		{
			buffer = BufferUtils.createByteBuffer(bufferSize);

			InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if(source == null)
			{
				throw new FileNotFoundException(resource);
			}

			try
			{
				ReadableByteChannel rbc = Channels.newChannel(source);
				
				try
				{
					while(true)
					{
						int bytes = rbc.read(buffer);
						
						if(bytes == -1)
						{
							break;
						}
						
						if(buffer.remaining() == 0)
						{
							buffer = resizeBuffer(buffer, buffer.capacity() * 2);
						}
					}
				}
				finally
				{
					rbc.close();
				}
			}
			finally
			{
				source.close();
			}
		}

		buffer.flip();
		return buffer;
	}
	
	// TODO: Remove this method once LWJGL is updated, LWJGL 3.0.0b doesn't have this method yet	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)
	{
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		
		buffer.flip();
		newBuffer.put(buffer);
		
		return newBuffer;
	}
}
