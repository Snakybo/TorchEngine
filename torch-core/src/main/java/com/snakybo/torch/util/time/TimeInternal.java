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

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.debug.Debug;
import com.snakybo.torch.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class TimeInternal
{
	static long totalFrameCount;
	
	static double frameTime;	
	static double currentTime;
	static double lastTime;
	static double passedTime;
	static double frameCounter;
	
	static int currentFrameCount;
	static int framesPerSecond;
	
	static
	{
		lastTime = glfwGetTime();
		currentTime = glfwGetTime();
		
		updateFrameTime();
	}
	
	private TimeInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Update the engine time and frame rate
	 */
	public static void update()
	{
		currentTime = glfwGetTime();
		passedTime = currentTime - lastTime;
		lastTime = currentTime;
		
		frameCounter += passedTime;
		
		if(frameCounter >= 1.0)
		{
			framesPerSecond = currentFrameCount;
			
			frameCounter = 0;
			currentFrameCount = 0;
			
			// Log FPS if required
			if(Debug.LOG_FPS)
			{
				Logger.log("FPS: " + Time.getFrameRate());
			}
		}
	}
	
	/**
	 * Update the frame count
	 */
	public static void updateFrameCount()
	{
		totalFrameCount++;
		currentFrameCount++;
	}
	
	/**
	 * Update the frame time, called by {@link TorchGame#setTargetFrameRate(int)}
	 */
	public static void updateFrameTime()
	{
		frameTime = 1.0 / TorchGame.getTargetFrameRate();
	}
	
	/**
	 * @return The desired time per frame
	 */
	public static double getFrameTime()
	{
		return frameTime;
	}
	
	/**
	 * @return The time that has passed since the last frame
	 */
	public static double getPassedTime()
	{
		return passedTime;
	}
}