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

import com.snakybo.torch.util.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureInternal
{
	private TextureInternal()
	{
		throw new AssertionError();
	}
	
	public static void bind(Texture texture, int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			throw new IllegalArgumentException("The texture unit " + unit + " is out of bounds");
		}
		
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(texture.getTarget(), texture.getNativeId());
	}
	
	public static void unbind(Texture texture, int unit)
	{
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(texture.getTarget(), 0);
	}
}
