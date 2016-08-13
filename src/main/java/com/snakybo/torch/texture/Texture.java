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

package com.snakybo.torch.texture;

import com.snakybo.torch.color.Color;
import com.snakybo.torch.color.Color32;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.interfaces.IDestroyable;
import com.snakybo.torch.util.ToByteBuffer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Texture implements IDestroyable
{
	protected TextureAsset asset;
	
	@Override
	public void destroy()
	{
		asset.destroy();
	}
	
	@Override
	public final int hashCode()
	{
		return asset.bufferedImage.hashCode();
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		return obj instanceof Texture && ((Texture)obj).asset.bufferedImage.equals(asset.bufferedImage);
	}
	
	/**
	 * Bind the texture to the specified unit
	 * @param unit The unit to bind to
	 */
	public abstract void bind(int unit);
	
	/**
	 * Bind the cube map
	 */
	public final void bind()
	{
		bind(0);
	}
	
	public final void savePNG(String outputFile)
	{
		save(outputFile, "png");
	}
	
	public final void saveJPG(String outputFile)
	{
		save(outputFile, "jpg");
	}
	
	private void save(String outputFile, String format)
	{
		try
		{
			LoggerInternal.log("Saving texture to: " + outputFile + "." + format);
			
			File file = new File(outputFile + "." + format);
			ImageIO.write(asset.bufferedImage, format, file);
		}
		catch(IOException e)
		{
			Logger.logError(e.toString(), e);
		}
	}
	
	/**
	 * Set the pixel data of the texture.
	 * @param pixels The new pixel data.
	 */
	public final void setPixels(int[] pixels)
	{
		asset.bufferedImage.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}
	
	/**
	 * Set the pixel data at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new data of the pixel.
	 */
	public final void setPixel(int x, int y, Color pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel data at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new data of the pixel.
	 */
	public final void setPixel(int x, int y, Color32 pixel)
	{
		setPixel(x, y, pixel.getARGB());
	}
	
	/**
	 * Set the pixel data at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new data of the pixel.
	 */
	public final void setPixel(int x, int y, int pixel)
	{
		asset.bufferedImage.setRGB(x, y, pixel);
	}
	
	/**
	 * Convert the bitmap to a ByteBuffer.
	 * @return The {@link ByteBuffer} representation of the bitmap.
	 */
	public final ByteBuffer getByteByffer()
	{
		return ToByteBuffer.convert(asset.bufferedImage);
	}
	
	/**
	 * Getthe pixel data of the texture.
	 * @return The pixel data.
	 */
	public final int[] getPixels()
	{
		return asset.bufferedImage.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	/**
	 * Get the pixel data at (x, y).
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @return The pixel data at (x, y).
	 */
	public final int getPixel(int x, int y)
	{
		return asset.bufferedImage.getRGB(x, y);
	}
	
	public int getWidth()
	{
		return asset.bufferedImage.getWidth();
	}
	
	public int getHeight()
	{
		return asset.bufferedImage.getHeight();
	}
}