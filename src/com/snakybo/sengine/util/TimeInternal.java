package com.snakybo.sengine.util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import com.snakybo.sengine.Game;

/**
 * @author Kevin
 * @since Feb 14, 2016
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
	 * Update the frame time, called by {@link Game#setTargetFrameRate(int)}
	 */
	public static void updateFrameTime()
	{
		frameTime = 1.0 / Game.getTargetFrameRate();
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
