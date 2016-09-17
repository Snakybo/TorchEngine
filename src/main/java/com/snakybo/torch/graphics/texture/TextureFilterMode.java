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

/**
 * <p>
 * All available filtering modes for a texture.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public enum TextureFilterMode
{
	/**
	 * <p>
	 * Point filtering (GL_NEAREST / no filtering).
	 * </p>
	 */
	Point,
	
	/**
	 * <p>
	 * Bilinear filtering (GL_LINEAR / averaged samples).
	 * </p>
	 */
	Bilinear,
	
	/**
	 * <p>
	 * Trilinear filtering (GL_LINEAR_MIPMAP_LINEAR / averaged samples and blending between mipmap levels).
	 * </p>
	 */
	Trilinear
}
