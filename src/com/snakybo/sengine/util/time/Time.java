package com.snakybo.sengine.util.time;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Time
{
	private Time()
	{
		throw new AssertionError();
	}
	
	/**
	 * @return The time in microseconds since the engine was started
	 */
	public static double getCurrentTime()
	{
		return TimeInternal.currentTime;
	}
	
	/**
	 * @return The time in milliseconds since the engine was started
	 */
	public static double getCurrentTimeMillis()
	{
		return getCurrentTime() * 1000;
	}
	
	/**
	 * @return The delta time since the last frame
	 */
	public static float getDeltaTime()
	{
		return (float)TimeInternal.frameTime;
	}
	
	/**
	 * @return The current frame ID
	 */
	public static long getFrameId()
	{
		return TimeInternal.totalFrameCount;
	}
	
	/**
	 * @return The current frame rate
	 */
	public static int getFrameRate()
	{
		return TimeInternal.framesPerSecond;
	}
}
