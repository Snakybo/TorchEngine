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

package com.snakybo.torch.input.cursor;

import com.snakybo.torch.graphics.texture.Texture;
import org.joml.Vector2f;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Holds all cursor shapes created by {@link Cursor#setShape(Texture, Vector2f)}.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
class CursorShape
{
	private static Set<CursorShape> cursorShapeCache = new HashSet<>();
	
	private Texture texture;
	private Vector2f hot;
	
	private long cursor;
	
	private CursorShape(Texture texture, Vector2f hot, long cursor)
	{
		this.texture = texture;
		this.hot = hot;
		this.cursor = cursor;
	}
	
	private boolean equals(Texture texture, Vector2f hot)
	{
		return texture.equals(this.texture) && hot.equals(this.hot);
	}
	
	/**
	 * Check whether or not the specified cursor has been registered already.
	 * @param texture The image of the cursor.
	 * @param hot The hotzone of the cursor.
	 * @return Whether or not the specified cursor has already been registered.
	 */
	public static boolean hasCursor(Texture texture, Vector2f hot)
	{
		for(CursorShape shape : cursorShapeCache)
		{
			if(shape.equals(texture, hot))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Add a new cursor shape to the cache.
	 * @param texture The image of the cursor.
	 * @param hot The hotzone of the cursor.
	 * @param cursor The GLFW generated ID of the cursor shape.
	 */
	public static void addCursor(Texture texture, Vector2f hot, long cursor)
	{
		if(!hasCursor(texture, hot))
		{
			cursorShapeCache.add(new CursorShape(texture, hot, cursor));
		}
	}
	
	/**
	 * Get the cursor ID from the specified texture and hot.
	 * @param texture The image of the cursor.
	 * @param hot The hotzone of the cursor.
	 * @return The GLFW generated ID of the cursor shape.
	 */
	public static long getCursor(Texture texture, Vector2f hot)
	{
		for(CursorShape shape : cursorShapeCache)
		{
			if(shape.equals(texture, hot))
			{
				return shape.cursor;
			}
		}
		
		return 0L;
	}
}
