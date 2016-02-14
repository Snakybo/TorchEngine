package com.snakybo.sengine.texture;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_BASE_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class Texture
{
	private int target;
	
	private int width;
	private int height;
	
	private int id;
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 */
	public Texture(int width, int height)
	{
		this(width, height, null);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 */
	public Texture(int width, int height, ByteBuffer data)
	{
		this(width, height, data, GL_TEXTURE_2D);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 */
	public Texture(int width, int height, ByteBuffer data, int target)
	{
		this(width, height, data, target, GL_NEAREST_MIPMAP_LINEAR);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 */
	public Texture(int width, int height, ByteBuffer data, int target, int filters)
	{
		this(width, height, data, target, filters, GL_RGBA);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 */
	public Texture(int width, int height, ByteBuffer data, int target, int filters, int internalformat)
	{
		this(width, height, data, target, filters, internalformat, GL_RGBA);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 * @param format - The format of the texture
	 */
	public Texture(int width, int height, ByteBuffer data, int target, int filters, int internalformat, int format)
	{
		this(width, height, data, target, filters, internalformat, GL_RGBA, false);
	}
	
	/**
	 * Create a new texture
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 * @param format - The format of the texture
	 * @param clamp - Whether or not to clamp the texture
	 */
	public Texture(int width, int height, ByteBuffer data, int target, int filters, int internalformat, int format, boolean clamp)
	{
		initialize(width, height, data, target, filters, internalformat, format, clamp);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 */
	public Texture(String fileName)
	{
		this(fileName, GL_TEXTURE_2D);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 */
	public Texture(String fileName, int target)
	{
		this(fileName, target, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 */
	public Texture(String fileName, int target, int filters)
	{
		this(fileName, target, filters, GL_RGBA);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 */
	public Texture(String fileName, int target, int filters, int internalformat)
	{
		this(fileName, target, filters, internalformat, GL_RGBA);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 * @param format - The format of the texture
	 */
	public Texture(String fileName, int target, int filters, int internalformat, int format)
	{
		this(fileName, target, filters, internalformat, format, false);
	}
	
	/**
	 * Load a texture from a file
	 * @param fileName - The name file to load
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 * @param format - The format of the texture
	 * @param clamp - Whether or not to clamp the texture
	 */
	public Texture(String fileName, int target, int filters, int internalformat, int format, boolean clamp)
	{
		Bitmap bitmap = new Bitmap(fileName);
		ByteBuffer data = BufferUtils.createByteBuffer(bitmap.getHeight() * bitmap.getWidth() * 4);
		
		for(int y = 0; y < bitmap.getHeight(); y++)
		{
			for(int x = 0; x < bitmap.getWidth(); x++)
			{
				int pixel = bitmap.getPixel(x, y);
				int alpha = bitmap.hasAlpha() ? (pixel >> 24) & 0xFF : 0xFF;
				
				data.put((byte)((pixel >> 16) & 0xFF));
				data.put((byte)((pixel >> 8) & 0xFF));
				data.put((byte)(pixel & 0xFF));
				data.put((byte)alpha);
			}
		}
		
		data.flip();
		
		initialize(bitmap.getWidth(), bitmap.getHeight(), data, target, filters, internalformat, format, clamp);
	}
	
	/**
	 * Bind the texture
	 */
	public final void bind()
	{
		bind(0);
	}
	
	/**
	 * Bind the texture
	 * @param unit - The texture unit to use: {@code GL_TEXTURE0 + unit}
	 */
	public final void bind(int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			Logger.throwException(IllegalArgumentException.class, new IllegalArgumentException("The unit: " + unit + " is out of bounds"), this);
		}
		
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(target, id);
	}
	
	/**
	 * Initialize the texture, used by both {@link #Texture(int, int, ByteBuffer, int, int, int, int, boolean)} and {@link #Texture(String, int, int, int, int, boolean)}
	 * @param width - The width of the texture
	 * @param height - The height of the texture
	 * @param data - The data of the texture
	 * @param target - The OpenGL texture target, usually {@code GL_TEXTURE_2D}
	 * @param filters - The filters to use, usually {@code GL_NEAREST}, {@code GL_LINEAR}, or their mipmap variants
	 * @param internalformat - The internal format of the texture
	 * @param format - The format of the texture
	 * @param clamp - Whether or not to clamp the texture
	 */
	private final void initialize(int width, int height, ByteBuffer data, int target, int filters, int internalformat, int format, boolean clamp)
	{
		this.target = target;
		this.width = width;
		this.height = height;
		this.id = glGenTextures();
		
		glBindTexture(target, id);
		
		glTexParameterf(target, GL_TEXTURE_MIN_FILTER, filters);
		glTexParameterf(target, GL_TEXTURE_MAG_FILTER, filters);
		
		if(clamp)
		{
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(target, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		}
		
		glTexImage2D(target, 0, internalformat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
		
		boolean mipmap =
				filters == GL_NEAREST_MIPMAP_NEAREST ||
				filters == GL_NEAREST_MIPMAP_LINEAR ||
				filters == GL_LINEAR_MIPMAP_NEAREST ||
				filters == GL_LINEAR_MIPMAP_LINEAR;
		
		if(mipmap)
		{
			glGenerateMipmap(target);
			
			// TODO: Might have to clamp maxAnisotropic to 0-8
			float maxAnisotropic = glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
			glTexParameterf(target, GL_TEXTURE_MAX_ANISOTROPY_EXT, maxAnisotropic);
		}
		else
		{
			glTexParameteri(target, GL_TEXTURE_BASE_LEVEL, 0);
			glTexParameteri(target, GL_TEXTURE_MAX_LEVEL, 0);
		}
	}
	
	public final int getWidth()
	{
		return width;
	}
	
	public final int getHeight()
	{
		return height;
	}
}
