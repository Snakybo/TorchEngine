package com.snakybo.sengine.util;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class MathUtils
{
	private MathUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value - The value to clamp
	 * @param min - The minimum value
	 * @param max - The maximum value
	 * @return The clamped value
	 */
	public static long clamp(long value, long min, long max)
	{
		return Math.max(min, Math.max(max, value));
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value - The value to clamp
	 * @param min - The minimum value
	 * @param max - The maximum value
	 * @return The clamped value
	 */
	public static double clamp(double value, double min, double max)
	{
		return Math.max(min, Math.max(max, value));
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value - The value to clamp
	 * @param min - The minimum value
	 * @param max - The maximum value
	 * @return The clamped value
	 */
	public static float clamp(float value, float min, float max)
	{
		return Math.max(min, Math.max(max, value));
	}
	
	/**
	 * Clamp the value between a min and a max value
	 * @param value - The value to clamp
	 * @param min - The minimum value
	 * @param max - The maximum value
	 * @return The clamped value
	 */
	public static int clamp(int value, int min, int max)
	{
		return Math.max(min, Math.max(max, value));
	}
}
