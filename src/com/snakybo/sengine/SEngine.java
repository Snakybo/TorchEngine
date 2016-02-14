package com.snakybo.sengine;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.rendering.WindowImplementation;
import com.snakybo.sengine.util.TimeInternal;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class SEngine
{
	private static String gameName;
	
	private static boolean running;
	
	private SEngine()
	{
		throw new AssertionError();
	}
	
	/**
	 * Start the engine, called by the constructor of {@link Game}
	 * @param game The game
	 */
	static void start(Game game)
	{
		if(!running)
		{
			Logger.log("Starting", "SEngine");
			
			SEngine.running = true;
			SEngine.gameName = game.getName();
			
			// Initialize engine systems
			WindowImplementation.create();
			
			// Create the scene
			game.createScene();
			
			// Begin the mainloop
			mainLoop();
		}
	}
	
	/**
	 * Stop the engine, called by {@link Game#quit()}
	 */
	static void stop()
	{
		if(running)
		{
			Logger.log("Stopping", "SEngine");
			
			running = false;
		}
	}
	
	/**
	 * The main loop of the engine
	 */
	private static void mainLoop()
	{
		while(running)
		{
			TimeInternal.update();
			TimeInternal.updateFrameCount();
			
			if(WindowImplementation.isCloseRequested())
			{
				stop();
			}
			
			WindowImplementation.update();
		}
		
		destroy();
	}
	
	/**
	 * Destroy all engine systems
	 */
	private static void destroy()
	{
		WindowImplementation.destroy();
	}
	
	/**
	 * @return The name of the game
	 */
	public static String getGameName()
	{
		return gameName;
	}
}
