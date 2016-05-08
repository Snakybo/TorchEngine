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

package com.snakybo.torch.bitmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.snakybo.torch.color.Color;
import com.snakybo.torch.color.Color32;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.resource.Resource;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Bitmap
{
	BufferedImage bufferedImage;
	
	/**
	 * Create a new bitmap.
	 * @param width The width of the bitmap.
	 * @param height The height of the bitmap.
	 */
	public Bitmap(int width, int height)
	{
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Load a bitmap from the disk.
	 * @param fileName The filename of the bitmap.
	 */
	public Bitmap(String fileName)
	{
		bufferedImage = (BufferedImage)Resource.load(fileName);
	}
	
	@Override
	public final int hashCode()
	{
		return bufferedImage.hashCode();
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		return obj instanceof Bitmap && ((Bitmap)obj).bufferedImage.equals(bufferedImage);
	}
	
	/**
	 * Save the bitmap to the disk.
	 * @param outputFile The path to the output file.
	 * @param format The format of the image.
	 */
	public final void saveToDisk(String outputFile, BitmapOutputFormat format)
	{
		try
		{
			LoggerInternal.log("Saving bitmap to: " + outputFile + "." + format, this);
			
			File file = new File(outputFile + "." + format);
			ImageIO.write(bufferedImage, format.toString(), file);
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
		}
	}
	
	/**
	 * Set all pixels of the bitmap.
	 * @param pixels The new pixels.
	 */
	public final void setPixels(int[] pixels)
	{
		bufferedImage.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}
	
	/**
	 * Set the pixel at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new pixel.
	 */
	public final void setPixel(int x, int y, Color pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new pixel.
	 */
	public final void setPixel(int x, int y, Color32 pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new pixel.
	 */
	public final void setPixel(int x, int y, int pixel)
	{
		bufferedImage.setRGB(x, y, pixel);
	}
	
	/**
	 * Convert the bitmap to a ByteBuffer.
	 * @return The {@link ByteBuffer} representation of the bitmap.
	 */
	public final ByteBuffer getByteByffer()
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(getWidth() * getHeight() * 4);
		
		for(int y = 0; y < getHeight(); y++)
		{
		    for(int x = 0; x < getWidth(); x++)
		    {
				int pixel = getPixel(x, y);
        		
				buffer.put((byte)((pixel >> 16) & 0xFF));
				buffer.put((byte)((pixel >> 8) & 0xFF));
				buffer.put((byte)(pixel & 0xFF));
				buffer.put((byte)((pixel >> 24) & 0xFF));
		    }
		}
		
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Get all pixels of the bitmap.
	 * @return The pixels of the bitmap.
	 */
	public final int[] getPixels()
	{
		return bufferedImage.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	/**
	 * Get the pixel at the specified coordinates.
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @return The pixel at (x, y).
	 */
	public final int getPixel(int x, int y)
	{
		return bufferedImage.getRGB(x, y);
	}
	
	/**
	 * Get the width of the bitmap in pixels.
	 * @return The width of the bitmap.
	 */
	public final int getWidth()
	{
		return bufferedImage.getWidth();
	}
	
	/**
	 * Get the height of the bitmap in pixels.
	 * @return The height of the bitmap.
	 */
	public final int getHeight()
	{
		return bufferedImage.getHeight();
	}
	
	/**
	 * Check whether or not the bitmap has an alpha channel.
	 * @return Whether or not the bitmap contains an alpha channel.
	 */
	public final boolean hasAlpha()
	{
		return bufferedImage.getColorModel().hasAlpha();
	}
}
