package com.snakybo.sengine;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public abstract class Game
{
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
}
