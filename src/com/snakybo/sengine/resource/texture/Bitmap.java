package com.snakybo.sengine.resource.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class Bitmap
{
	private int[] pixels;
	
	private int width;
	private int height;
	
	private boolean hasAlpha;
	
	/**
	 * Create a new bitmap
	 * @param width - The width of the bitmap
	 * @param height - The height of the bitmap
	 */
	public Bitmap(int width, int height)
	{
		this.pixels	= new int[width * height];
		this.width = width;
		this.height = height;
		this.hasAlpha = false;
	}
	
	/**
	 * Load a bitmap from the disk
	 * @param fileName - The filename of the bitmap
	 */
	public Bitmap(String fileName)
	{
		File file = new File("./res/" + fileName);
		
		if(!file.exists())
		{
			Logger.logError(fileName + " cannot be found", this);
			return;
		}
		
		try
		{
			BufferedImage image = ImageIO.read(file);
			
			width = image.getWidth();
			height = image.getHeight();
			pixels = image.getRGB(0, 0, width, height, null, 0, width);
			hasAlpha = image.getColorModel().hasAlpha();
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
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);		
			image.setRGB(0, 0, width, height, pixels, 0, width);
			
			File file = new File("./res/" + outputFile + "." + format);
			ImageIO.write(image, format, file);
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
		this.pixels = pixels;
	}
	
	/**
	 * Set the pixel at (x, y)
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @param pixel - The new pixel
	 */
	public final void setPixel(int x, int y, int pixel)
	{
		pixels[y * width + x] = pixel;
	}
	
	/**
	 * @return The pixels of the bitmap
	 */
	public final int[] getPixels()
	{
		return pixels;
	}
	
	/**
	 * @param x - The X position of the pixel
	 * @param y - The Y position of the pixel
	 * @return The pixel at (x, y)
	 */
	public final int getPixel(int x, int y)
	{
		return pixels[y * width + x];
	}
	
	/**
	 * @return The width of the bitmap
	 */
	public final int getWidth()
	{
		return width;
	}
	
	/**
	 * @return The height of the bitmap
	 */
	public final int getHeight()
	{
		return height;
	}
	
	// TODO: Check if the bitmap has alpha after the used modified it with setPixel or setPixels
	/**
	 * @return Whether or not the bitmap contains an alpha channel
	 */
	public final boolean hasAlpha()
	{
		return hasAlpha;
	}
}
