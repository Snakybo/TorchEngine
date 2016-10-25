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

import java.util.Arrays;

/**
 * @author Snakybo
 * @since 1.0
 */
public class Matrix3x3
{
	public float m00;
	public float m10;
	public float m20;
	
	public float m01;
	public float m11;
	public float m21;
	
	public float m02;
	public float m12;
	public float m22;
	
	@Override
	public final int hashCode()
	{
		return  Arrays.hashCode(getColumn(0)) ^
				Arrays.hashCode(getColumn(1)) << 2 ^
				Arrays.hashCode(getColumn(2)) >> 2;
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
		
		Matrix3x3 v = (Matrix3x3)o;
		
		return  Arrays.equals(getColumn(0), v.getColumn(0)) &&
				Arrays.equals(getColumn(1), v.getColumn(1)) &&
				Arrays.equals(getColumn(2), v.getColumn(2));
	}
	
	@Override
	public final String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(m00).append("\t").append(m01).append("\t").append(m02).append("\n");
		builder.append(m10).append("\t").append(m11).append("\t").append(m12).append("\n");
		builder.append(m20).append("\t").append(m21).append("\t").append(m22);
		
		return builder.toString();
	}
	
	public final void set(int x, int y, float v)
	{
		set(x + y * 3, v);
	}
	
	public final void set(int index, float v)
	{
		switch(index)
		{
		case 0:
			m00 = v;
			break;
		case 1:
			m10 = v;
			break;
		case 2:
			m20 = v;
			break;
		case 3:
			m01 = v;
			break;
		case 4:
			m11 = v;
			break;
		case 5:
			m21 = v;
			break;
		case 6:
			m02 = v;
			break;
		case 7:
			m12 = v;
			break;
		case 8:
			m22 = v;
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid Matrix3x3 index: " + index);
		}
	}
	
	public final float get(int x, int y)
	{
		return get(x + y * 3);
	}
	
	public final float get(int index)
	{
		switch(index)
		{
		case 0:
			return m00;
		case 1:
			return m10;
		case 2:
			return m20;
		case 3:
			return m01;
		case 4:
			return m11;
		case 5:
			return m21;
		case 6:
			return m02;
		case 7:
			return m12;
		case 8:
			return m22;
		default:
			throw new IndexOutOfBoundsException("Invalid Matrix3x3 index: " + index);
		}
	}
	
	public final void setColumn(int i, float[] v)
	{
		set(0, i, v.length > 0 ? v[0] : 0);
		set(1, i, v.length > 1 ? v[1] : 0);
		set(2, i, v.length > 2 ? v[2] : 0);
	}
	
	public final void setRow(int i, float[] v)
	{
		set(i, 0, v.length > 0 ? v[0] : 0);
		set(i, 1, v.length > 1 ? v[1] : 0);
		set(i, 2, v.length > 2 ? v[2] : 0);
	}
	
	public final float[] getColumn(int i)
	{
		return new float[] { get(0, i), get(1, i), get(2, i) };
	}
	
	public final float[] getRow(int i)
	{
		return new float[] { get(i, 0), get(i, 1), get(i, 2) };
	}
	
	public static Matrix3x3 zero()
	{
		return new Matrix3x3();
	}
	
	public static Matrix3x3 identity()
	{
		return new Matrix3x3()
		{
			{
				this.m00 = 1f;
				this.m11 = 1f;
				this.m22 = 1f;
			}
		};
	}
}
