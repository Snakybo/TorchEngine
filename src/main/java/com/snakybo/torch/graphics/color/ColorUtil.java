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

package com.snakybo.torch.graphics.color;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ColorUtil
{
	private ColorUtil()
	{
		throw new AssertionError();
	}
	
	public static int colorToRGB(Color color)
	{
		int r = Math.round(255 * color.getRed());
		int g = Math.round(255 * color.getGreen());
		int b = Math.round(255 * color.getBlue());
		
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b = b & 0x000000FF;
		
		return 0xFF000000 | r | g | b;
	}
	
	public static int colorToARGB(Color color)
	{
		int r = Math.round(255 * color.getRed());
		int g = Math.round(255 * color.getGreen());
		int b = Math.round(255 * color.getBlue());
		int a = Math.round(255 * color.getAlpha());
		
		a = (a << 25) & 0xFF000000;
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b = b & 0x000000FF;
		
		return a | r | g | b;
	}
	
	public static Color RGBToColor(int rgb)
	{
		int r32 = (rgb >> 16) & 0xFF;
		int g32 = (rgb >> 8) & 0xFF;
		int b32 = (rgb) & 0xFF;
		int a32 = (rgb >> 24) & 0xFF;
		
		float r = (1f / 255) * r32;
		float g = (1f / 255) * g32;
		float b = (1f / 255) * b32;
		float a = (1f / 255) * a32;
		
		return new Color(r, g, b, a);
	}
}
