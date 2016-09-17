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

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

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
		super(GL_TEXTURE_2D, width, height);
	}
	
	Texture2D(String name, BufferedImage bufferedImage)
	{
		super(GL_TEXTURE_2D, name, bufferedImage);
	}
	
	Texture2D(TextureAsset asset)
	{
		super(asset);
	}
	
	// TODO: Epand Texture2D class
}
