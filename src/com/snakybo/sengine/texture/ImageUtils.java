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

package com.snakybo.sengine.texture;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class ImageUtils
{
	private ImageUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Convert a bitmap to a ByteBuffer
	 * @param bitmap - The bitmap to convert
	 * @return The ByteBuffer
	 */
	public static ByteBuffer toByteByffer(Bitmap bitmap)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(bitmap.getWidth() * bitmap.getHeight() * 4);
		
		for(int y = 0; y < bitmap.getHeight(); y++)
		{
		    for(int x = 0; x < bitmap.getWidth(); x++)
		    {
				int pixel = bitmap.getPixel(x, y);
        		
				buffer.put((byte)((pixel >> 16) & 0xFF));
				buffer.put((byte)((pixel >> 8) & 0xFF));
				buffer.put((byte)(pixel & 0xFF));
				buffer.put((byte)((pixel >> 24) & 0xFF));
		    }
		}
		
		buffer.flip();
		return buffer;
	}
}
