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
public class Vector4
{
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector4()
	{
		this(0, 0, 0, 0);
	}
	
	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(Vector2 v)
	{
		set(v);
	}
	
	public Vector4(Vector3 v)
	{
		set(v);
	}
	
	public Vector4(Vector4 v)
	{
		set(v);
	}
	
	public Vector4(Quaternion v)
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
		builder.append(", ");
		builder.append(w);
		
		return builder.toString();
	}
	
	@Override
	public final int hashCode()
	{
		int hash = 64;
		
		hash = 64 * hash + Float.floatToIntBits(x);
		hash = 64 * hash + Float.floatToIntBits(y);
		hash = 64 * hash + Float.floatToIntBits(z);
		hash = 64 * hash + Float.floatToIntBits(w);
		
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
		
		Vector4 v = (Vector4)o;
		
		return  Float.floatToIntBits(x) == Float.floatToIntBits(v.x) &&
				Float.floatToIntBits(y) == Float.floatToIntBits(v.y) &&
				Float.floatToIntBits(z) == Float.floatToIntBits(v.z) &&
				Float.floatToIntBits(w) == Float.floatToIntBits(v.w);
	}
	
	public final float length()
	{
		return (float)Math.sqrt(length2());
	}
	
	public final float length2()
	{
		return x * x + y * y + z * z + w * w;
	}
	
	public final void normalize()
	{
		float invLength = 1.0f / length();
		
		x *= invLength;
		y *= invLength;
		z *= invLength;
		w *= invLength;
	}
	
	public final Vector4 normalized()
	{
		Vector4 result = new Vector4(this);
		result.normalize();
		return result;
	}
	
	public final Vector4 add(Vector2 v)
	{
		x += v.x;
		y += v.y;
		return this;
	}
	
	public final Vector4 add(Vector3 v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public final Vector4 add(Vector4 v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		w += v.w;
		return this;
	}
	
	public final Vector4 add(Quaternion v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		w += v.w;
		return this;
	}
	
	public final Vector4 sub(Vector2 v)
	{
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public final Vector4 sub(Vector3 v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public final Vector4 sub(Vector4 v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		w -= v.w;
		return this;
	}
	
	public final Vector4 sub(Quaternion v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		w -= v.w;
		return this;
	}
	
	public final Vector4 mul(float v)
	{
		x *= v;
		y *= v;
		z *= v;
		w *= v;
		return this;
	}
	
	public final Vector4 div(float v)
	{
		x /= v;
		y /= v;
		z /= v;
		w /= v;
		return this;
	}
	
	public final void set(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = 0;
		this.w = 0;
	}
	
	public final void set(Vector3 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = 0;
	}
	
	public final void set(Vector4 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}
	
	public final void set(Quaternion v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
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
		case 3:
			w = v;
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector4 index: " + index);
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
		case 3:
			return w;
		default:
			throw new IndexOutOfBoundsException("Invalid Vector4 index: " + index);
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
		buffer.put(index + 3, w);
		return buffer;
	}
	
	public static Vector4 scale(Vector4 lhs, Vector2 rhs)
	{
		return new Vector4(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z, lhs.w);
	}
	
	public static Vector4 scale(Vector4 lhs, Vector3 rhs)
	{
		return new Vector4(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z, lhs.w);
	}
	
	public static Vector4 scale(Vector4 lhs, Vector4 rhs)
	{
		return new Vector4(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z, lhs.w * rhs.w);
	}
	
	public static Vector4 scale(Vector4 lhs, Quaternion rhs)
	{
		return new Vector4(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z, lhs.w * rhs.w);
	}
	
	public static float distance(Vector4 lhs, Vector4 rhs)
	{
		float dx = rhs.x - lhs.x;
		float dy = rhs.y - lhs.y;
		float dz = rhs.z - lhs.z;
		float dw = rhs.w - lhs.w;
		return (float)Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
	}
	
	public static float distance2(Vector4 lhs, Vector4 rhs)
	{
		float dx = rhs.x - lhs.x;
		float dy = rhs.y - lhs.y;
		float dz = rhs.z - lhs.z;
		float dw = rhs.w - lhs.w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public static float dot(Vector4 lhs, Vector4 rhs)
	{
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z + lhs.w * rhs.w;
	}
	
	public static Vector4 lerp(Vector4 lhs, Vector4 rhs, float t)
	{
		Vector4 result = new Vector4();
		
		result.x = lhs.x + (rhs.x - lhs.x) * t;
		result.y = lhs.y + (rhs.y - lhs.y) * t;
		result.z = lhs.z + (rhs.z - lhs.z) * t;
		result.w = lhs.w + (rhs.w - lhs.w) * t;
		
		return result;
	}
	
	public static Vector4 one()
	{
		return new Vector4(1, 1, 1, 1);
	}
	
	public static Vector4 zero()
	{
		return new Vector4(0, 0, 0, 0);
	}
}
