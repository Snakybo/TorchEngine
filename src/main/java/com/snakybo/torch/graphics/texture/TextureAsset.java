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
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
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
	int target;
	
	TextureAsset(int target, String name, BufferedImage bufferedImage)
	{
		super(name);
		
		this.target = target;
		this.bufferedImage = bufferedImage;
		
		this.id = BufferUtils.createIntBuffer(1);
		
		glGenTextures(id);
		glBindTexture(target, id.get(0));
		
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		ByteBuffer data = com.snakybo.torch.util.BufferUtils.toByteBuffer(bufferedImage);
		
		glTexImage2D(target, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		glGenerateMipmap(target);
		glBindTexture(target, 0);
		
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
}
