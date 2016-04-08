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

package com.snakybo.sengine;

import org.lwjgl.Version;

import com.snakybo.sengine.audio.AudioManagerInternal;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.input.cursor.CursorInternal;
import com.snakybo.sengine.input.joystick.JoystickInternal;
import com.snakybo.sengine.input.keyboad.KeyboardInternal;
import com.snakybo.sengine.input.mouse.MouseInternal;
import com.snakybo.sengine.scene.SceneUtilities;
import com.snakybo.sengine.util.time.TimeInternal;
import com.snakybo.sengine.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SEngine
{	
	public static final int VERSION_MAJOR = 1;
	public static final int VERSION_MINOR = 0;
	public static final int VERSION_PATCH = 0;
	public static final String VERSION_STRING = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH;
	
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
			LoggerInternal.log("Starting", "SEngine");
			LoggerInternal.log("SEngine version: " + VERSION_STRING, "SEngine");
			LoggerInternal.log("Game name: " + game.getName(), "SEngine");
			
			logLWJGLInfo();
			
			SEngine.running = true;
			SEngine.gameName = game.getName();
			
			// Initialize engine systems
			WindowInternal.createWindowed(1280, 720);
			AudioManagerInternal.create();
			
			KeyboardInternal.create();
			MouseInternal.create();
			CursorInternal.create();
			JoystickInternal.create();
			
			// Create the scene
			game.createScene();
			
			// Begin the main loop
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
			LoggerInternal.log("Stopping", "SEngine");
			
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
				if(WindowInternal.isCloseRequested())
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
				
				WindowInternal.update();
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
	
	/**
	 * Run a single update cycle
	 */
	private static void updateCycle()
	{
		SceneUtilities.runUpdateCycle();
		
		// Update input
		KeyboardInternal.update();
		MouseInternal.update();
		CursorInternal.update();
		JoystickInternal.update();
	}
	
	/**
	 * Run a single render cycle
	 */
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
		JoystickInternal.destroy();
		
		AudioManagerInternal.destroy();
		WindowInternal.destroy();
	}
	
	/**
	 * @return The name of the game
	 */
	public static String getGameName()
	{
		return gameName;
	}
	
	/**
	 * Log information about LWJGL
	 */
	private static void logLWJGLInfo()
	{
		LoggerInternal.log("Version: " + Version.getVersion(), "LWJGL");
	}
}
