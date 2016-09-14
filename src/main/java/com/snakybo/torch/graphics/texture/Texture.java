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

package com.snakybo.torch.graphics.texture;

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.color.ColorUtil;
import com.snakybo.torch.util.BufferUtils;
import com.snakybo.torch.util.MathUtils;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * <p>
 * You can create new {@code Texture}, or create a {@code Texture} from an image.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Texture extends Asset
{
	protected TextureAsset asset;
	
	Texture(TextureAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Texture(String name, BufferedImage bufferedImage, int type, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		asset = new TextureAsset(name, bufferedImage, 1);
		asset.init(type, filters, anisoLevel, internalFormat, format, clamp);
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 */
	public Texture(int type, int width, int height)
	{
		this(type, width, height, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 */
	public Texture(int type, int width, int height, int filters)
	{
		this(type, width, height, filters, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 */
	public Texture(int type, int width, int height, int filters, float anisoLevel)
	{
		this(type, width, height, filters, anisoLevel, GL_RGBA);
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 */
	public Texture(int type, int width, int height, int filters, float anisoLevel, int internalFormat)
	{
		this(type, width, height, filters, anisoLevel, internalFormat, GL_RGBA);
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 * @param format The format of the texture, usually the same as {@code internalFormat}.
	 */
	public Texture(int type, int width, int height, int filters, float anisoLevel, int internalFormat, int format)
	{
		this(type, width, height, filters, anisoLevel, internalFormat, format, false);
	}
	
	/**
	 * <p>
	 * Create a new texture with the specified width and height.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param width The desired width.
	 * @param height The desired height.
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 * @param format The format of the texture, usually the same as {@code internalFormat}.
	 * @param clamp If clamp is set to {@code true}, the texture will clamp, if it's set to {@code false}, the texture
	 *              will repeat instead.
	 */
	public Texture(int type, int width, int height, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), type, filters, anisoLevel, internalFormat, format, clamp);
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 */
	public Texture(BufferedImage bufferedImage, int type)
	{
		this(bufferedImage, type, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 */
	public Texture(BufferedImage bufferedImage, int type, int filters)
	{
		this(bufferedImage, type, filters, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 */
	public Texture(BufferedImage bufferedImage, int type, int filters, float anisoLevel)
	{
		this(bufferedImage, type, filters, anisoLevel, GL_RGBA, GL_RGBA);
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 */
	public Texture(BufferedImage bufferedImage, int type, int filters, float anisoLevel, int internalFormat)
	{
		this(bufferedImage, type, filters, anisoLevel, internalFormat, GL_RGBA);
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 * @param format The format of the texture, usually the same as {@code internalFormat}.
	 */
	public Texture(BufferedImage bufferedImage, int type, int filters, float anisoLevel, int internalFormat, int format)
	{
		this(bufferedImage, type, filters, anisoLevel, internalFormat, format, false);
	}
	
	/**
	 * <p>
	 * Create a texture from the given image.
	 * </p>
	 *
	 * <p>
	 * Valid values for the {@code filters} parameter are:
	 * </p>
	 *
	 * <ul>
	 *     <li>{@code GL_LINEAR}</li>
	 *     <li>{@code GL_NEAREST}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_LINEAR_MIPMAP_NEAREST}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_LINEAR}</li>
	 *     <li>{@code GL_NEAREST_MIPMAP_NEAREST}</li>
	 * </ul>
	 *
	 * <p>
	 * Note: Anisotropic filtering ({@code anisoLevel}) is only used if {@code filters} is set to one
	 * of the mipmap-enabled filters.
	 * </p>
	 *
	 * @param bufferedImage The image to create a texture from.
	 * @param type The OpenGL type (e.g. GL_TEXTURE_2D, or GL_TEXTURE_3D).
	 * @param filters The OpenGL filters (see JavaDoc for info).
	 * @param anisoLevel The desired anisotropic filtering level. If automatically clamped
	 *                   to the highest value the system supports.
	 * @param internalFormat The internal format of the texture, usually {@code GL_RGBA}.
	 * @param format The format of the texture, usually the same as {@code internalFormat}.
	 * @param clamp If clamp is set to {@code true}, the texture will clamp, if it's set to {@code false}, the texture
	 *              will repeat instead.
	 */
	public Texture(BufferedImage bufferedImage, int type, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		asset = new TextureAsset("", bufferedImage, 1);
		asset.init(type, filters, anisoLevel, internalFormat, format, clamp);
	}
	
	@Override
	public final void finalize() throws Throwable
	{
		try
		{
			destroy();
		}
		finally
		{
			super.finalize();
		}
	}
	
	@Override
	public void destroy()
	{
		if(asset != null)
		{
			asset.removeUsage();
			asset = null;
		}
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
	 * <p>
	 * Bind the texture.
	 * </p>
	 */
	public final void bind()
	{
		bind(0);
	}
	
	/**
	 * <p>
	 * Bind the texture to the specified texture unit.
	 * </p>
	 *
	 * @param unit The unit.
	 */
	public final void bind(int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			throw new IllegalArgumentException("The texture unit " + unit + " is out of bounds");
		}
		
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(asset.type, asset.id.get(0));
	}
	
	/**
	 * <p>
	 * Save the texture to the disk as a {@code png} file..
	 * </p>
	 *
	 * @param outputFile The output file.
	 */
	public final void savePNG(String outputFile)
	{
		save(outputFile, "png");
	}
	
	/**
	 * <p>
	 * Save the texture to the disk as a {@code jpg} file..
	 * </p>
	 *
	 * @param outputFile The output file.
	 */
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
	 * <p>
	 * Set the pixel data.
	 * </p>
	 *
	 * @param pixels The new pixel data.
	 */
	public final void setPixels(int[] pixels)
	{
		asset.bufferedImage.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
	}
	
	/**
	 * <p>
	 * Set the pixel data at {@code (x, y)}.
	 * </p>
	 *
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @param pixel The new data of the pixel.
	 */
	public final void setPixel(int x, int y, Color pixel)
	{
		asset.bufferedImage.setRGB(x, y, ColorUtil.colorToARGB(pixel));
	}
	
	/**
	 * <p>
	 * Convert the texture to a {@code ByteBuffer}.
	 * </p>
	 *
	 * @return A {@link ByteBuffer} containing the pixel data.
	 */
	public final ByteBuffer getByteBuffer()
	{
		return BufferUtils.toByteBuffer(asset.bufferedImage);
	}
	
	/**
	 * <p>
	 * Get an array containing all pixels.
	 * </p>
	 *
	 * @return An array containing all pixels.
	 */
	public final Color[] getPixels()
	{
		return getPixels(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * <p>
	 * Get an array containing the pixels in the specified range.
	 * </p>
	 *
	 * @param x The starting X position.
	 * @param y The starting Y position.
	 * @param width The width of the region.
	 * @param height The height of the region.
	 * @return The pixel data in the specified range.
	 */
	public final Color[] getPixels(int x, int y, int width, int height)
	{
		x = MathUtils.clamp(x, 0, getWidth());
		y = MathUtils.clamp(y, 0, getHeight());
		width = MathUtils.clamp(width, 0, getWidth());
		height = MathUtils.clamp(height, 0, getHeight());
		
		int[] pixels = asset.bufferedImage.getRGB(x, y, width, height, null, 0, width);
		Color[] colors = new Color[pixels.length];
		
		for(int i = 0; i < pixels.length; i++)
		{
			colors[i] = ColorUtil.RGBToColor(pixels[i]);
		}
		
		return colors;
	}
	
	/**
	 * <p>
	 * Get the pixel data at {@code (x, y)}.
	 * </p>
	 *
	 * @param x The X position of the pixel.
	 * @param y The Y position of the pixel.
	 * @return The pixel data at (x, y).
	 */
	public final Color getPixel(int x, int y)
	{
		return ColorUtil.RGBToColor(asset.bufferedImage.getRGB(x, y));
	}
	
	/**
	 * <p>
	 * Get the width.
	 * </p>
	 *
	 * @return The width.
	 */
	public final int getWidth()
	{
		return asset.bufferedImage.getWidth();
	}
	
	/**
	 * <p>
	 * Get the height.
	 * </p>
	 *
	 * @return The height.
	 */
	public final int getHeight()
	{
		return asset.bufferedImage.getHeight();
	}
}
