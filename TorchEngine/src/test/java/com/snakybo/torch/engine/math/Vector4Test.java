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
public class Vector4Test
{
	@Test
	public void testCtor()
	{
		Vector4 v1 = new Vector4();
		
		assertEquals(0, v1.x, 1e-15);
		assertEquals(0, v1.y, 1e-15);
		assertEquals(0, v1.z, 1e-15);
		assertEquals(0, v1.w, 1e-15);
		
		Vector4 v2 = new Vector4(1, 0, 2, 5);
		
		assertEquals(1, v2.x, 1e-15);
		assertEquals(0, v2.y, 1e-15);
		assertEquals(2, v2.z, 1e-15);
		assertEquals(5, v2.w, 1e-15);
		
		Vector4 v3 = new Vector4(new Vector2(1, 2));
		
		assertEquals(1, v3.x, 1e-15);
		assertEquals(2, v3.y, 1e-15);
		assertEquals(0, v3.z, 1e-15);
		assertEquals(0, v3.w, 1e-15);
		
		Vector4 v4 = new Vector4(new Vector3(1, 2, 3));
		
		assertEquals(1, v4.x, 1e-15);
		assertEquals(2, v4.y, 1e-15);
		assertEquals(3, v4.z, 1e-15);
		assertEquals(0, v4.w, 1e-15);
		
		Vector4 v5 = new Vector4(new Vector4(12, 5, 5, 2));
		
		assertEquals(12, v5.x, 1e-15);
		assertEquals(5, v5.y, 1e-15);
		assertEquals(5, v5.z, 1e-15);
		assertEquals(2, v5.w, 1e-15);
	}
	
	@Test
	public void testHashCode()
	{
		Vector4 v1 = new Vector4(15, 123, 215, 3);
		Vector4 v2 = new Vector4(15, 122, 23, 54);
		Vector4 v3 = new Vector4(123, 15, 12, 12);
		Vector4 v4 = new Vector4(15, 123, 215, 3);
		
		assertEquals(true, v1.hashCode() == v1.hashCode());
		assertEquals(false, v1.hashCode() == v2.hashCode());
		assertEquals(false, v1.hashCode() == v3.hashCode());
		assertEquals(true, v1.hashCode() == v4.hashCode());
	}
	
	@Test
	public void testEquals()
	{
		Vector4 v1 = new Vector4(15, 123, 215, 3);
		Vector4 v2 = new Vector4(15, 122, 23, 54);
		Vector4 v3 = new Vector4(123, 15, 12, 12);
		Vector4 v4 = new Vector4(15, 123, 215, 3);
		
		assertEquals(true, v1.equals(v1));
		assertEquals(false, v1.equals(v2));
		assertEquals(false, v1.equals(v3));
		assertEquals(true, v1.equals(v4));
	}
	
	@Test
	public void testLength()
	{
		Vector4 v1 = new Vector4(13, 116, 54, 22);
		Vector4 v2 = new Vector4(21, 13, 65, 43);
		
		assertEquals(130.48f, v1.length(), 0.05f);
		assertEquals(6684, v2.length2(), 1e-15);
	}
	
	@Test
	public void testNormalize()
	{
		Vector4 v = new Vector4(3, 5, 65, 54);
		v.normalize();
		
		assertEquals(0.0f, v.x, 0.05f);
		assertEquals(0.1f, v.y, 0.05f);
		assertEquals(0.8f, v.z, 0.05f);
		assertEquals(0.6f, v.w, 0.05f);
	}
	
	@Test
	public void testAdd()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector4 r1 = Vector4.one().add(v2);
		
		assertEquals(4, r1.x, 1e-15);
		assertEquals(4, r1.y, 1e-15);
		assertEquals(1, r1.z, 1e-15);
		assertEquals(1, r1.w, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector4 r2 = Vector4.one().add(v3);
		
		assertEquals(7, r2.x, 1e-15);
		assertEquals(3, r2.y, 1e-15);
		assertEquals(3, r2.z, 1e-15);
		assertEquals(1, r2.w, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector4 r3 = Vector4.one().add(v4);
		
		assertEquals(6, r3.x, 1e-15);
		assertEquals(2, r3.y, 1e-15);
		assertEquals(7, r3.z, 1e-15);
		assertEquals(2, r3.w, 1e-15);
	}
	
	@Test
	public void testSub()
	{
		Vector2 v2 = new Vector2(3, 3);
		Vector4 r1 = Vector4.one().sub(v2);
		
		assertEquals(-2, r1.x, 1e-15);
		assertEquals(-2, r1.y, 1e-15);
		assertEquals(1, r1.z, 1e-15);
		assertEquals(1, r1.z, 1e-15);
		
		Vector3 v3 = new Vector3(6, 2, 2);
		Vector4 r2 = Vector4.one().sub(v3);
		
		assertEquals(-5, r2.x, 1e-15);
		assertEquals(-1, r2.y, 1e-15);
		assertEquals(-1, r2.z, 1e-15);
		assertEquals(1, r2.w, 1e-15);
		
		Vector4 v4 = new Vector4(5, 1, 6, 1);
		Vector4 r3 = Vector4.one().sub(v4);
		
		assertEquals(-4, r3.x, 1e-15);
		assertEquals(0, r3.y, 1e-15);
		assertEquals(-5, r3.z, 1e-15);
		assertEquals(0, r3.w, 1e-15);
	}
	
	@Test
	public void testMul()
	{
		Vector4 v1 = new Vector4(3, 3, 3, 3).mul(3);
		
		assertEquals(9, v1.x, 1e-15);
		assertEquals(9, v1.y, 1e-15);
		assertEquals(9, v1.z, 1e-15);
		assertEquals(9, v1.w, 1e-15);
		
		Vector4 v2 = new Vector4(3, 2, 5, 2).mul(0.5f);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
		assertEquals(2.5f, v2.z, 1e-15);
		assertEquals(1, v2.w, 1e-15);
	}
	
	@Test
	public void testDiv()
	{
		Vector4 v1 = new Vector4(3, 3, 3, 3).div(0.33f);
		
		assertEquals(9, v1.x, 0.1f);
		assertEquals(9, v1.y, 0.1f);
		assertEquals(9, v1.z, 0.1f);
		assertEquals(9, v1.w, 0.1f);
		
		Vector4 v2 = new Vector4(3, 2, 1, 2).div(2);
		
		assertEquals(1.5f, v2.x, 1e-15);
		assertEquals(1, v2.y, 1e-15);
		assertEquals(0.5f, v2.z, 1e-15);
		assertEquals(1, v2.w, 1e-15);
	}
	
	@Test
	public void testLerp()
	{
		Vector4 v1 = new Vector4(0, 0, 0, 0);
		Vector4 v2 = new Vector4(1, 1, 1, 1);
		
		Vector4 r = Vector4.lerp(v1, v2, 0.5f);
		
		assertEquals(0.5f, r.x, 1e-15);
		assertEquals(0.5f, r.y, 1e-15);
		assertEquals(0.5f, r.z, 1e-15);
		assertEquals(0.5f, r.w, 1e-15);
	}
	
	@Test
	public void testDot()
	{
		Vector4 v1 = new Vector4(4, 6, 12, 43);
		Vector4 v2 = new Vector4(1, 7, 32, 12);
		
		assertEquals(946, Vector4.dot(v1, v2), 1e-15);
	}
	
	@Test
	public void testDistance()
	{
		Vector4 v1 = new Vector4(0, 0, 3, 1);
		Vector4 v2 = new Vector4(5, 0, 9, 6);
		
		assertEquals(9.27f, Vector4.distance(v1, v2), 0.05f);
	}
}
