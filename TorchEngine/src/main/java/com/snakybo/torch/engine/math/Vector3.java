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
public class Vector3
{
	public float x;
	public float y;
	public float z;
	
	public Vector3()
	{
		this(0);
	}
	
	public Vector3(float xyz)
	{
		this(xyz, xyz, xyz);
	}
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector2 v)
	{
		set(v);
	}
	
	public Vector3(Vector3 v)
	{
		set(v);
	}
	
	public Vector3(Vector4 v)
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
		builder.append(", ");
		builder.append(z);
		
		return builder.toString();
	}
	
	@Override
	public final int hashCode()
	{
		int hash = 32;
		
		hash = 32 * hash + Float.floatToIntBits(x);
		hash = 32 * hash + Float.floatToIntBits(y);
		hash = 32 * hash + Float.floatToIntBits(z);
		
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
		
		Vector3 v = (Vector3)o;
		
		return  Float.floatToIntBits(x) == Float.floatToIntBits(v.x) &&
				Float.floatToIntBits(y) == Float.floatToIntBits(v.y) &&
				Float.floatToIntBits(z) == Float.floatToIntBits(v.z);
	}
	
	public final float length()
	{
		return (float)Math.sqrt(length2());
	}
	
	public final float length2()
	{
		return x * x + y * y + z * z;
	}
	
	public final void normalize()
	{
		float invLength = 1.0f / length();
		
		x *= invLength;
		y *= invLength;
		z *= invLength;
	}
	
	public final Vector3 normalized()
	{
		Vector3 result = new Vector3(this);
		result.normalize();
		return result;
	}
	
	public final Vector3 add(Vector2 v)
	{
		x += v.x;
		y += v.y;
		return this;
	}
	
	public final Vector3 add(Vector3 v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public final Vector3 add(Vector4 v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public final Vector3 sub(Vector2 v)
	{
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public final Vector3 sub(Vector3 v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public final Vector3 sub(Vector4 v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public final Vector3 mul(float v)
	{
		x *= v;
		y *= v;
		z *= v;
		return this;
	}
	
	public final Vector3 div(float v)
	{
		x /= v;
		y /= v;
		z /= v;
		return this;
	}
	
	public final void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = 0;
	}
	
	public final void set(Vector3 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public final void set(Vector4 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
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
		case 2:
			z = v;
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector3 index: " + index);
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
		case 2:
			return z;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector3 index: " + index);
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
		buffer.put(index + 2, z);
		return buffer;
	}
	
	public static Vector3 scale(Vector3 lhs, Vector2 rhs)
	{
		return new Vector3(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z);
	}
	
	public static Vector3 scale(Vector3 lhs, Vector3 rhs)
	{
		return new Vector3(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z);
	}
	
	public static Vector3 scale(Vector3 lhs, Vector4 rhs)
	{
		return new Vector3(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z);
	}
	
	public static Vector3 cross(Vector3 lhs, Vector3 rhs)
	{
		return new Vector3(
				lhs.y * rhs.z - lhs.z * rhs.y,
				lhs.z * rhs.x - lhs.x * rhs.z,
				lhs.x * rhs.y - lhs.y * rhs.x);
	}
	
	public static float distance(Vector3 lhs, Vector3 rhs)
	{
		float dx = rhs.x - lhs.x;
		float dy = rhs.y - lhs.y;
		float dz = rhs.z - lhs.z;
		return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public static float distance2(Vector3 lhs, Vector3 rhs)
	{
		float dx = rhs.x - lhs.x;
		float dy = rhs.y - lhs.y;
		float dz = rhs.z - lhs.z;
		return dx * dx + dy * dy + dz * dz;
	}
	
	public static float dot(Vector3 lhs, Vector3 rhs)
	{
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z;
	}
	
	public static float angle(Vector3 lhs, Vector3 rhs)
	{
		return (float)(Math.acos(Mathf.clamp(Vector3.dot(lhs.normalized(), rhs.normalized()), -1, 1)) * 57.29578);
	}
	
	public static Vector3 lerp(Vector3 lhs, Vector3 rhs, float t)
	{
		Vector3 result = new Vector3();
		
		result.x = lhs.x + (rhs.x - lhs.x) * t;
		result.y = lhs.y + (rhs.y - lhs.y) * t;
		result.z = lhs.z + (rhs.z - lhs.z) * t;
		
		return result;
	}
	
	public static Vector3 left()
	{
		return new Vector3(-1, 0, 0);
	}
	
	public static Vector3 right()
	{
		return new Vector3(1, 0, 0);
	}
	
	public static Vector3 up()
	{
		return new Vector3(0, 1, 0);
	}
	
	public static Vector3 down()
	{
		return new Vector3(0, -1, 0);
	}
	
	public static Vector3 forward()
	{
		return new Vector3(0, 0, 1);
	}
	
	public static Vector3 back()
	{
		return new Vector3(0, 0, -1);
	}
	
	public static Vector3 one()
	{
		return new Vector3(1, 1, 1);
	}
	
	public static Vector3 zero()
	{
		return new Vector3(0, 0, 0);
	}
}
