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

import com.snakybo.torch.util.MathUtils;
import com.snakybo.torch.util.ToByteBuffer;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_BASE_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Snakybo
 * @since 1.0
 */
final class TextureAsset
{
	static Map<String, TextureAsset> all = new HashMap<>();
	
	String name;
	
	IntBuffer id;
	
	BufferedImage bufferedImage;
	
	TextureAsset(String name, BufferedImage bufferedImage, int size)
	{
		this.name = name;
		this.bufferedImage = bufferedImage;
		
		if(size > 0)
		{
			this.id = BufferUtils.createIntBuffer(size);
			glGenTextures(id);
		}
		
		if(name != null && !name.isEmpty())
		{
			all.put(name, this);
		}
	}
	
	final void destroy()
	{
		glDeleteTextures(id);
	}
	
	final void init(int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
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
		
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, format, GL_UNSIGNED_BYTE, ToByteBuffer.convert(bufferedImage));
		
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
}