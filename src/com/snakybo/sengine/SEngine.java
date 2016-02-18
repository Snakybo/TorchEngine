package com.snakybo.sengine;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.input.cursor.CursorInternal;
import com.snakybo.sengine.input.keyboad.KeyboardInternal;
import com.snakybo.sengine.input.mouse.MouseInternal;
import com.snakybo.sengine.scene.SceneUtilities;
import com.snakybo.sengine.util.time.TimeInternal;
import com.snakybo.sengine.window.WindowImplementation;

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
			Logger.log("Game name: " + game.getName(), "SEngine");
			
			SEngine.running = true;
			SEngine.gameName = game.getName();
			
			// Initialize engine systems
			WindowImplementation.create();
			
			KeyboardInternal.create();
			MouseInternal.create();
			CursorInternal.create();
			
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
		double unprocessedTime = 0.0;
		
		while(running)
		{
			boolean shouldRender = false;
			
			TimeInternal.update();
			unprocessedTime += TimeInternal.getPassedTime();
			
			// Construct the frame queue
			// Might have to be moved to updateCycle()
			SceneUtilities.constructFrameQueue();
			
			while(unprocessedTime > TimeInternal.getFrameTime())
			{
				if(WindowImplementation.isCloseRequested())
				{
					stop();
				}
				
				shouldRender = true;
				unprocessedTime -= TimeInternal.getFrameTime();
				
				updateCycle();
			}
			
			if(shouldRender)
			{
				renderCycle();
				
				WindowImplementation.update();
				TimeInternal.updateFrameCount();
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					Logger.logException(e, "SEngine");
				}
			}
		}
		
		destroy();
	}
	
	private static void updateCycle()
	{
		SceneUtilities.runUpdateCycle();
		
		// Update input
		KeyboardInternal.update();
		MouseInternal.update();
		CursorInternal.update();
	}
	
	private static void renderCycle()
	{
		SceneUtilities.runRenderCycle();
	}
	
	/**
	 * Destroy all engine systems
	 */
	private static void destroy()
	{
		CursorInternal.destroy();
		MouseInternal.destroy();
		KeyboardInternal.destroy();
		
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
