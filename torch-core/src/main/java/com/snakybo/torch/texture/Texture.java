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

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.interfaces.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Texture implements IDestroyable
{
	protected IntBuffer id;
	
	private Bitmap bitmap;
	
	public Texture(Bitmap bitmap, int size)
	{
		this.bitmap = bitmap;
		this.id = BufferUtils.createIntBuffer(size);
		
		glGenTextures(id);
	}
	
	/**
	 * Bind the texture to the specified unit
	 * @param unit The unit to bind to
	 */
	public abstract void bind(int unit);

	@Override
	public void destroy()
	{
		glDeleteTextures(id);
	}
	
	/**
	 * Bind the cube map
	 */
	public final void bind()
	{
		bind(0);
	}
	
	public int getWidth()
	{
		return bitmap.getWidth();
	}
	
	public int getHeight()
	{
		return bitmap.getHeight();
	}
}
