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

package com.snakybo.torch.engine.math;

import java.nio.FloatBuffer;

/**
 * @author Snakybo
 * @since 1.0
 */
public class Vector2
{
	public float x;
	public float y;
	
	public Vector2()
	{
		this(0, 0);
	}
	
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 v)
	{
		set(v);
	}
	
	public Vector2(Vector3 v)
	{
		set(v);
	}
	
	public Vector2(Vector4 v)
	{
		set(v);
	}
	
	@Override
	public final String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(x);
		builder.append(", ");
		builder.append(y);
		
		return builder.toString();
	}
	
	@Override
	public final int hashCode()
	{
		int hash = 45;
		
		hash = 45 * hash + Float.floatToIntBits(x);
		hash = 45 * hash + Float.floatToIntBits(y);
		
		return hash;
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		
		if(o == null || o.getClass() != getClass())
		{
			return false;
		}
		
		Vector2 v = (Vector2)o;
		
		return  Float.floatToIntBits(x) == Float.floatToIntBits(v.x) &&
				Float.floatToIntBits(y) == Float.floatToIntBits(v.y);
	}
	
	public final float length()
	{
		return (float)Math.sqrt(length2());
	}
	
	public final float length2()
	{
		return x * x + y * y;
	}
	
	public final void normalize()
	{
		float invLength = 1.0f / length();
		
		x *= invLength;
		y *= invLength;
	}
	
	public final Vector2 normalized()
	{
		Vector2 result = new Vector2(this);
		result.normalize();
		return result;
	}
	
	public final Vector2 add(Vector2 v)
	{
		x += v.x;
		y += v.y;
		return this;
	}
	
	public final Vector2 add(Vector3 v)
	{
		x += v.x;
		y += v.y;
		return this;
	}
	
	public final Vector2 add(Vector4 v)
	{
		x += v.x;
		y += v.y;
		return this;
	}
	
	public final Vector2 sub(Vector2 v)
	{
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public final Vector2 sub(Vector3 v)
	{
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public final Vector2 sub(Vector4 v)
	{
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public final Vector2 mul(float v)
	{
		x *= v;
		y *= v;
		return this;
	}
	
	public final Vector2 div(float v)
	{
		x /= v;
		y /= v;
		return this;
	}
	
	public final void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public final void set(Vector3 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public final void set(Vector4 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public final void set(int index, float v)
	{
		switch(index)
		{
		case 0:
			x = v;
			break;
		case 1:
			y = v;
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector2 index: " + index);
		}
	}
	
	public final float get(int index)
	{
		switch(index)
		{
		case 0:
			return x;
		case 1:
			return y;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector2 index: " + index);
		}
	}
	
	public final FloatBuffer get(FloatBuffer buffer)
	{
		return get(buffer.position(), buffer);
	}
	
	public final FloatBuffer get(int index, FloatBuffer buffer)
	{
		buffer.put(index,     x);
		buffer.put(index + 1, y);
		return buffer;
	}
	
	public static Vector2 scale(Vector2 lhs, Vector2 rhs)
	{
		return new Vector2(lhs.x * rhs.x, lhs.y * rhs.y);
	}
	
	public static Vector2 scale(Vector2 lhs, Vector3 rhs)
	{
		return new Vector2(lhs.x * rhs.x, lhs.y * rhs.y);
	}
	
	public static Vector2 scale(Vector2 lhs, Vector4 rhs)
	{
		return new Vector2(lhs.x * rhs.x, lhs.y * rhs.y);
	}
	
	public static Vector2 lerp(Vector2 lhs, Vector2 rhs, float t)
	{
		Vector2 result = new Vector2();
		
		result.x = lhs.x + (rhs.x - lhs.x) * t;
		result.y = lhs.y + (rhs.y - lhs.y) * t;
		return result;
	}
	
	public static float dot(Vector2 lhs, Vector2 rhs)
	{
		return lhs.x * rhs.x + lhs.y * rhs.y;
	}
	
	public static float angle(Vector2 lhs, Vector2 rhs)
	{
		return (float)(Math.acos(Mathf.clamp(Vector2.dot(lhs.normalized(), rhs.normalized()), -1, 1)) * 57.29578);
	}
	
	public static float distance(Vector2 lhs, Vector2 rhs)
	{
		float dx = lhs.x - rhs.x;
		float dy = lhs.y - rhs.y;
		
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
	
	public static Vector2 left()
	{
		return new Vector2(-1, 0);
	}
	
	public static Vector2 right()
	{
		return new Vector2(1, 0);
	}
	
	public static Vector2 up()
	{
		return new Vector2(0, 1);
	}
	
	public static Vector2 down()
	{
		return new Vector2(0, -1);
	}
	
	public static Vector2 one()
	{
		return new Vector2(1, 1);
	}
	
	public static Vector2 zero()
	{
		return new Vector2(0, 0);
	}
}
