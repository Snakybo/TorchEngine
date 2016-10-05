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

import com.snakybo.torch.util.FileUtils;
import com.snakybo.torch.util.debug.Logger;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.NoSuchFileException;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * <p>
 * Handler for 2D textures.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Texture2D extends Texture
{
	/**
	 * <p>
	 * Create a new, empty 2D texture.
	 * </p>
	 *
	 * @param width The width of the texture in pixels.
	 * @param height The height of the texture in pixels.
	 */
	public Texture2D(int width, int height)
	{
		super("");
		
		create(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
	}
	
	Texture2D(String name)
	{
		super(name);
		
		if(isCreator())
		{
			try
			{
				URI uri = FileUtils.toURI(name);
				create(ImageIO.read(new File(uri)));
			}
			catch(IOException e)
			{
				Logger.logError(e.toString(), e);
			}
		}
	}
	
	private void create(BufferedImage bufferedImage)
	{
		IntBuffer id = BufferUtils.createIntBuffer(1);
		
		// Create texture
		glGenTextures(id);
		glBindTexture(GL_TEXTURE_2D, id.get(0));
		
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		ByteBuffer data = com.snakybo.torch.util.BufferUtils.toByteBuffer(bufferedImage);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		// Set properties
		setProperty("id", id);
		setProperty("target", GL_TEXTURE_2D);
		setProperty("bufferedImage", bufferedImage);
		
		// Set texture properties
		setFilterMode(TextureFilterMode.Trilinear);
		setWrapMode(TextureWrapMode.Repeat);
		setAnisoLevel(16);
	}
	
	@Override
	protected final void onDestroy()
	{
		glDeleteTextures((IntBuffer)getProperty("id"));
	}
	
	public final ByteBuffer getByteBuffer()
	{
		return com.snakybo.torch.util.BufferUtils.toByteBuffer((BufferedImage)getProperty("bufferedImage"));
	}
	
	@Override
	public final int getWidth()
	{
		return ((BufferedImage)getProperty("bufferedImage")).getWidth();
	}
	
	@Override
	public final int getHeight()
	{
		return ((BufferedImage)getProperty("bufferedImage")).getHeight();
	}
	
	// TODO: Expand Texture2D class
}
