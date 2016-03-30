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
	 * @param value - The value to clamp
	 * @return The clamped value
	 */
	public static long clamp01(long value)
	{
		return clamp(value, 0, 1);
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
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value - The value to clamp
	 * @return The clamped value
	 */
	public static double clamp01(double value)
	{
		return clamp(value, 0, 1);
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
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value - The value to clamp
	 * @return The clamped value
	 */
	public static float clamp01(float value)
	{
		return clamp(value, 0, 1);
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
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp the value between 0 and 1
	 * @param value - The value to clamp
	 * @return The clamped value
	 */
	public static int clamp01(int value)
	{
		return clamp(value, 0, 1);
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
		return Math.max(min, Math.min(max, value));
	}
}
