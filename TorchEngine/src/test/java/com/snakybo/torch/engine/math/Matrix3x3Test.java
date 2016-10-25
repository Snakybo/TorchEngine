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
public class Matrix3x3Test
{
	@Test
	public void testHashCode()
	{
		Matrix3x3 m1 = new Matrix3x3();
		m1.m00 = 1;
		m1.m10 = 1;
		m1.m20 = 1;
		
		Matrix3x3 m2 = new Matrix3x3();
		m2.m00 = 1;
		m2.m11 = 0;
		m2.m22 = 1;
		
		Matrix3x3 m3 = new Matrix3x3();
		m3.m00 = 1;
		m3.m10 = 1;
		m3.m20 = 1;
		m3.m01 = 1;
		
		Matrix3x3 m4 = new Matrix3x3();
		m4.m00 = 1;
		m4.m10 = 1;
		m4.m20 = 1;
		
		assertEquals(true, m1.hashCode() == m1.hashCode());
		assertEquals(false, m1.hashCode() == m2.hashCode());
		assertEquals(false, m1.hashCode() == m3.hashCode());
		assertEquals(true, m1.hashCode() == m4.hashCode());
	}
	
	@Test
	public void testEquals()
	{
		Matrix3x3 m1 = new Matrix3x3();
		m1.m00 = 1;
		m1.m10 = 1;
		m1.m20 = 1;
		
		Matrix3x3 m2 = new Matrix3x3();
		m2.m00 = 1;
		m2.m02 = 0;
		m2.m11 = 1;
		
		Matrix3x3 m3 = new Matrix3x3();
		m3.m00 = 1;
		m3.m10 = 1;
		m3.m20 = 1;
		m3.m01 = 1;
		
		Matrix3x3 m4 = new Matrix3x3();
		m4.m00 = 1;
		m4.m10 = 1;
		m4.m20 = 1;
		
		assertEquals(true, m1.equals(m1));
		assertEquals(false, m1.equals(m2));
		assertEquals(false, m1.equals(m3));
		assertEquals(true, m1.equals(m4));
	}
	
	@Test
	public void testSet()
	{
		Matrix3x3 m = new Matrix3x3();
		
		m.set(1, 1, 1);
		m.set(3, 1);
		
		assertEquals(1, m.m11, 1e-15);
		assertEquals(1, m.m01, 1e-15);
	}
	
	@Test
	public void testGet()
	{
		Matrix3x3 m = new Matrix3x3();
		
		m.m11 = 1;
		m.m22 = 1;
		
		assertEquals(1, m.get(1, 1), 1e-15);
		assertEquals(1, m.get(8), 1e-15);
	}
	
	@Test
	public void testSetColumn()
	{
		Matrix3x3 m = new Matrix3x3();
		
		float[] v1 = new float[] { 0, 1, 0 };
		float[] v2 = new float[] { 1 };
		
		m.setColumn(0, v1);
		assertEquals(0, m.m00, 1e-15);
		assertEquals(1, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		
		m.setColumn(1, v2);
		assertEquals(1, m.m01, 1e-15);
		assertEquals(0, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
	}
	
	@Test
	public void testSetRow()
	{
		Matrix3x3 m = new Matrix3x3();
		
		float[] v1 = new float[] { 1, 0, 1 };
		float[] v2 = new float[] { 0, 1 };
		
		m.setRow(0, v1);
		assertEquals(1, m.m00, 1e-15);
		assertEquals(0, m.m01, 1e-15);
		assertEquals(1, m.m02, 1e-15);
		
		m.setRow(2, v2);
		assertEquals(0, m.m20, 1e-15);
		assertEquals(1, m.m21, 1e-15);
		assertEquals(0, m.m22, 1e-15);
	}
	
	@Test
	public void testGetColumn()
	{
		Matrix3x3 m = new Matrix3x3();
		m.m00 = 1;
		m.m10 = 1;
		m.m20 = 0;
		
		float[] v = m.getColumn(0);
		
		assertEquals(1, v[0], 1e-15);
		assertEquals(1, v[1], 1e-15);
		assertEquals(0, v[2], 1e-15);
	}
	
	@Test
	public void testGetRow()
	{
		Matrix3x3 m = new Matrix3x3();
		m.m10 = 1;
		m.m11 = 1;
		m.m12 = 1;
		
		float[] v = m.getRow(1);
		
		assertEquals(1, v[0], 1e-15);
		assertEquals(1, v[1], 1e-15);
		assertEquals(1, v[2], 1e-15);
	}
	
	@Test
	public void testZero()
	{
		Matrix3x3 m = Matrix3x3.zero();
		
		assertEquals(0, m.m00, 1e-15);
		assertEquals(0, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		
		assertEquals(0, m.m01, 1e-15);
		assertEquals(0, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
		
		assertEquals(0, m.m02, 1e-15);
		assertEquals(0, m.m12, 1e-15);
		assertEquals(0, m.m22, 1e-15);
	}
	
	@Test
	public void testIdentity()
	{
		Matrix3x3 m = Matrix3x3.identity();
		
		assertEquals(1, m.m00, 1e-15);
		assertEquals(0, m.m10, 1e-15);
		assertEquals(0, m.m20, 1e-15);
		
		assertEquals(0, m.m01, 1e-15);
		assertEquals(1, m.m11, 1e-15);
		assertEquals(0, m.m21, 1e-15);
		
		assertEquals(0, m.m02, 1e-15);
		assertEquals(0, m.m12, 1e-15);
		assertEquals(1, m.m22, 1e-15);
	}
}
