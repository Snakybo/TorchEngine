package com.snakybo.sengine.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class Bitmap
{
	private BufferedImage bitmap;
	
	/**
	 * Create a new bitmap
	 * @param width - The width of the bitmap
	 * @param height - The height of the bitmap
	 */
	public Bitmap(int width, int height)
	{
		bitmap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Load a bitmap from the disk
	 * @param fileName - The filename of the bitmap
	 */
	public Bitmap(String fileName)
	{
		try
		{
			File file = new File("./res/" + fileName);			
			if(!file.exists())
			{
				throw new FileNotFoundException(fileName + " cannot be found");
			}
			
			bitmap = ImageIO.read(file);
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
		}
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
			Logger.log("Saving bitmap to: " + outputFile + "." + format, this);
			
			File file = new File("./res/" + outputFile + "." + format);
			ImageIO.write(bitmap, format, file);
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
		bitmap.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}
	
	/**
	 * Set the pixel at (x, y)
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @param pixel - The new pixel
	 */
	public final void setPixel(int x, int y, int pixel)
	{
		bitmap.setRGB(x, y, pixel);
	}
	
	/**
	 * @return The pixels of the bitmap
	 */
	public final int[] getPixels()
	{
		return bitmap.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	/**
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @return The pixel at (x, y)
	 */
	public final int getPixel(int x, int y)
	{
		return bitmap.getRGB(x, y);
	}
	
	/**
	 * @return The width of the bitmap
	 */
	public final int getWidth()
	{
		return bitmap.getWidth();
	}
	
	/**
	 * @return The height of the bitmap
	 */
	public final int getHeight()
	{
		return bitmap.getHeight();
	}
	
	/**
	 * @return Whether or not the bitmap contains an alpha channel
	 */
	public final boolean hasAlpha()
	{
		return bitmap.getColorModel().hasAlpha();
	}
}
