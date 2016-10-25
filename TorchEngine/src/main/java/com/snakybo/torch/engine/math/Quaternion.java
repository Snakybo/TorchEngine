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
public class Quaternion
{
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Quaternion()
	{
		this(0, 0, 0, 1);
	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector4 v)
	{
		set(v);
	}
	
	public Quaternion(Quaternion v)
	{
		set(v);
	}
	
	public final void normalize()
	{
		float invNorm = (float)(1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
		
		x *= invNorm;
		y *= invNorm;
		z *= invNorm;
		w *= invNorm;
	}
	
	public final Quaternion normalized()
	{
		Quaternion result = new Quaternion(this);
		result.normalize();
		return result;
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
			throw new IndexOutOfBoundsException("Invalid Quaternion index: " + index);
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
			throw new IndexOutOfBoundsException("Invalid Quaternion index: " + index);
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
	
	public static float dot(Quaternion lhs, Quaternion rhs)
	{
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z + lhs.w * rhs.w;
	}
	
	public static Quaternion identity()
	{
		return new Quaternion(0, 0, 0, 1);
	}
}
