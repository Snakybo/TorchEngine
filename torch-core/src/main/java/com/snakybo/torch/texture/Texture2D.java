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

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
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

import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.util.MathUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Texture2D extends Texture
{
	public Texture2D(Bitmap bitmap)
	{
		this(bitmap, GL_LINEAR_MIPMAP_LINEAR);
	}
	
	public Texture2D(Bitmap bitmap, int filters)
	{
		this(bitmap, filters, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
	}
	
	public Texture2D(Bitmap bitmap, int filters, float anisoLevel)
	{
		this(bitmap, filters, anisoLevel, GL_RGBA, GL_RGBA);
	}
	
	public Texture2D(Bitmap bitmap, int filters, float anisoLevel, int internalFormat, int format)
	{
		this(bitmap, filters, anisoLevel, internalFormat, format, false);
	}
	
	public Texture2D(Bitmap bitmap, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		super(bitmap, 1);
		
		glBindTexture(GL_TEXTURE_2D, id.get(0));
		
		// Filters
		assert( filters == GL_LINEAR || filters == GL_NEAREST ||
				filters == GL_LINEAR_MIPMAP_LINEAR || filters == GL_NEAREST_MIPMAP_NEAREST ||
				filters == GL_LINEAR_MIPMAP_NEAREST || filters == GL_NEAREST_MIPMAP_LINEAR);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filters);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filters);
		
		// Clamp
		if(clamp)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		}
		
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, getWidth(), getHeight(), 0, format, GL_UNSIGNED_BYTE, bitmap.getByteByffer());
		
		// Mipmaps
		boolean hasMipmaps = filters == GL_LINEAR_MIPMAP_LINEAR || filters == GL_LINEAR_MIPMAP_NEAREST ||
						  filters == GL_NEAREST_MIPMAP_LINEAR || filters == GL_NEAREST_MIPMAP_NEAREST;
		
		if(hasMipmaps)
		{
			glGenerateMipmap(GL_TEXTURE_2D);
			
			float maxAnisotropic = MathUtils.clamp(anisoLevel, 0, 8);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT, maxAnisotropic);
		}
		else
		{
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);
		}
	}

	@Override
	public void bind(int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			throw new IllegalArgumentException("The texture unit " + unit + " is out of bounds");
		}

		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, id.get(0));
	}
}
