package com.snakybo.sengine;

import com.snakybo.sengine.util.TimeInternal;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public abstract class Game
{
	private static int targetFrameRate = 60;
	
	public Game()
	{
		SEngine.start(this);
	}
	
	/**
	 * Used to create the game scene
	 */
	public abstract void createScene();
	
	/**
	 * @return The name of the game
	 */
	public abstract String getName();
	
	/**
	 * Quit the game
	 */
	public static void quit()
	{
		SEngine.stop();
	}
	
	/**
	 * Set the target frame rate
	 * @param targetFrameRate - The new target frame rate
	 */
	public static void setTargetFrameRate(int targetFrameRate)
	{
		Game.targetFrameRate = targetFrameRate;
		
		// Update the frame time
		TimeInternal.updateFrameTime();
	}
	
	/**
	 * @return The target frame rate
	 */
	public static int getTargetFrameRate()
	{
		return targetFrameRate;
	}
}