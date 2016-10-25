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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Snakybo
 * @since 1.0
 */
public class Matrix4x4Test
{
	@Test
	public void testHashCode()
	{
		Matrix4x4 m1 = new Matrix4x4();
		m1.m00 = 1;
		m1.m10 = 1;
		m1.m20 = 1;
		m1.m30 = 1;
		
		Matrix4x4 m2 = new Matrix4x4();
		m2.m00 = 1;
		m2.m01 = 0;
		m2.m22 = 1;
		m2.m03 = 1;
		
		Matrix4x4 m3 = new Matrix4x4();
		m3.m00 = 1;
		m3.m10 = 1;
		m3.m20 = 1;
		m3.m01 = 1;
		
		Matrix4x4 m4 = new Matrix4x4();
		m4.m00 = 1;
		m4.m10 = 1;
		m4.m20 = 1;
		m4.m30 = 1;
		
		assertEquals(true, m1.hashCode() == m1.hashCode());
		assertEquals(false, m1.hashCode() == m2.hashCode());
		assertEquals(false, m1.hashCode() == m3.hashCode());
		assertEquals(true, m1.hashCode() == m4.hashCode());
	}
	
	@Test
	public void testEquals()
	{
		Matrix4x4 m1 = new Matrix4x4();
		m1.m00 = 1;
		m1.m10 = 1;
		m1.m20 = 1;
		m1.m30 = 1;
		
		Matrix4x4 m2 = new Matrix4x4();
		m2.m00 = 1;
		m2.m11 = 0;
		m2.m21 = 1;
		
		Matrix4x4 m3 = new Matrix4x4();
		m3.m00 = 1;
		m3.m10 = 1;
		m3.m20 = 1;
		m3.m01 = 1;
		
		Matrix4x4 m4 = new Matrix4x4();
		m4.m00 = 1;
		m4.m10 = 1;
		m4.m20 = 1;
		m4.m30 = 1;
		
		assertEquals(true, m1.equals(m1));
		assertEquals(false, m1.equals(m2));
		assertEquals(false, m1.equals(m3));
		assertEquals(true, m1.equals(m4));
	}
	
	@Test
	public void testSet()
	{
		Matrix4x4 m = new Matrix4x4();
		
		m.set(1, 1, 1);
		m.set(3, 1);
		
		assertEquals(1, m.m11, 1e-15);
		assertEquals(1, m.m30, 1e-15);
	}
	
	@Test
	public void testGet()
	{
		Matrix4x4 m = new Matrix4x4();
		
		m.m11 = 1;
		m.m02 = 1;
		
		assertEquals(1, m.get(1, 1), 1e-15);
		assertEquals(1, m.get(8), 1e-15);
	}
	
	@Test
	public void testSetColumn()
	{
		Matrix4x4 m = new Matrix4x4();
		
		float[] v1 = new float[] { 0, 1, 0, 1 };
		float[] v2 = new float[] { 1 };
		
		m.setColumn(0, v1);
		assertEquals(0, m.m00, 1e-15);
		assertEquals(1, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		assertEquals(1, m.m30, 1e-15);
		
		m.setColumn(1, v2);
		assertEquals(1, m.m01, 1e-15);
		assertEquals(0, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
		assertEquals(0, m.m31, 1e-15);
	}
	
	@Test
	public void testSetRow()
	{
		Matrix4x4 m = new Matrix4x4();
		
		float[] v1 = new float[] { 1, 0, 1, 1 };
		float[] v2 = new float[] { 0, 1 };
		
		m.setRow(0, v1);
		assertEquals(1, m.m00, 1e-15);
		assertEquals(0, m.m01, 1e-15);
		assertEquals(1, m.m02, 1e-15);
		assertEquals(1, m.m03, 1e-15);
		
		m.setRow(2, v2);
		assertEquals(0, m.m20, 1e-15);
		assertEquals(1, m.m21, 1e-15);
		assertEquals(0, m.m22, 1e-15);
		assertEquals(0, m.m23, 1e-15);
	}
	
	@Test
	public void testGetColumn()
	{
		Matrix4x4 m = new Matrix4x4();
		m.m00 = 1;
		m.m10 = 1;
		m.m20 = 0;
		m.m30 = 1;
		
		float[] v = m.getColumn(0);
		
		assertEquals(1, v[0], 1e-15);
		assertEquals(1, v[1], 1e-15);
		assertEquals(0, v[2], 1e-15);
		assertEquals(1, v[3], 1e-15);
	}
	
	@Test
	public void testGetRow()
	{
		Matrix4x4 m = new Matrix4x4();
		m.m10 = 1;
		m.m11 = 1;
		m.m12 = 1;
		m.m13 = 0;
		
		float[] v = m.getRow(1);
		
		assertEquals(1, v[0], 1e-15);
		assertEquals(1, v[1], 1e-15);
		assertEquals(1, v[2], 1e-15);
		assertEquals(0, v[3], 1e-15);
	}
	
	@Test
	public void testZero()
	{
		Matrix4x4 m = Matrix4x4.zero();
		
		assertEquals(0, m.m00, 1e-15);
		assertEquals(0, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		assertEquals(0, m.m30, 1e-15);
		
		assertEquals(0, m.m01, 1e-15);
		assertEquals(0, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
		assertEquals(0, m.m31, 1e-15);
		
		assertEquals(0, m.m02, 1e-15);
		assertEquals(0, m.m12, 1e-15);
		assertEquals(0, m.m22, 1e-15);
		assertEquals(0, m.m32, 1e-15);
		
		assertEquals(0, m.m03, 1e-15);
		assertEquals(0, m.m13, 1e-15);
		assertEquals(0, m.m23, 1e-15);
		assertEquals(0, m.m33, 1e-15);
	}
	
	@Test
	public void testIdentity()
	{
		Matrix4x4 m = Matrix4x4.identity();
		
		assertEquals(1, m.m00, 1e-15);
		assertEquals(0, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		assertEquals(0, m.m30, 1e-15);
		
		assertEquals(0, m.m01, 1e-15);
		assertEquals(1, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
		assertEquals(0, m.m31, 1e-15);
		
		assertEquals(0, m.m02, 1e-15);
		assertEquals(0, m.m12, 1e-15);
		assertEquals(1, m.m22, 1e-15);
		assertEquals(0, m.m32, 1e-15);
		
		assertEquals(0, m.m03, 1e-15);
		assertEquals(0, m.m13, 1e-15);
		assertEquals(0, m.m23, 1e-15);
		assertEquals(1, m.m33, 1e-15);
	}
}
