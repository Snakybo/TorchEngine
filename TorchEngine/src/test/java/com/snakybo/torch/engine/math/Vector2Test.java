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
public class Vector2Test
{
	@Test
	public void testCtor()
	{
		Vector2 v1 = new Vector2();
		
		assertEquals(0, v1.x, 1e-15);
		assertEquals(0, v1.y, 1e-15);
		
		Vector2 v2 = new Vector2(1, 0);
		
		assertEquals(1, v2.x, 1e-15);
		assertEquals(0, v2.y, 1e-15);
		
		Vector2 v3 = new Vector2(new Vector2(5, 6));
		
		assertEquals(5, v3.x, 1e-15);
		assertEquals(6, v3.y, 1e-15);
		
		Vector2 v4 = new Vector2(new Vector3(1, 2, 3));
		
		assertEquals(1, v4.x, 1e-15);
		assertEquals(2, v4.y, 1e-15);
		
		Vector2 v5 = new Vector2(new Vector4(12, 5, 5, 2));
		
		assertEquals(12, v5.x, 1e-15);
		assertEquals(5, v5.y, 1e-15);
	}
	
	@Test
	public void testHashCode()
	{
		Vector2 v1 = new Vector2(15, 123);
		Vector2 v2 = new Vector2(15, 122);
		Vector2 v3 = new Vector2(123, 15);
		Vector2 v4 = new Vector2(15, 123);
		
		assertEquals(true, v1.hashCode() == v1.hashCode());
		assertEquals(false, v1.hashCode() == v2.hashCode());
		assertEquals(false, v1.hashCode() == v3.hashCode());
		assertEquals(true, v1.hashCode() == v4.hashCode());
	}
	
	@Test
	public void testEquals()
	{
		Vector2 v1 = new Vector2(15, 123);
		Vector2 v2 = new Vector2(15, 122);
		Vector2 v3 = new Vector2(123, 15);
		Vector2 v4 = new Vector2(15, 123);
		
		assertEquals(true, v1.equals(v1));
		assertEquals(false, v1.equals(v2));
		assertEquals(false, v1.equals(v3));
		assertEquals(true, v1.equals(v4));
	}
	
	@Test
	public void testLength()
	{
		Vector2 v1 = new Vector2(13, 116);
		Vector2 v2 = new Vector2(21, 13);
		
		assertEquals(116.72f, v1.length(), 0.05f);
		assertEquals(610, v2.length2(), 1e-15);
	}
	
	@Test
	public void testNormalize()
	{
		Vector2 v = new Vector2(3, 5);
		v.normalize();
		
		assertEquals(0.51f, v.x, 0.05f);
		assertEquals(0.85f, v.y, 0.05f);
	}
	
	@Test
	public void testAdd()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector2 r1 = Vector2.one().add(v2);
		
		assertEquals(4, r1.x, 1e-15);
		assertEquals(4, r1.y, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector2 r2 = Vector2.one().add(v3);
		
		assertEquals(7, r2.x, 1e-15);
		assertEquals(3, r2.y, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector2 r3 = Vector2.one().add(v4);
		
		assertEquals(6, r3.x, 1e-15);
		assertEquals(2, r3.y, 1e-15);
	}
	
	@Test
	public void testSub()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector2 r1 = Vector2.one().sub(v2);
		
		assertEquals(-2, r1.x, 1e-15);
		assertEquals(-2, r1.y, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector2 r2 = Vector2.one().sub(v3);
		
		assertEquals(-5, r2.x, 1e-15);
		assertEquals(-1, r2.y, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector2 r3 = Vector2.one().sub(v4);
		
		assertEquals(-4, r3.x, 1e-15);
		assertEquals(0, r3.y, 1e-15);
	}
	
	@Test
	public void testMul()
	{
		Vector2 v1 = new Vector2(3, 3).mul(3);
		
		assertEquals(9, v1.x, 1e-15);
		assertEquals(9, v1.y, 1e-15);
		
		Vector2 v2 = new Vector2(3, 2).mul(0.5f);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
	}
	
	@Test
	public void testDiv()
	{
		Vector2 v1 = new Vector2(3, 3).div(0.33f);
		
		assertEquals(9, v1.x, 0.1f);
		assertEquals(9, v1.y, 0.1f);
		
		Vector2 v2 = new Vector2(3, 2).div(2);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
	}
	
	@Test
	public void testLerp()
	{
		Vector2 v1 = new Vector2(0, 0);
		Vector2 v2 = new Vector2(1, 1);
		
		Vector2 r = Vector2.lerp(v1, v2, 0.5f);
		
		assertEquals(0.5f, r.x, 1e-15);
		assertEquals(0.5f, r.y, 1e-15);
	}
	
	@Test
	public void testDot()
	{
		Vector2 v1 = new Vector2(4, 6);
		Vector2 v2 = new Vector2(1, 7);
		
		assertEquals(46, Vector2.dot(v1, v2), 1e-15);
	}
	
	@Test
	public void testAngle()
	{
		Vector2 v1 = new Vector2(0, 1);
		Vector2 v2 = new Vector2(1, -1);
		
		assertEquals(135, Vector2.angle(v1, v2), 1e-15);
	}
	
	@Test
	public void testDistance()
	{
		Vector2 v1 = new Vector2(0, 0);
		Vector2 v2 = new Vector2(5, 0);
		
		assertEquals(5, Vector2.distance(v1, v2), 1e-15);
	}
}
