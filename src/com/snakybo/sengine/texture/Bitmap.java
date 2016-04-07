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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.util.Color;
import com.snakybo.sengine.util.Color32;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Bitmap
{
	BufferedImage bufferedImage;
	
	/**
	 * Create a new bitmap
	 * @param width - The width of the bitmap
	 * @param height - The height of the bitmap
	 */
	public Bitmap(int width, int height)
	{
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Load a bitmap from the disk
	 * @param fileName - The filename of the bitmap
	 */
	public Bitmap(String fileName)
	{
		try
		{
			LoggerInternal.log("Loading Bitmap from: ./res/" + fileName, "Bitmap");
			
			File file = new File("./res/" + fileName);			
			if(!file.exists())
			{
				throw new FileNotFoundException(fileName + " cannot be found");
			}
			
			bufferedImage = ImageIO.read(file);
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
		}
	}
	
	@Override
	public int hashCode()
	{
		return bufferedImage.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Bitmap && ((Bitmap)obj).bufferedImage.equals(bufferedImage);
	}
	
	/**
	 * Save the bitmap to the disk
	 * @param outputFile - The path to the output file
	 * @param format - The format of the image
	 */
	public final void saveToDisk(String outputFile, String format)
	{
		try
		{
			LoggerInternal.log("Saving bitmap to: " + outputFile + "." + format, this);
			
			File file = new File(outputFile + "." + format);
			ImageIO.write(bufferedImage, format, file);
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
		}
	}
	
	/**
	 * Set all pixels of the bitmap
	 * @param pixels - The new pixels
	 */
	public final void setPixels(int[] pixels)
	{
		bufferedImage.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}
	
	/**
	 * Set the pixel at (x, y)
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @param pixel - The new pixel
	 */
	public final void setPixel(int x, int y, Color pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel at (x, y)
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @param pixel - The new pixel
	 */
	public final void setPixel(int x, int y, Color32 pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel at (x, y)
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @param pixel - The new pixel
	 */
	public final void setPixel(int x, int y, int pixel)
	{
		bufferedImage.setRGB(x, y, pixel);
	}
	
	/**
	 * @return The pixels of the bitmap
	 */
	public final int[] getPixels()
	{
		return bufferedImage.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	/**
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @return The pixel at (x, y)
	 */
	public final int getPixel(int x, int y)
	{
		return bufferedImage.getRGB(x, y);
	}
	
	/**
	 * @return The width of the bitmap
	 */
	public final int getWidth()
	{
		return bufferedImage.getWidth();
	}
	
	/**
	 * @return The height of the bitmap
	 */
	public final int getHeight()
	{
		return bufferedImage.getHeight();
	}
	
	/**
	 * @return Whether or not the bitmap contains an alpha channel
	 */
	public final boolean hasAlpha()
	{
		return bufferedImage.getColorModel().hasAlpha();
	}
}
