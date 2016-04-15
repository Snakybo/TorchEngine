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

import java.lang.reflect.InvocationTargetException;

import org.lwjgl.Version;

import com.snakybo.sengine.audio.AudioManagerInternal;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.input.cursor.CursorInternal;
import com.snakybo.sengine.input.joystick.JoystickInternal;
import com.snakybo.sengine.input.keyboad.KeyboardInternal;
import com.snakybo.sengine.input.mouse.MouseInternal;
import com.snakybo.sengine.renderer.OpenGLRenderer;
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
	
	private Game game;
	
	private boolean running;
	
	SEngine(Game game)
	{
		LoggerInternal.log("Starting", this);
		LoggerInternal.log("SEngine version: " + VERSION_STRING, this);
		
		logLWJGLInfo();
		
		// Initialize engine systems
		WindowInternal.createWindowed(1280, 720);
		OpenGLRenderer.create();
		AudioManagerInternal.create();
		
		KeyboardInternal.create();
		MouseInternal.create();
		CursorInternal.create();
		JoystickInternal.create();
		
		this.game = game;
		
		start();
	}
	
	/**
	 * Start the engine, called by {@link Game#start()}
	 */
	private final void start()
	{
		if(!running)
		{
			running = true;
			
			game.onCreate();
			mainLoop();
		}
	}
	
	/**
	 * Stop the engine, called by {@link Game#quit()}
	 */
	public final void stop()
	{
		if(running)
		{
			LoggerInternal.log("Stopping", this);
			
			running = false;
		}
	}
	
	/**
	 * The main loop of the engine
	 */
	private final void mainLoop()
	{
		double unprocessedTime = 0.0;
		
		while(running)
		{
			boolean shouldRender = false;
			
			TimeInternal.update();
			unprocessedTime += TimeInternal.getPassedTime();
			
			// Load the new scene if the user scheduled a scene load via SceneManager.load
			SceneUtilities.loadScene();
			
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
	private final void updateCycle()
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
	private final void renderCycle()
	{
		SceneUtilities.runRenderCycle();
	}
	
	/**
	 * Destroy all engine systems
	 */
	private final void destroy()
	{
		JoystickInternal.destroy();
		CursorInternal.destroy();
		MouseInternal.destroy();
		KeyboardInternal.destroy();		
		
		AudioManagerInternal.destroy();
		WindowInternal.destroy();
	}
	
	/**
	 * Log information about LWJGL
	 */
	private final void logLWJGLInfo()
	{
		LoggerInternal.log("Version: " + Version.getVersion(), "LWJGL");
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		if(args.length < 1)
		{
			System.err.println("No game specified");
			System.exit(1);
		}
		
		Class.forName(args[0]).getConstructor().newInstance();
	}
}
