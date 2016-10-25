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
public class Vector3Test
{
	@Test
	public void testCtor()
	{
		Vector3 v1 = new Vector3();
		
		assertEquals(0, v1.x, 1e-15);
		assertEquals(0, v1.y, 1e-15);
		assertEquals(0, v1.z, 1e-15);
		
		Vector3 v2 = new Vector3(1, 0, 2);
		
		assertEquals(1, v2.x, 1e-15);
		assertEquals(0, v2.y, 1e-15);
		assertEquals(2, v2.z, 1e-15);
		
		Vector3 v3 = new Vector3(new Vector2(1, 2));
		
		assertEquals(1, v3.x, 1e-15);
		assertEquals(2, v3.y, 1e-15);
		assertEquals(0, v3.z, 1e-15);
		
		Vector3 v4 = new Vector3(new Vector3(1, 2, 3));
		
		assertEquals(1, v4.x, 1e-15);
		assertEquals(2, v4.y, 1e-15);
		assertEquals(3, v4.z, 1e-15);
		
		Vector3 v5 = new Vector3(new Vector4(12, 5, 5, 2));
		
		assertEquals(12, v5.x, 1e-15);
		assertEquals(5, v5.y, 1e-15);
		assertEquals(5, v5.z, 1e-15);
	}
	
	@Test
	public void testHashCode()
	{
		Vector3 v1 = new Vector3(15, 123, 215);
		Vector3 v2 = new Vector3(15, 122, 23);
		Vector3 v3 = new Vector3(123, 15, 12);
		Vector3 v4 = new Vector3(15, 123, 215);
		
		assertEquals(true, v1.hashCode() == v1.hashCode());
		assertEquals(false, v1.hashCode() == v2.hashCode());
		assertEquals(false, v1.hashCode() == v3.hashCode());
		assertEquals(true, v1.hashCode() == v4.hashCode());
	}
	
	@Test
	public void testEquals()
	{
		Vector3 v1 = new Vector3(15, 123, 215);
		Vector3 v2 = new Vector3(15, 122, 23);
		Vector3 v3 = new Vector3(123, 15, 12);
		Vector3 v4 = new Vector3(15, 123, 215);
		
		assertEquals(true, v1.equals(v1));
		assertEquals(false, v1.equals(v2));
		assertEquals(false, v1.equals(v3));
		assertEquals(true, v1.equals(v4));
	}
	
	@Test
	public void testLength()
	{
		Vector3 v1 = new Vector3(13, 116, 54);
		Vector3 v2 = new Vector3(21, 13, 65);
		
		assertEquals(128.61f, v1.length(), 0.05f);
		assertEquals(4835, v2.length2(), 1e-15);
	}
	
	@Test
	public void testNormalize()
	{
		Vector3 v = new Vector3(3, 5, 65);
		v.normalize();
		
		assertEquals(0.0f, v.x, 0.05f);
		assertEquals(0.1f, v.y, 0.05f);
		assertEquals(1.0f, v.z, 0.05f);
	}
	
	@Test
	public void testAdd()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector3 r1 = Vector3.one().add(v2);
		
		assertEquals(4, r1.x, 1e-15);
		assertEquals(4, r1.y, 1e-15);
		assertEquals(1, r1.z, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector3 r2 = Vector3.one().add(v3);
		
		assertEquals(7, r2.x, 1e-15);
		assertEquals(3, r2.y, 1e-15);
		assertEquals(3, r2.z, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector3 r3 = Vector3.one().add(v4);
		
		assertEquals(6, r3.x, 1e-15);
		assertEquals(2, r3.y, 1e-15);
		assertEquals(7, r3.z, 1e-15);
	}
	
	@Test
	public void testSub()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector3 r1 = Vector3.one().sub(v2);
		
		assertEquals(-2, r1.x, 1e-15);
		assertEquals(-2, r1.y, 1e-15);
		assertEquals(1, r1.z, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector3 r2 = Vector3.one().sub(v3);
		
		assertEquals(-5, r2.x, 1e-15);
		assertEquals(-1, r2.y, 1e-15);
		assertEquals(-1, r2.z, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector3 r3 = Vector3.one().sub(v4);
		
		assertEquals(-4, r3.x, 1e-15);
		assertEquals(0, r3.y, 1e-15);
		assertEquals(-5, r3.z, 1e-15);
	}
	
	@Test
	public void testMul()
	{
		Vector3 v1 = new Vector3(3, 3, 3).mul(3);
		
		assertEquals(9, v1.x, 1e-15);
		assertEquals(9, v1.y, 1e-15);
		assertEquals(9, v1.z, 1e-15);
		
		Vector3 v2 = new Vector3(3, 2, 5).mul(0.5f);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
		assertEquals(2.5f, v2.z, 1e-15);
	}
	
	@Test
	public void testDiv()
	{
		Vector3 v1 = new Vector3(3, 3, 3).div(0.33f);
		
		assertEquals(9, v1.x, 0.1f);
		assertEquals(9, v1.y, 0.1f);
		assertEquals(9, v1.z, 0.1f);
		
		Vector3 v2 = new Vector3(3, 2, 1).div(2);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
		assertEquals(0.5f, v2.z, 1e-15);
	}
	
	@Test
	public void testLerp()
	{
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(1, 1, 1);
		
		Vector3 r = Vector3.lerp(v1, v2, 0.5f);
		
		assertEquals(0.5f, r.x, 1e-15);
		assertEquals(0.5f, r.y, 1e-15);
		assertEquals(0.5f, r.z, 1e-15);
	}
	
	@Test
	public void testDot()
	{
		Vector3 v1 = new Vector3(4, 6, 12);
		Vector3 v2 = new Vector3(1, 7, 32);
		
		assertEquals(430, Vector3.dot(v1, v2), 1e-15);
	}
	
	@Test
	public void testAngle()
	{
		Vector3 v1 = new Vector3(0, 1, 0);
		Vector3 v2 = new Vector3(1, -1, 5);
		
		assertEquals(101.09f, Vector3.angle(v1, v2), 0.05f);
	}
	
	@Test
	public void testDistance()
	{
		Vector3 v1 = new Vector3(0, 0, 3);
		Vector3 v2 = new Vector3(5, 0, 9);
		
		assertEquals(7.81f, Vector3.distance(v1, v2), 0.05f);
	}
}
