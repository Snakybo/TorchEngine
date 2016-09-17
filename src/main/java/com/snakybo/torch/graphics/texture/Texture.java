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
import com.snakybo.torch.util.MathUtils;
import com.snakybo.torch.util.debug.Logger;
import org.lwjgl.opengl.GL;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_BASE_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;

/**
 * <p>
 * Base class for textures.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public abstract class Texture extends Asset
{
	protected final int id;
	protected final int target;
	
	TextureAsset asset;
	
	protected Texture(int target, int width, int height)
	{
		this(target, "", new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
	}
	
	protected Texture(int target, String name, BufferedImage bufferedImage)
	{
		this.asset = new TextureAsset(target, name, bufferedImage);
		
		this.id = asset.id.get(0);
		this.target = asset.target;
		
		setFilterMode(TextureFilterMode.Bilinear);
		setWrapMode(TextureWrapMode.Repeat);
		setAnisoLevel(16);
	}
	
	protected Texture(TextureAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
		
		this.id = asset.id.get(0);
		this.target = asset.target;
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
	public final int hashCode()
	{
		int hashCode = 654;
		
		hashCode += 31 * asset.bufferedImage.hashCode();
		hashCode += 31 * asset.id.hashCode();
		hashCode += 31 * asset.target;
		
		return hashCode;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
		return true;
	}
		
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		Texture t = (Texture)o;
		
		if(!asset.bufferedImage.equals(t.asset.bufferedImage))
		{
			return false;
		}
		
		if(!asset.id.equals(t.asset.id))
		{
			return false;
		}
		
		return asset.target == t.asset.target;
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
	
	/**
	 * <p>
	 * Set the texture's filter mode.
	 * </p>
	 *
	 * @param filterMode The filtering mode of the texture.
	 */
	public final void setFilterMode(TextureFilterMode filterMode)
	{
		glBindTexture(target, id);
		
		switch(filterMode)
		{
		case Point:
			glTexParameterf(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			break;
		case Bilinear:
			glTexParameterf(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			break;
		case Trilinear:
			glTexParameterf(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameterf(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		}
		
		glBindTexture(target, 0);
	}
	
	/**
	 * <p>
	 * Set the texture's wrap mode.
	 * </p>
	 *
	 * @param wrapMode The wrap mode of the texture.
	 */
	public final void setWrapMode(TextureWrapMode wrapMode)
	{
		glBindTexture(target, id);
		
		switch(wrapMode)
		{
		case Clamp:
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			break;
		case Repeat:
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_REPEAT);
		}
		
		glBindTexture(target, 0);
	}
	
	/**
	 * <p>
	 * Set the anisotropic filtering level of the texture.
	 * </p>
	 *
	 * @param level The anisotropic filtering level.
	 */
	public final void setAnisoLevel(float level)
	{
		if(GL.getCapabilities().GL_EXT_texture_filter_anisotropic)
		{
			glBindTexture(target, id);
			
			level = MathUtils.clamp(level, 0, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			
			if(level > 0)
			{
				// Enable anisotropic filtering
				glTexParameterf(target, GL_TEXTURE_MAX_ANISOTROPY_EXT, level);
			}
			else
			{
				// Disable anisotropic filtering
				glTexParameterf(target, GL_TEXTURE_BASE_LEVEL, 0);
				glTexParameterf(target, GL_TEXTURE_MAX_LEVEL, 0);
			}
			
			glBindTexture(target, 0);
		}
		else
		{
			Logger.logWarning("Anisotropic filtering is not supported on this system");
		}
	}
	
	/**
	 * <p>
	 * Get the width of the texture in pixels.
	 * </p>
	 *
	 * @return The width of the texture in pixels.
	 */
	public final int getWidth()
	{
		return asset.bufferedImage.getWidth();
	}
	
	/**
	 * <p>
	 * Get the height of the texture in pixels.
	 * </p>
	 *
	 * @return The height of the texture in pixels.
	 */
	public final int getHeight()
	{
		return asset.bufferedImage.getHeight();
	}
	
	public final int getNativeId()
	{
		return id;
	}
}
