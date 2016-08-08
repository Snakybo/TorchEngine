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
import com.snakybo.torch.interfaces.IDestroyable;
import com.snakybo.torch.util.FileUtils;

import java.nio.file.NoSuchFileException;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Cubemap implements IDestroyable
{
	private CubemapAsset asset;
	
	public Cubemap(String front, String back, String left, String right, String top, String bottom)
	{
		String name = front + back + left + right + top + bottom;
		
		if(CubemapAsset.all.containsKey(name))
		{
			asset = CubemapAsset.all.get(name);
		}
		else
		{
			try
			{
				asset = new CubemapAsset(name);
				asset.init(FileUtils.getBufferedImage(front),
						FileUtils.getBufferedImage(back),
						FileUtils.getBufferedImage(left),
						FileUtils.getBufferedImage(right),
						FileUtils.getBufferedImage(top),
						FileUtils.getBufferedImage(bottom));
			}
			catch(NoSuchFileException e)
			{
				Logger.logError(e.toString(), e);
			}
		}
	}
	
	@Override
	public void destroy()
	{
		asset.destroy();
	}
	
	/**
	 * Bind the cube map to the specified unit
	 * @param unit The unit to bind to
	 */
	public final void bind(int unit)
	{
		if(unit < 0 || unit >= 32)
		{
			throw new IllegalArgumentException("The texture unit " + unit + " is out of bounds");
		}

		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_CUBE_MAP, asset.id.get(0));
	}
}
