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

package com.snakybo.sengine.util;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MathUtils
{
	private MathUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value The value to clamp
	 * @return The clamped value
	 */
	public static long clamp01(long value)
	{
		return clamp(value, 0, 1);
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The clamped value
	 */
	public static long clamp(long value, long min, long max)
	{
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value The value to clamp
	 * @return The clamped value
	 */
	public static double clamp01(double value)
	{
		return clamp(value, 0, 1);
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The clamped value
	 */
	public static double clamp(double value, double min, double max)
	{
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value The value to clamp
	 * @return The clamped value
	 */
	public static float clamp01(float value)
	{
		return clamp(value, 0, 1);
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The clamped value
	 */
	public static float clamp(float value, float min, float max)
	{
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value The value to clamp
	 * @return The clamped value
	 */
	public static int clamp01(int value)
	{
		return clamp(value, 0, 1);
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The clamped value
	 */
	public static int clamp(int value, int min, int max)
	{
		return Math.max(min, Math.min(max, value));
	}
}
