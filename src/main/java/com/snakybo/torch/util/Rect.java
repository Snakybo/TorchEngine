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

package com.snakybo.torch.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * <p>
 * A 2D rectangle defined by X, Y, width and height.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Rect
{
	private float x;
	private float y;
	
	private float width;
	private float height;
	
	/**
	 * <p>
	 * Create a new rect with a position of (0, 0) and a size of (0, 0).
	 * </p>
	 */
	public Rect()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * <p>
	 * Copy another Rect.
	 * </p>
	 *
	 * @param rect The source Rect.
	 */
	public Rect(Rect rect)
	{
		this.x = rect.x;
		this.y = rect.y;
		this.width = rect.width;
		this.height = rect.height;
	}
	
	/**
	 * <p>
	 * Create a new Rect with the specified {@code position} and {@code size}.
	 * </p>
	 *
	 * @param position The position.
	 * @param size The size.
	 */
	public Rect(Vector2f position, Vector2f size)
	{
		this(position.x, position.y, size.x, size.y);
	}
	
	/**
	 * <p>
	 * Create a new Rect with the specified {@code x}, {@code y}, {@code width} and {@code height}.
	 * </p>
	 *
	 * @param x The X position.
	 * @param y The Y position.
	 * @param width The width.
	 * @param height The height.
	 */
	public Rect(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public final String toString()
	{
		return "x=" + x + " y=" + y + " w=" + width + " h=" + height;
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		Rect rect = (Rect)o;
		
		if(Float.compare(rect.x, x) != 0)
		{
			return false;
		}
		
		if(Float.compare(rect.y, y) != 0)
		{
			return false;
		}
		
		if(Float.compare(rect.width, width) != 0)
		{
			return false;
		}
		
		return Float.compare(rect.height, height) == 0;
	}
	
	@Override
	public final int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
		result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
		
		return result;
	}
	
	/**
	 * <p>
	 * Check if both the X and Y components of the specified {@code point} are within the bounds
	 * of this Rect
	 * </p>
	 *
	 *
	 * @param point The point to test.
	 * @return {@code true} if the {@code point} is within the bounds of this Rect.
	 */
	public final boolean contains(Vector2f point)
	{
		return point.x > getXMin() && point.x < getXMax() && point.y > getYMin() && point.y < getYMax();
	}
	
	/**
	 * <p>
	 * Check if both the {@code X} and {@code Y} components of the specified {@code point} are within the bounds
	 * of this Rect
	 * </p>
	 *
	 *
	 * @param point The point to test.
	 * @return {@code true} if the {@code point} is within the bounds of this Rect.
	 */
	public final boolean contains(Vector3f point)
	{
		return point.x > getXMin() && point.x < getXMax() && point.y > getYMin() && point.y < getYMax();
	}
	
	/**
	 * <p>
	 * Check if the other Rect intersects this one.
	 * </p>
	 *
	 * @param rect The Rect to test.
	 * @return {@code true} if the other Rect intersects this one.
	 */
	public final boolean intersects(Rect rect)
	{
		return rect.getXMax() > getXMin() && rect.getXMin() < getXMax() && rect.getYMax() > getYMin() && rect.getYMin() < getYMax();
	}
	
	/**
	 * <p>
	 * Set the position.
	 * </p>
	 *
	 * <p>
	 * This is essentially the same as calling {@link #setX(float)} and {@link #setY(float)}.
	 * </p>
	 *
	 * @param position The new position.
	 */
	public final void setPosition(Vector2f position)
	{
		this.x = position.x;
		this.y = position.y;
	}
	
	/**
	 * <p>
	 * Set the size.
	 * </p>
	 *
	 * <p>
	 * This is essentially the same as calling {@link #setWidth(float)} and {@link #setHeight(float)}.
	 * </p>
	 *
	 * @param size The new size.
	 */
	public final void setSize(Vector2f size)
	{
		this.width = size.x;
		this.height = size.y;
	}
	
	/**
	 * <p>
	 * Set the center position, the Rect is moved so that the center is at the specified position.
	 * </p>
	 *
	 * @param center The new center position.
	 */
	public final void setCenter(Vector2f center)
	{
		this.x = center.x - width / 2f;
		this.y = center.y - height / 2f;
	}
	
	/**
	 * <p>
	 * Set the X position, this does not resize the Rect.
	 * </p>
	 *
	 * @param x The new X position.
	 */
	public final void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * <p>
	 * Set the Y position, this does not resize the Rect.
	 * </p>
	 *
	 * @param y The new Y position.
	 */
	public final void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * <p>
	 * Set the width.
	 * </p>
	 *
	 * @param width The new width.
	 */
	public final void setWidth(float width)
	{
		this.width = width;
	}
	
	/**
	 * <p>
	 * Set the height.
	 * </p>
	 *
	 * @param height The new height.
	 */
	public final void setHeight(float height)
	{
		this.height = height;
	}
	
	/**
	 * <p>
	 * Set the minimum X position, this resizes the rect.
	 * </p>
	 *
	 * @param xMin The new minimum X position.
	 */
	public final void setXMin(float xMin)
	{
		float xMax = getXMax();
		this.x = xMin;
		this.width = xMax - x;
	}
	
	/**
	 * <p>
	 * Set the minimum Y position, this resizes the rect.
	 * </p>
	 *
	 * @param yMin The new minimum Y position.
	 */
	public final void setYMin(float yMin)
	{
		float yMax = getYMax();
		this.y = yMin;
		this.height = yMax - y;
	}
	
	/**
	 * <p>
	 * Set the maximum X position, this resizes the rect.
	 * </p>
	 *
	 * @param xMax The new maximum X position.
	 */
	public final void setXMax(float xMax)
	{
		this.width = xMax - x;
	}
	
	/**
	 * <p>
	 * Set the maximum Y position, this resizes the rect.
	 * </p>
	 *
	 * @param yMax The new maximum Y position.
	 */
	public final void setYMax(float yMax)
	{
		this.height = yMax - y;
	}
	
	/**
	 * <p>
	 * Get the position.
	 * </p>
	 *
	 * <p>
	 * This is essentially the same as calling {@link #getX()} and {@link #getY()}.
	 * </p>
	 *
	 * @return The position.
	 */
	public final Vector2f getPosition()
	{
		return new Vector2f(x, y);
	}
	
	/**
	 * <p>
	 * Get the size.
	 * </p>
	 *
	 * <p>
	 * This is essentially the same as calling {@link #getWidth()} and {@link #getHeight()}.
	 * </p>
	 *
	 * @return The size.
	 */
	public final Vector2f getSize()
	{
		return new Vector2f(width, height);
	}
	
	/**
	 * <p>
	 * Get the center coordinate.
	 * </p>
	 *
	 * @return The center.
	 */
	public final Vector2f getCenter()
	{
		return new Vector2f(x - width / 2f, y - height / 2f);
	}
	
	/**
	 * <p>
	 * Get the X position.
	 * </p>
	 *
	 * @return The X position.
	 */
	public final float getX()
	{
		return x;
	}
	
	/**
	 * <p>
	 * Get the Y position.
	 * </p>
	 *
	 * @return The Y position.
	 */
	public final float getY()
	{
		return y;
	}
	
	/**
	 * <p>
	 * Get the width.
	 * </p>
	 *
	 * @return The width.
	 */
	public final float getWidth()
	{
		return width;
	}
	
	/**
	 * <p>
	 * Get the height.
	 * </p>
	 *
	 * @return The height.
	 */
	public final float getHeight()
	{
		return height;
	}
	
	/**
	 * <p>
	 * Get the minimum X position.
	 * </p>
	 *
	 * @return The minimum X position.
	 */
	public final float getXMin()
	{
		return x;
	}
	
	/**
	 * <p>
	 * Get the maximum X position.
	 * </p>
	 *
	 * @return The maximum X position.
	 */
	public final float getXMax()
	{
		return x + width;
	}
	
	/**
	 * <p>
	 * Get the minimum Y position.
	 * </p>
	 *
	 * @return The minimum Y position.
	 */
	public final float getYMin()
	{
		return y;
	}
	
	/**
	 * <p>
	 * Get the maximum Y position.
	 * </p>
	 *
	 * @return The maximum Y position.
	 */
	public final float getYMax()
	{
		return y + height;
	}
}
