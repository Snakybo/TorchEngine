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

import com.snakybo.torch.asset.AssetData;
import com.snakybo.torch.util.MathUtils;
import com.snakybo.torch.util.ToByteBuffer;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
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
import static org.lwjgl.opengl.GL11.glGetFloat;
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
final class TextureAsset extends AssetData
{
	static Map<String, TextureAsset> all = new HashMap<>();
	
	BufferedImage bufferedImage;
	
	IntBuffer id;
	
	int type;
	
	TextureAsset(String name, BufferedImage bufferedImage, int size)
	{
		super(name);
		
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
	
	@Override
	public final void destroy()
	{
		if(name != null && !name.isEmpty())
		{
			all.remove(name);
		}
		
		glDeleteTextures(id);
	}
	
	final void init(int type, int filters, float anisoLevel, int internalFormat, int format, boolean clamp)
	{
		this.type = type;
		
		glBindTexture(type, id.get(0));
		
		// Filters
		assert( filters == GL_LINEAR || filters == GL_NEAREST ||
				filters == GL_LINEAR_MIPMAP_LINEAR || filters == GL_NEAREST_MIPMAP_NEAREST ||
				filters == GL_LINEAR_MIPMAP_NEAREST || filters == GL_NEAREST_MIPMAP_LINEAR);
		
		glTexParameterf(type, GL_TEXTURE_MIN_FILTER, filters);
		glTexParameterf(type, GL_TEXTURE_MAG_FILTER, filters);
		
		// Clamp
		if(clamp)
		{
			glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(type, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		}
		
		glTexImage2D(type, 0, internalFormat, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, format, GL_UNSIGNED_BYTE, ToByteBuffer.convert(bufferedImage));
		
		// Mipmaps
		boolean hasMipmaps = filters == GL_LINEAR_MIPMAP_LINEAR || filters == GL_LINEAR_MIPMAP_NEAREST ||
				filters == GL_NEAREST_MIPMAP_LINEAR || filters == GL_NEAREST_MIPMAP_NEAREST;
		
		if(hasMipmaps)
		{
			glGenerateMipmap(type);
			
			anisoLevel = MathUtils.clamp(anisoLevel, 0, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			glTexParameterf(type, GL_TEXTURE_MAX_ANISOTROPY_EXT, anisoLevel);
		}
		else
		{
			glTexParameterf(type, GL_TEXTURE_BASE_LEVEL, 0);
			glTexParameterf(type, GL_TEXTURE_MAX_LEVEL, 0);
		}
	}
}
