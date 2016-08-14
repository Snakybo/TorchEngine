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

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.util.FileUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Texture2D extends Texture
{
	Texture2D(TextureAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Texture2D(String name, BufferedImage bufferedImage, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		asset = new TextureAsset(name, bufferedImage, 1);
		asset.init(filters, anisoLevel, internalFormat, format, clamp);
	}
	
	public Texture2D(int width, int height)
	{
		this(width, height, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	public Texture2D(int width, int height, int filters)
	{
		this(width, height, filters, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
	}
	
	public Texture2D(int width, int height, int filters, float anisoLevel)
	{
		this(width, height, filters, anisoLevel, GL_RGBA);
	}
	
	public Texture2D(int width, int height, int filters, float anisoLevel, int internalFormat)
	{
		this(width, height, filters, anisoLevel, internalFormat, GL_RGBA);
	}
	
	public Texture2D(int width, int height, int filters, float anisoLevel, int internalFormat, int format)
	{
		this(width, height, filters, anisoLevel, internalFormat, format, false);
	}
	
	public Texture2D(int width, int height, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), filters, anisoLevel, internalFormat, format, clamp);
	}
	
	public Texture2D(BufferedImage bufferedImage)
	{
		this(bufferedImage, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	public Texture2D(BufferedImage bufferedImage, int filters)
	{
		this(bufferedImage, filters, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
	}
	
	public Texture2D(BufferedImage bufferedImage, int filters, float anisoLevel)
	{
		this(bufferedImage, filters, anisoLevel, GL_RGBA, GL_RGBA);
	}
	
	public Texture2D(BufferedImage bufferedImage, int filters, float anisoLevel, int internalFormat, int format)
	{
		this(bufferedImage, filters, anisoLevel, internalFormat, format, false);
	}
	
	public Texture2D(BufferedImage bufferedImage, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		asset = new TextureAsset("", bufferedImage, 1);
		asset.init(filters, anisoLevel, internalFormat, format, clamp);
	}
	
	@Override
	public void bind(int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			throw new IllegalArgumentException("The texture unit " + unit + " is out of bounds");
		}

		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, asset.id.get(0));
	}
}
