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

package com.snakybo.torch.util.time;

/**
 * <p>
 * Manages the time.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Time
{
	static double currentTime;
	static double lastTime;
	
	static float deltaTime;
	
	static long frameCount;
	
	private Time()
	{
		throw new AssertionError();
	}
	
	/**
	 * <p>
	 * Get the time in microseconds since the engine was started.
	 * </p>
	 *
	 * @return The time in microseconds since the engine was started.
	 */
	public static double getCurrentTime()
	{
		return currentTime;
	}
	
	/**
	 * <p>
	 * Get the time of the previous frame.
	 * </p>
	 *
	 * <p>
	 * Can be used for delta calculations by doing {@code getCurrentTime() - getLastTime()}.
	 * </p>
	 *
	 * @return The time in microseconds of the last frame.
	 */
	public static double getLastTime()
	{
		return lastTime;
	}
	
	/**
	 * <p>
	 * Get the time in milliseconds since the engine was started.
	 * </p>
	 *
	 * @return The time in milliseconds since the engine was started.
	 */
	public static double getCurrentTimeMillis()
	{
		return getCurrentTime() * 1000;
	}
	
	/**
	 * <p>
	 * Get the time it took to get from the previous frame to this frame.
	 * </p>
	 *
	 * @return The current delta time.
	 */
	public static float getDeltaTime()
	{
		return deltaTime;
	}
	
	/**
	 * <p>
	 * Get the ID of this frame, the frame ID is incremented at the start of every frame.
	 * </p>
	 *
	 * @return The current frame ID.
	 */
	public static long getFrameId()
	{
		return frameCount;
	}
}
